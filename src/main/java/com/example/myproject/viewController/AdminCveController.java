package com.example.myproject.viewController;


import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.LoginRequestDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.enumCode.EnumProgram;
import com.example.myproject.globalException.MyCustomException;
import com.example.myproject.model.Analyst;
import com.example.myproject.model.Cve;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.CveService;
import com.example.myproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminCveController {

    @Autowired
    private CveService cveService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("admin", new Analyst());
        model.addAttribute("roles", EnumProgram.Role.values()); // Enum for roles
        return "admin-registration"; // Renders admin-registration.html
    }

    // Handle registration of admin
    @PostMapping("/register-admin")
    public String handleRegistration(@ModelAttribute Analyst analyst,
                                     @RequestParam String confirmPassword,
                                     Model model) {
        try {
            if (!analyst.getPassword().equals(confirmPassword)) {
                model.addAttribute("errorMessage", "Password and Confirm Password do not match!");
                model.addAttribute("admin", analyst);
                return "admin-registration";
            }
            // Map Analyst -> UserRequestDto
            UserRequestDto dto = modelMapper.map(analyst, UserRequestDto.class);
            dto.setConfirm_password(confirmPassword);

            // Register admin
            userService.registerAdmin(dto);

            // Add success message and empty LoginRequestDto for login form
            model.addAttribute("successMessage", "Registration successful! Please check your email to verify.");
            model.addAttribute("admin", new LoginRequestDto());

            return "admin-login"; // Renders admin-login.html
        } catch (IllegalArgumentException | MyCustomException.EmailAlreadyExistsException e) {
            // Send error message and retain filled registration form
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("admin", analyst);
            model.addAttribute("roles", EnumProgram.Role.values());
            return "admin-registration";
        }
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("admin")) {
            model.addAttribute("admin", new LoginRequestDto()); // Required for Thymeleaf form binding
        }
        return "admin-login"; // Renders admin-login.html
    }

    // Handle login form submission
    @PostMapping("/post-cve-data")
    public String processLogin(@ModelAttribute("admin") LoginRequestDto loginRequestDto, Model model, HttpSession session,RedirectAttributes redirectAttributes) {

        Optional<Analyst> user = userRepository.findByEmailAndPasswordAndRole(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword(),
                EnumProgram.Role.ADMIN
        );

        if (user.isPresent()) {
            Analyst analyst = user.get();
            model.addAttribute("analyst", analyst);      // Pass to Thymeleaf
            session.setAttribute("loggedInUser", analyst);// store in session
            return "redirect:/admin/upload-cve"; // Renders upload-cve-data.html
        } else {
            model.addAttribute("errorMessage", "Invalid email and password");
            model.addAttribute("admin", loginRequestDto); // Keep entered email/password
            return "redirect:/admin/login";
        }
    }


    // GET - Upload CVE Page
    @GetMapping("/upload-cve")
    public String showUploadPage(HttpSession session, RedirectAttributes redirectAttributes) {
        Analyst analyst = (Analyst) session.getAttribute("loggedInUser");
        System.out.println(session.getAttribute("loggedInUser"));
        if (analyst == null || analyst.getRole() != EnumProgram.Role.ADMIN) {
            redirectAttributes.addFlashAttribute("error", "Please login as admin!");
            return "redirect:/admin/login";
        }
        return "upload-cve-data";
    }


    // POST - Upload CVE File (CSV/JSON/Excel)
    @PostMapping("/upload-cve")
    public String uploadCveFile(@RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            String filename = file.getOriginalFilename();

            if (filename == null || filename.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
                return "redirect:/admin/upload-cve"; // Correct redirect to upload page
            }

            // Check extension
            String extension = FilenameUtils.getExtension(filename).toLowerCase();
            if (!extension.equals("xlsx") && !extension.equals("xls")) {
                redirectAttributes.addFlashAttribute("error", "Only Excel files (.xls/.xlsx) are supported.");
                return "redirect:/admin/upload-cve";
            }

            // If Excel file, upload
            cveService.uploadFromExcel(file);
            redirectAttributes.addFlashAttribute("success", "CVE data uploaded successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Upload failed: " + e.getMessage());
        }

        return "redirect:/admin/upload-cve"; // Always redirect back to upload page
    }

    // ===== ADD CVE =====
    @GetMapping("/add-cve")
    public String showAddCveForm(Model model) {
        model.addAttribute("cve", new CveRequestDto());
        model.addAttribute("roles", EnumProgram.Role.values());
        return "admin-add-cve";  // Opens Add CVE page
    }

    @PostMapping("/add-cve")
    public String addCve(@ModelAttribute("cve") CveRequestDto dto,
                         RedirectAttributes redirectAttributes) {
        try {
            if(dto.getRole()== EnumProgram.Role.ADMIN) {
                cveService.uploadCveData(dto);
                redirectAttributes.addFlashAttribute("success", "CVE added successfully!");
            }else{
                // User is not allowed
                redirectAttributes.addFlashAttribute("failed", "Only Admins can add CVEs!");
            }
            } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add CVE: " + e.getMessage());
        }
        return "redirect:/admin/add-cve";
    }

    // ===== DELETE CVE =====
    @GetMapping("/delete-cve")
    public String showDeleteCvePage() {
        return "admin-delete-cve";  // Your Delete CVE page
    }

    @PostMapping("/delete-cve")
    public String deleteCveById(@RequestParam("id") int id,
                                RedirectAttributes redirectAttributes) {
        try {
            cveService.deleteDataById(id);
            redirectAttributes.addFlashAttribute("success", "CVE deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Deletion failed: " + e.getMessage());
        }
        return "redirect:/admin/delete-cve";
    }

    @PostMapping("/delete-all")
    public String deleteAllCves(RedirectAttributes redirectAttributes) {
        try {
            CveRequestDto dto = new CveRequestDto();
            cveService.deleteAllData(dto); // no parameter
            redirectAttributes.addFlashAttribute("successMsg", "All CVEs deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to delete all CVEs: " + e.getMessage());
        }
        return "redirect:/admin/delete-cve";
    }

    // ===== UPDATE CVE =====
    @GetMapping("/update-cve")
    public String showUpdateCveForm(Model model) {
        model.addAttribute("cve", new CveRequestDto());
        model.addAttribute("roles", EnumProgram.Role.values());
        return "admin-update-cve";  // Opens Update CVE page
    }

    @PostMapping("/update-cve")
    public String updateCve(@ModelAttribute("cve") CveRequestDto dto,
                            RedirectAttributes redirectAttributes) {
        try {
            if(dto.getRole()== EnumProgram.Role.ADMIN) {
                cveService.updateCveData(dto);
                redirectAttributes.addFlashAttribute("success", "CVE updated successfully!");
            }else{
                // User is not allowed for update cve
                redirectAttributes.addFlashAttribute("failed", "Only Admins can update CVEs!");
            }
            } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Update failed: " + e.getMessage());
        }
        return "redirect:/admin/update-cve";
    }
}