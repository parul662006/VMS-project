package com.example.myproject.controller;

import com.example.myproject.model.CveRequestDto;
import com.example.myproject.model.CveResponseDto;
import com.example.myproject.service.CveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CveController {
    @Autowired
    private CveService cveService;

    //upload cve-data
    @PostMapping("/cve-data")
    public ResponseEntity<?> uploadCveInfo(@Valid @RequestBody CveRequestDto dto){
        if (!"ADMIN".equals(dto.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can upload CVE data.");
        }
        CveResponseDto responseDto = cveService.uploadCveData(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
