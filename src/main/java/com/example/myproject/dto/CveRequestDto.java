package com.example.myproject.dto;


import com.example.myproject.model.Versions;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CveRequestDto {
    @NotBlank(message = "cve_data is required")
    private String cveData;
    @NotBlank(message = "cve_package is required")
    private String cvePackage;

    private Versions versions;
    @NotBlank(message = "cve_description is required")
    private String cveDescription;
    @NotBlank(message = "role is required")
    private String role;

    //ddepartment id
    private Integer  deptId;


    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getCveData() {
        return cveData;
    }

    public void setCveData(String cveData) {
        this.cveData = cveData;
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

    public String getCveDescription() {
        return cveDescription;
    }

    public void setCveDescription(String cveDescription) {
        this.cveDescription = cveDescription;
    }


}

