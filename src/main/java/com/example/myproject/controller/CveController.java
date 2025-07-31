package com.example.myproject.controller;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.model.Cve;
import com.example.myproject.repository.cve.CveRepository;
import com.example.myproject.response.APIResponse;
import com.example.myproject.service.CveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "CVE APIs", description = "Operations related to CVE data")
@RestController
public class CveController {
    @Autowired
    private CveService cveService;

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private ModelMapper modelMapper;


    //get all cve data
    @Operation(summary = "Get all CVE data", description = "Retrieve all CVE records from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All CVE data fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("get-all-cveData")
    public ResponseEntity<APIResponse<List<CveResponseDto>>> getAll(){
        List<CveResponseDto> cveResponseDto=cveService.getAllCveData();
        APIResponse<List<CveResponseDto>> a=new APIResponse<List<CveResponseDto>>(
                HttpStatus.OK.value(),"All Cve data fetched :",cveResponseDto);
        return ResponseEntity.ok(a);
    }

    //upload cve-data
    @Operation(summary = "Upload CVE data", description = "Upload new CVE entry (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CVE data uploaded successfully"),
            @ApiResponse(responseCode = "403", description = "Only admin can upload CVE data"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Get CVE data by ID", description = "Retrieve a specific CVE entry by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data fetched successfully"),
            @ApiResponse(responseCode = "404", description = "CVE data not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
    @Operation(summary = "Delete CVE data by ID", description = "Delete a specific CVE record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data deleted successfully"),
            @ApiResponse(responseCode = "404", description = "CVE data not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("delete-cve-data/{id}")
    public ResponseEntity<APIResponse<String>> delete(@PathVariable int id){
        cveService.deleteDataById(id);
        APIResponse<String> ap=new APIResponse<>(
                HttpStatus.OK.value(),"data deleted successfully of this id:",null);
        return new ResponseEntity<>(ap,HttpStatus.OK);
    }

    //delete all
    @Operation(summary = "Delete all CVE data", description = "Delete all CVE entries from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All CVE data deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("delete-all-cve-data")
    public ResponseEntity<APIResponse<String>> deleteAllData(CveRequestDto cveRequestDto){
        cveService.deleteAllData(cveRequestDto);
        APIResponse<String> ap=new APIResponse<>(
                HttpStatus.OK.value()," all data deleted successfully:",null);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }

    //get cve version by param
    @Operation(summary = "Get CVE by version", description = "Retrieve CVEs based on version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE version data fetched successfully")
    })
    @GetMapping("get-version")
    public ResponseEntity<APIResponse<List<Cve>>> getVersion(@RequestParam String version){
        List<Cve> cveList=cveService.cveVersion(version);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve version fetched :",cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }

    //get cve package by param
    @Operation(summary = "Get CVE by package", description = "Retrieve CVEs based on package name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE package data fetched successfully")
    })
    @GetMapping("get-package")
    public ResponseEntity<APIResponse<List<Cve>>> getCvePackage(@RequestParam String cvePackage){
        List<Cve> cveList=cveService.cvePackage(cvePackage);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve package fetched :",cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }


    //get cve by package and status
    @Operation(summary = "Get CVE by package and status", description = "Retrieve CVEs using package name and status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data fetched successfully")
    })
    @GetMapping("/get-by-status-package")
    public ResponseEntity<APIResponse<List<Cve>>> getByPackageStatus(@RequestParam String cvePackage){
        List<Cve> cveList=cveService.cvePackage(cvePackage);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve fetched by package and status successfully:"+cvePackage,cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);
    }

    //get cve by deptId
    @Operation(summary = "Get CVE by department ID", description = "Retrieve CVEs associated with a specific department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data fetched by department ID")
    })
    @GetMapping("/get-cve-by-deptId")
    public ResponseEntity<APIResponse<List<Cve>>> getByDeptId(@RequestParam int deptId){
        List<Cve> cveList=cveService.getCveByDeptId(deptId);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve fetched by deptId successfully:"+deptId,cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);

    }

    //get cve by status
    @Operation(summary = "Get CVE by status", description = "Retrieve CVEs filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data fetched by status")
    })
    @GetMapping("/get-by-status")
    public ResponseEntity<APIResponse<List<Cve>>> getByStatus(@RequestParam CveStatus.cveStatus status){
        List<Cve> cveList=cveService.getCveByStatus(status);
        APIResponse<List<Cve>> ap=new APIResponse<>(
                HttpStatus.OK.value(),"Cve fetched by status successfully:"+status,cveList);
        return new ResponseEntity<>(ap,HttpStatus.OK);
    }

    //UPDATE CVE DATA BY PUT MAPPING
    @Operation(summary = "Update CVE data (full)", description = "Update the full CVE record by ID using PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data updated successfully"),
            @ApiResponse(responseCode = "404", description = "CVE not found")
    })
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
    @Operation(summary = "Partially update CVE", description = "Update specific fields of a CVE entry by ID using PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data partially updated successfully"),
            @ApiResponse(responseCode = "404", description = "CVE not found")
    })
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

    //search-cve
    @Operation(summary = "Search CVE data", description = "Search CVE data by ID, version, keyword, or status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CVE data fetched using search filters")
    })
    @GetMapping("/search-cve")
    public ResponseEntity<List<Cve>> searchCve(@RequestParam(required = false) String cveId,
                                               @RequestParam(required = false) String version,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(required = false) String status) {
        List<Cve> results = cveService.search(cveId, version, keyword, status);
        return ResponseEntity.ok(results);
    }
}


