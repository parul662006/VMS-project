package com.example.myproject.service.impl;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import com.example.myproject.model.Cve;
import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.model.Department;
import com.example.myproject.repository.cve.CveRepository;
import com.example.myproject.service.CveService;
import com.example.myproject.service.DeptService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CveImplementation implements CveService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeptService deptService;

    @Override
    public CveResponseDto uploadCveData(CveRequestDto cveRequestDto) {
        Cve cve = modelMapper.map(cveRequestDto, Cve.class);
        System.out.println("DTO Versions = " + cveRequestDto.getVersions());

        cve.setId(0);

        //get department by id
        Department department = deptService.getDeptById(cveRequestDto.getDeptId());

        //set department in cve
        cve.setDepartment(department);

        //generate cve-id then set
        String uuid = UUID.randomUUID().toString();
        cve.setCveId(uuid);

        //set default status and severity if not provided
        if (true) {
            cve.setStatus(CveStatus.cveStatus.ACTIVE);
        }
        //set severity
        if (true) {
            cve.setSeverity(SeverityLevel.Severity_level.MEDIUM);
        }

        //save
        Cve org_cve = cveRepository.save(cve);
        //map to responseDto
        return modelMapper.map(org_cve, CveResponseDto.class);


    }

    //get all cve-data
    public List<CveResponseDto> getAllCveData() {
        List<Cve> cve = cveRepository.findAll();
        List<CveResponseDto> list = new ArrayList<>();
        for (Cve c : cve) {
            CveResponseDto cveResponseDto = modelMapper.map(c, CveResponseDto.class);
            list.add(cveResponseDto);
        }
        return list;
    }


    //delete cve data by id
    @Override
    public void deleteDataById(int id) {
        Cve cve = cveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found data with this id" + id));
        cveRepository.delete(cve);

    }

    @Override
    public void deleteAllData(CveRequestDto cveRequestDto) {
        cveRepository.deleteAll();
    }


    //get verion method
    @Override
    public List<Cve> cveVersion(String version) {
        return cveRepository.getVersionCustom(version);
    }

    @Override
    public List<Cve> cvePackage(String cvePackage) {
        return cveRepository.findByCvePackage(cvePackage);
    }

    @Override

    public List<Cve> getCveByPackageStatus(String cvePackage){
        return cveRepository.findByCvePackageAndStatus(cvePackage,CveStatus.cveStatus.ACTIVE);
    }

    @Override
    public List<Cve> getCveByDeptId(int deptId){
        return cveRepository.findByDepartmentDeptId(deptId);
    }

    @Override
    public List<Cve> getCveByStatus(CveStatus.cveStatus status) {
        return cveRepository.findByStatus(status);
    }

    @Override
    public List<Cve> search(String cveId, String versions, String cvePackage, String status) {
        return cveRepository.search(cveId, versions, cvePackage, status);
    }

}
