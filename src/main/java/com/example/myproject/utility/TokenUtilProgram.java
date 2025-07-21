package com.example.myproject.utility;

import com.example.myproject.model.Analyst;
import com.example.myproject.model.VerificationToken;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
@Component
public class TokenUtilProgram {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    //generate token
    public String generateToken() {
        Random random = new Random();
        int token = 1000 + random.nextInt(8000);
        return String.valueOf(token);
    }


    //email send
    public void sendVerificationEmail(String toEmail,String token){
        String subject="verify your email";
        String verificationUrl="http://localhost:9094/api/auth/verify?token="+token;
        String body="Hi! click the link to verify your account :\n"+verificationUrl;


        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
    }

    public String verifyEmailByToken(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            VerificationToken verificationToken = optionalToken.get();
            Analyst analyst = verificationToken.getAnalyst();

            if (!analyst.getVerified()) {
                analyst.setVerified(true);
                userRepository.save(analyst);
            }

            return "Email verified successfully!";
        } else {
            return "Invalid or expired token!";
        }
    }
}
