package com.example.myproject.viewController;

import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.globalException.MyCustomException;
import com.example.myproject.model.Analyst;
import com.example.myproject.model.Cve;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.service.CveService;
import com.example.myproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CveService cveService;

    @Autowired
    private ModelMapper modelMapper;


    //login user
    @GetMapping("/login-page")
    public String postUsersData(Model model){
        Analyst analyst=new Analyst();
        model.addAttribute("analyst",analyst);
        return "login";
    }

    // show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("analyst",new Analyst());
        return "registration";
    }

    // Handle login form submission (POST)
    @PostMapping("/post-data")
    public String processLogin(@ModelAttribute Analyst analyst,Model model) {
        // You can add actual authentication logic here
        Optional<Analyst> user=userRepository.findByEmailAndPassword(
        analyst.getEmail(),analyst.getPassword());

        System.out.println("Email: " + analyst.getEmail());
        System.out.println("Password: " + analyst.getPassword());

        if(user.isPresent()){
            model.addAttribute("analyst", user.get());
            return "dashboard";
        }else{
        model.addAttribute("errorMessage","Invalid email and password");
        return "login"; // loads dashboard.html after login
    }}


    @PostMapping("/register-user")
    public String handleRegistration(@ModelAttribute Analyst analyst,
                                     @RequestParam String confirmPassword,
                                     Model model) {
        try {
            // Auto-map Analyst -> UserRequestDto
            UserRequestDto dto = modelMapper.map(analyst, UserRequestDto.class);
            dto.setConfirm_password(confirmPassword); // set separately

            userService.createUser(dto);

            model.addAttribute("successMessage", "Registration successful! Please check your email to verify.");
            return "login";
        } catch (IllegalArgumentException | MyCustomException.EmailAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("analyst", analyst);
            return "registration";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String cveId,
                            @RequestParam(required = false) String version,
                            @RequestParam(required = false) String cvePackage,
                            @RequestParam(required = false) String status,
                            Model model) {

        // If all filters are empty, don't show any results
        if ((cveId == null || cveId.isEmpty()) &&
                (version == null || version.isEmpty()) &&
                (cvePackage == null || cvePackage.isEmpty()) &&
                (status == null || status.isEmpty())) {
            model.addAttribute("cveList", null); // ensure this is null
            return "dashboard";
        }

        List<Cve> cveList = cveService.search(cveId, version, cvePackage, status);
        model.addAttribute("cveList", cveList);
        return "dashboard";
    }


}

