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

        private String cve_description;

        private Versions versions;

        private SeverityLevel.Severity_level severity;
        private CveStatus.cve_status status;
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

        public String getCve_description() {
            return cve_description;
        }

        public void setCve_description(String cve_description) {
            this.cve_description = cve_description;
        }

        public Versions getVersions() {
            return versions;
        }

        public void setVersions(Versions versions) {
            this.versions = versions;
        }

        public SeverityLevel.Severity_level getSeverity() {
            return severity;
        }

        public void setSeverity(SeverityLevel.Severity_level severity) {
            this.severity = severity;
        }

        public CveStatus.cve_status getStatus() {
            return status;
        }

        public void setStatus(CveStatus.cve_status status) {
            this.status = status;
        }

        public LocalDateTime getCreated_at() {
            return created_at;
        }

        public void setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
        }
    }
