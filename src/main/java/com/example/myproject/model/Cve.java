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

    @Column(name = "cve_id", unique = true, nullable = false)
    private String cveId;

    @Enumerated(EnumType.STRING)
    private CveStatus.cve_status status;

    @NotBlank(message = "cve_Data is required")
    private String cve_data;

    @Enumerated(EnumType.STRING)
    private SeverityLevel.Severity_level severity;

    @NotBlank(message = "package is required")
    private String cve_package;


    @Convert(converter= VersionConverter.class)
    @Column(columnDefinition = "JSON", nullable = false)
    private Versions versions;

    @NotBlank(message = "Description is required")
    private String cve_description;

    @CreationTimestamp
    private LocalDateTime created_at;

    public String getCve_id() {
        return cveId;
    }

    public void setCve_id(String cve_id) {
        this.cveId = cve_id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CveStatus.cve_status getStatus() {
        return status;
    }

    public void setStatus(CveStatus.cve_status status) {
        this.status = status;
    }

    public String getCve_data() {
        return cve_data;
    }

    public void setCve_data(String cve_data) {
        this.cve_data = cve_data;
    }

    public SeverityLevel.Severity_level getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel.Severity_level severity) {
        this.severity = severity;
    }

    public String getCve_package() {
        return cve_package;
    }

    public void setCve_package(String cve_package) {
        this.cve_package = cve_package;
    }

    public Versions getVersions() {
        return versions;
    }

    public void setVersions(Versions versions) {
        this.versions = versions;
    }

    public String getCve_description() {
        return cve_description;
    }

    public void setCve_description(String cve_description) {
        this.cve_description = cve_description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
