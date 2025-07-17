package com.example.myproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class VerificationToken {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String token;
    @OneToOne
    private Analyst analyst;
    private LocalDateTime expiryDate;
    public void setExpiryDate(int day) {
        this.expiryDate = LocalDateTime.now().plusDays(day);
    }



}
