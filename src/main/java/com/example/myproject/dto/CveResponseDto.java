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

        public String getCveDescription() {
            return cveDescription;
        }

        public void setCveDescription(String cveDescription) {
            this.cveDescription = cveDescription;
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


        public CveStatus.cveStatus getStatus() {
            return status;
        }

        public void setStatus(CveStatus.cveStatus status) {
            this.status = status;
        }

        public LocalDateTime getCreated_at() {
            return created_at;
        }

        public void setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
        }
    }
