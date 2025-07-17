package com.example.myproject.controller;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.model.Analyst;
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


    //delete by id
    @DeleteMapping("delete-cve-data/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable int id){
        cveService.deleteDataById(id);
        APIResponse<String> ap=new APIResponse<>(
                HttpStatus.OK.value(),"data deleted successfully of this id:",null);
        return new ResponseEntity<>(ap,HttpStatus.OK);

//        return ResponseEntity.ok("data deleted successfully of id : "+id);
    }

    //delete all
    @DeleteMapping("delete-all-cve-data")
    public ResponseEntity<APIResponse<String>> deleteAllData(CveRequestDto cveRequestDto){
        cveService.deleteAllData(cveRequestDto);
        APIResponse<String> ap=new APIResponse<>(
                HttpStatus.OK.value()," all data deleted successfully:",null);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }

    //get cve version by param
    @GetMapping("get-version")
    public ResponseEntity<APIResponse<List<Cve>>> getVersion(@RequestParam String version){
        List<Cve> cveList=cveService.cveVersion(version);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve version fetched :",cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }

    //get cve package by param
    @GetMapping("get-package")
    public ResponseEntity<APIResponse<List<Cve>>> getCvePackage(@RequestParam String cvePackage){
        List<Cve> cveList=cveService.cvePackage(cvePackage);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve package fetched :",cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }


    //get cve by package and status
    @GetMapping("/get-by-status-package")
    public ResponseEntity<APIResponse<List<Cve>>> getByPackageStatus(@RequestParam String cvePackage){
        List<Cve> cveList=cveService.cvePackage(cvePackage);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve fetched by package and status successfully:"+cvePackage,cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);
    }

    //get cve by deptId
    @GetMapping("/get-cve-by-deptId")
    public ResponseEntity<APIResponse<List<Cve>>> getByDeptId(@RequestParam int deptId){
        List<Cve> cveList=cveService.getCveByDeptId(deptId);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve fetched by deptId successfully:"+deptId,cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }

    //get cve by status
    @GetMapping("/get-by-status")
    public ResponseEntity<APIResponse<List<Cve>>> getByStatus(@RequestParam CveStatus.cveStatus status){
        List<Cve> cveList=cveService.getCveByStatus(status);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve fetched by status successfully:"+status,cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);
    }

    //UPDATE CVE DATA BY PUT MAPPING
    @PutMapping("update-data/{id}")
    public ResponseEntity<APIResponse<Cve>> updateCveData(@PathVariable int id,@RequestBody Cve updatedCveData){
        Cve cve=cveRepository.findById(id)
                .orElseThrow(()->new RuntimeException("cve not found with this id :"+id));

        cve.setCveData(updatedCveData.getCveData());
        cve.setCvePackage(updatedCveData.getCvePackage());
        cve.setCveDescription(updatedCveData.getCveDescription());
        cve.setVersions(updatedCveData.getVersions());
        cve.setSeverity(updatedCveData.getSeverity());

        Cve savedData=cveRepository.save(cve);
        APIResponse<Cve> ap=new APIResponse<>(
                HttpStatus.OK.value(),"update data successfully of this id:"+""+id,savedData);
        return new ResponseEntity<>(ap,HttpStatus.OK);


    }

    //data update by patch
    @PatchMapping("cve/update/{id}")
    public ResponseEntity<APIResponse<Cve>> updateUserDataPartially(@PathVariable int id, @RequestBody Cve updatedCve){
        Cve cve=cveRepository.findById(id)
                .orElseThrow(()->new RuntimeException("cve data not found of this id :"+" "+id));
        if(updatedCve.getCveData()!=null){
            cve.setCveData(updatedCve.getCveData());
        }if(updatedCve.getSeverity()!=null){
            cve.setSeverity(updatedCve.getSeverity());
        }if(updatedCve.getCveDescription()!=null){
            cve.setCveDescription(updatedCve.getCveDescription());
        }if(updatedCve.getCvePackage()!=null){
            cve.setCvePackage(updatedCve.getCvePackage());
        }
        //save
        cveRepository.save(cve);
        APIResponse<Cve> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Cve data updated successfully of this id"+" "+id, cve);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}


