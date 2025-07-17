package com.example.myproject.controller;

import com.example.myproject.model.Cve;
import com.example.myproject.model.Department;
import com.example.myproject.repository.DepartmentRepository;
import com.example.myproject.response.APIResponse;
import com.example.myproject.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private DepartmentRepository departmentRepository;

    //post dept data
    @PostMapping("/add")
    public ResponseEntity<APIResponse<Department>> addDeptData(@RequestBody Department department){
        Department department1=deptService.addDepartment(department);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Department data upload successfully", department1);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }

    //get dept by id
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Department>> getById(@PathVariable int deptId) {
        Department department = deptService.getDeptById(deptId);
        APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Department data with this id : ", department);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

        //get all dept
        @GetMapping("get-all-depts")
        public ResponseEntity<APIResponse<List<Department>>> getAll(){
            List<Department> depts=deptService.getAllDept();
            APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), "Fetched all Department data successfully", depts);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);

        }

        //delete dept by id
        @DeleteMapping("/{id}")
        public ResponseEntity<APIResponse<Department>> deleteById(@PathVariable int deptId){
             deptService.deleteDeptById(deptId);
            APIResponse apiResponse = new APIResponse<>(HttpStatus.CREATED.value(), " Department data deleted successfully of this id", deptId);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }

        //delete all
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
    @GetMapping("get-dept-with-cve")
    public ResponseEntity<APIResponse<List<Department>>> getDeptWithTheirCves(){
        List<Department> deptList=deptService.getAllDeptWithCve();
        APIResponse<List<Department>> apiResponse = new APIResponse<>(HttpStatus.OK.value(),
                "All departments fetched with their cve successfully",deptList
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    //data update by patch
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





