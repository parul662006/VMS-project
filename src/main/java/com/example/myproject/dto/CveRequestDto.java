package com.example.myproject.dto;


import com.example.myproject.model.Versions;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CveRequestDto {
    @NotBlank(message = "cve_data is required")
    private String cve_data;
    @NotBlank(message = "cve_package is required")
    private String cve_package;

    private Versions versions;
    @NotBlank(message = "cve_description is required")
    private String cve_description;
    @NotBlank(message = "role is required")
    private String role;


    public String getRole() {
        return role;
    }

    public String getCve_data() {
        return cve_data;
    }

    public void setCve_data(String cve_data) {
        this.cve_data = cve_data;
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

    public void setRole(String role) {
        this.role = role;
    }
}

