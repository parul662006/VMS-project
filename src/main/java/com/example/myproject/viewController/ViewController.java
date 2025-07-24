package com.example.myproject.viewController;

import com.example.myproject.model.Analyst;
import com.example.myproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private UserRepository userRepository;

    //login user
    @GetMapping("login")
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


        //Handle registration form
        @PostMapping("register-user")
    public String handleRegistration(@ModelAttribute Analyst analyst,@RequestParam String confirmPassword, Model model) {
            if (!analyst.getPassword().equals(confirmPassword)) {
                model.addAttribute("errorMessage", "Password do not match");
                model.addAttribute("analyst", analyst);
                return "registration";

                //check if user already exists
            }if(userRepository.findByEmail(analyst.getEmail()).isPresent()){
                model.addAttribute("errorMessage","Email already exists" );
                model.addAttribute("analyst", analyst);
                return "registration";
            }
                userRepository.save(analyst);
                model.addAttribute("successMessage","Registration successfull! pLease log in");
                return "login";
            }
        }

