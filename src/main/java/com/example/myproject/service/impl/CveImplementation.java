package com.example.myproject.service.impl;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import com.example.myproject.model.Cve;
import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.repository.cve.CveRepository;
import com.example.myproject.service.CveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CveImplementation implements CveService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CveResponseDto uploadCveData(CveRequestDto cveRequestDto){
        Cve cve=modelMapper.map(cveRequestDto, Cve.class);
        System.out.println("DTO Versions = " + cveRequestDto.getVersions());


        //generate cve-id then set
        String uuid= UUID.randomUUID().toString();
        cve.setCve_id(uuid);

        //set default status and severity if not provided
        if(true){
            cve.setStatus(CveStatus.cve_status.ACTIVE);
        }
        //set severity
        if(true){
            cve.setSeverity(SeverityLevel.Severity_level.MEDIUM);
        }

        //save
      Cve org_cve=   cveRepository.save(cve);
        //map to responseDto
      return  modelMapper.map(org_cve, CveResponseDto.class);


    }

    //get all cve-data
    public List<CveResponseDto> getAllCveData(){
    List<Cve> cve=cveRepository.findAll();
    List<CveResponseDto> list=new ArrayList<>();
    for(Cve c:cve){
        CveResponseDto cveResponseDto=modelMapper.map(c, CveResponseDto.class);
        list.add(cveResponseDto);
    }
        return list;
    }


}
