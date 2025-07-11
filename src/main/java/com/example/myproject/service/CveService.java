package com.example.myproject.service;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;

import java.util.List;

public interface CveService {
    CveResponseDto uploadCveData(CveRequestDto cveRequestDto);
    List<CveResponseDto> getAllCveData();




}
