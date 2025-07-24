package com.example.myproject.model;

import com.example.myproject.converter.VersionConverter;
import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="cve_info")
@Getter
@Setter

public class Cve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String cveId;

    @Enumerated(EnumType.STRING)
    private CveStatus.cveStatus status;

    @NotBlank(message = "cve_Data is required")
    private String cveData;

    @Enumerated(EnumType.STRING)
    private SeverityLevel.Severity_level severity;

    @NotBlank(message = "package is required")
    private String cvePackage;


    @Convert(converter = VersionConverter.class)
    @Column(columnDefinition = "JSON", nullable = false)
    private Versions versions;


    @ManyToOne
    @JsonBackReference
    private Department department;


    @NotBlank(message = "Description is required")
    private String cveDescription;

    @CreationTimestamp
    private LocalDateTime created_at;

}