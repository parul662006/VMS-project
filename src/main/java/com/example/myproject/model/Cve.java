package com.example.myproject.model;

import com.example.myproject.converter.VersionConverter;
import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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


    @Convert(converter= VersionConverter.class)
    @Column(columnDefinition = "JSON", nullable = false)
    private Versions versions;


    @ManyToOne
    @JsonBackReference
    private Department department;


    @NotBlank(message = "Description is required")
    private String cveDescription;

    @CreationTimestamp
    private LocalDateTime created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }

    public CveStatus.cveStatus getStatus() {
        return status;
    }

    public void setStatus(CveStatus.cveStatus status) {
        this.status = status;
    }

    public String getCveData() {
        return cveData;
    }

    public void setCveData(String cveData) {
        this.cveData = cveData;
    }

    public SeverityLevel.Severity_level getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel.Severity_level severity) {
        this.severity = severity;
    }

    public String getCvePackage() {
        return cvePackage;
    }

    public void setCvePackage(String cvePackage) {
        this.cvePackage = cvePackage;
    }

    public Versions getVersions() {
        return versions;
    }

    public void setVersions(Versions versions) {
        this.versions = versions;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getCveDescription() {
        return cveDescription;
    }

    public void setCveDescription(String cveDescription) {
        this.cveDescription = cveDescription;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
