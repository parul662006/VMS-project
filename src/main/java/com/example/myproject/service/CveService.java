package com.example.myproject.service;

import com.example.myproject.model.CveRequestDto;
import com.example.myproject.model.CveResponseDto;

public interface CveService {
    CveResponseDto uploadCveData(CveRequestDto cveRequestDto);
}
