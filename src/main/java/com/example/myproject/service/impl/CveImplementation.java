package com.example.myproject.service.impl;

import com.example.myproject.config.ModelMapperConfig;
import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.SeverityLevel;
import com.example.myproject.model.Cve;
import com.example.myproject.model.CveRequestDto;
import com.example.myproject.model.CveResponseDto;
import com.example.myproject.repository.cve.CveRepository;
import com.example.myproject.service.CveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        //generate cve-id then set
        String cve_id= UUID.randomUUID().toString();
        cve.setCve_id(cve_id);

        //set default status and severity if not provided
        if(true){
            cve.setStatus(CveStatus.cve_status.ACTIVE);
        }
        //set severity
        if(cve.getSeverity()==null){
            cve.setSeverity(SeverityLevel.Severity_level.MEDIUM);
        }

        //save
        Cve savedInfo=cveRepository.save(cve);
        return modelMapper.map(savedInfo,CveResponseDto.class);
    }


}
