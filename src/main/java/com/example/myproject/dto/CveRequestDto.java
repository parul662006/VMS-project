package com.example.myproject.dto;


import com.example.myproject.enumCode.EnumProgram;
import com.example.myproject.model.Versions;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CveRequestDto {

    private Integer id;

    @NotBlank(message = "cve_data is required")
    private String cveData;
    @NotBlank(message = "cve_package is required")
    private String cvePackage;

    private Versions versions;
    @NotBlank(message = "cve_description is required")
    private String cveDescription;
    @NotBlank(message = "role is required")
    private EnumProgram.Role role;

    //department id
    private Integer  deptId;

    public EnumProgram.Role getRole() {
        return role;
    }

    public void setRole(EnumProgram.Role role) {
        this.role = role;
    }
}

