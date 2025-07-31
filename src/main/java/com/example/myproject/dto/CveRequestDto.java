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

    //department id
    private Integer  deptId;

}

