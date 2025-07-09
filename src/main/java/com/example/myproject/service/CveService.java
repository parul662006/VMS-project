package com.example.myproject.service;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;

public interface CveService {
    CveResponseDto uploadCveData(CveRequestDto cveRequestDto);
}
