package com.example.myproject.dto;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import com.example.myproject.model.Versions;
import lombok.Data;

import java.time.LocalDateTime;


    @Data
    public  class CveResponseDto {
        private int id;
        private String cveId;


        private String cveDescription;

        private Versions versions;

        private SeverityLevel.Severity_level severity;
        private CveStatus.cveStatus status;
        private LocalDateTime created_at;

    }
