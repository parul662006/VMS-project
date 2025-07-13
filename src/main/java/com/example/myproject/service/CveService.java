package com.example.myproject.service;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.model.Analyst;

import java.util.List;

public interface CveService {
    CveResponseDto uploadCveData(CveRequestDto cveRequestDto);
    List<CveResponseDto> getAllCveData();

    //delete cve data by id
    void deleteDataById(int id);

    //delete all
    void deleteAllData(CveRequestDto cveRequestDto);




}
