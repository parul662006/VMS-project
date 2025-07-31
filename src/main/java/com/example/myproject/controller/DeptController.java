package com.example.myproject.controller;


import com.example.myproject.model.Department;
import com.example.myproject.repository.DepartmentRepository;
import com.example.myproject.response.APIResponse;
import com.example.myproject.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Department APIs", description = "Operations related to Department data")
@RestController
@RequestMapping("/department")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private DepartmentRepository departmentRepository;

    //post dept data
    @Operation(summary = "Add department data", description = "Adds a new department record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Department data uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid department data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<APIResponse<Department>> addDeptData(@RequestBody Department department){
        Department department1=deptService.addDepartment(department);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Department data upload successfully", department1);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    //get dept by id
    @Operation(summary = "Get department by ID", description = "Retrieves department details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department data fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Department>> getById(@PathVariable int deptId) {
        Department department = deptService.getDeptById(deptId);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Department data with this id : ", department);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

        //get all dept
        @Operation(summary = "Get all departments", description = "Fetches all department records")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "All departments fetched successfully"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @GetMapping("get-all-depts")
        public ResponseEntity<APIResponse<List<Department>>> getAll(){
            List<Department> depts=deptService.getAllDept();
            APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Fetched all Department data successfully", depts);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);

        }

        //delete dept by id
        @Operation(summary = "Delete department by ID", description = "Deletes a specific department by ID")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Department deleted successfully"),
                @ApiResponse(responseCode = "404", description = "Department not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<APIResponse<Department>> deleteById(@PathVariable int deptId){
             deptService.deleteDeptById(deptId);
            APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), " Department data deleted successfully of this id", deptId);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }

        //delete all
        @Operation(summary = "Delete all departments", description = "Deletes all department records from the system")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "All department data deleted successfully"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
        @DeleteMapping("delete-all-dept")
    public ResponseEntity<APIResponse<String>> deleteAll(){
        deptService.deleteAllDepartments();
            APIResponse<String> apiResponse = new APIResponse<>(
                    HttpStatus.OK.value(),
                    "All departments deleted successfully",
                    null
            );
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }

        //get department with cve
        @Operation(summary = "Get departments with CVE data", description = "Fetches all departments along with their associated CVE data")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Departments with CVE data fetched successfully"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
        })
    @GetMapping("get-dept-with-cve")
    public ResponseEntity<APIResponse<List<Department>>> getDeptWithTheirCves(){
        List<Department> deptList=deptService.getAllDeptWithCve();
        APIResponse<List<Department>> apiResponse = new APIResponse<>(HttpStatus.OK.value(),
                "All departments fetched with their cve successfully",deptList
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    //data update by patch
    @Operation(summary = "Partially update department data", description = "Updates specific fields of a department by ID using PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department data updated successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("department/update/{id}")
    public ResponseEntity<APIResponse<Department>> updateDepartmentDataPartially(@PathVariable int id, @RequestBody Department updatedDepartment){
        Department department=departmentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Department not found of this id :"+" "+id));
        if(updatedDepartment.getDeptName()!=null){
            department.setDeptName(updatedDepartment.getDeptName());
        }if(updatedDepartment.getDeptEmail()!=null){
            department.setDeptEmail(updatedDepartment.getDeptEmail());
        }if(updatedDepartment.getDeptAddress()!=null){
            department.setDeptAddress(updatedDepartment.getDeptAddress());
        }if(updatedDepartment.getProprietorName()!=null){
            department.setProprietorName(updatedDepartment.getProprietorName());
        }if(updatedDepartment.getEstablishedDate()!=null){
            department.setEstablishedDate(updatedDepartment.getEstablishedDate());
        }
        //save
        departmentRepository.save(department);
        APIResponse<Department> apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Cve data updated successfully of this id"+" "+id, department);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}





