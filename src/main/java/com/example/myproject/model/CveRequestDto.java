package com.example.myproject.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CveRequestDto {
    @NotBlank(message="cve_data is required")
    private String cve_data;
    @NotBlank(message="cve_package is required")
    private String cve_package;
    @NotBlank(message="cve_version is required")
    private String cve_version_start;
    @NotBlank(message="cve_version is required")
    private String cve_version_end;
    @NotBlank(message="cve_description is required")
    private String cve_description;
    @NotBlank(message="role is required")
    private String role;


    public String getRole() {
        return role;
    }
}
