package com.example.myproject.model;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CveResponseDto {
    private int id;
    private String cveId;

    private String cve_description;

    private String cve_version_start;
    private String cve_version_end;

    private SeverityLevel.Severity_level severity;
    private CveStatus.cve_status status;
    private LocalDateTime created_at;
}
