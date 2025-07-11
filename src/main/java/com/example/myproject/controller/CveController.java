package com.example.myproject.controller;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.model.Cve;
import com.example.myproject.repository.cve.CveRepository;
import com.example.myproject.response.APIResponse;
import com.example.myproject.service.CveService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CveController {
    @Autowired
    private CveService cveService;

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;


    //get all cve data
    @GetMapping("get-all-cveData")
    public ResponseEntity<APIResponse<List<CveResponseDto>>> getAll(){
        List<CveResponseDto> cveResponseDto=cveService.getAllCveData();
        APIResponse<List<CveResponseDto>> a=new APIResponse<List<CveResponseDto>>(
                HttpStatus.OK.value(),"All Cve data fetched :",cveResponseDto);
        return ResponseEntity.ok(a);
    }

    //upload cve-data
    @PostMapping("/cve-data")

    public ResponseEntity<APIResponse<Cve>> uploadCveInfo(@Valid @RequestBody CveRequestDto dto) {
       //if role isn't admin will show this message
        if (!"ADMIN".equals(dto.getRole())) {
            APIResponse<String> ap=new APIResponse<>(
            HttpStatus.FORBIDDEN.value(),"Only admin can upload CVE data.",null);
            return new ResponseEntity(ap,HttpStatus.BAD_REQUEST);
        }
        CveResponseDto cveResponseDto = cveService.uploadCveData(dto);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "cve data upload successfully", cveResponseDto);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    //get cve data by id
    @GetMapping("get-cve-data/{id}")
    public ResponseEntity<APIResponse<CveResponseDto>> getCveDataById(@PathVariable int id){
        Optional<Cve> getCveData = cveRepository.findById(id);

        if(getCveData.isEmpty()){
            APIResponse<String> ap=new APIResponse<>(
            HttpStatus.NOT_FOUND.value(),"Cve data not found with this id :"+id,null);
            return new ResponseEntity(ap,HttpStatus.NOT_FOUND);
        }else{

            //if id is present will give us
            Cve cve=getCveData.get();

            //convert entity into responseDto
            CveResponseDto cveResponseDto=modelMapper.map(cve, CveResponseDto.class);

            APIResponse<CveResponseDto> ap=new APIResponse<>(
                    HttpStatus.OK.value(),"Cve data fetched :",cveResponseDto);
            return new ResponseEntity<>(ap,HttpStatus.OK);

        }
    }




}


