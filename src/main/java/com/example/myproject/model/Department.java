package com.example.myproject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dept_id")
    private int deptId;
    @NotBlank(message="deptName is required")
    private String deptName;
    @Email(message="Email format is wrong")
    private String deptEmail;
    @NotBlank(message="deptAddress is required")
    private String deptAddress;
    @NotNull(message="deptPhone is required")
    private long deptPhone;
    @NotBlank(message="proprietorName is required")
    private String proprietorName;
    @NotBlank(message="establishedDate  is required")
    private String establishedDate ;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Cve> cve;

}
