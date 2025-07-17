package com.example.myproject.service;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.dto.UserRequestDto;
import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.model.Analyst;
import com.example.myproject.model.Cve;
import com.example.myproject.model.Department;

import java.util.List;

public interface CveService {
    CveResponseDto uploadCveData(CveRequestDto cveRequestDto);
    List<CveResponseDto> getAllCveData();

    //delete cve data by id
    void deleteDataById(int id);

    //delete all
    void deleteAllData(CveRequestDto cveRequestDto);

    //get version by param
    List<Cve> cveVersion(String version);

    //get cve package
    List<Cve> cvePackage(String cve_package);

    //get package based on status
    List<Cve> getCveByPackageStatus(String cve_package);

    //get cve By deptId
    List<Cve> getCveByDeptId(int deptId);

    //get cve by status
    List<Cve> getCveByStatus(CveStatus.cveStatus status);


}
