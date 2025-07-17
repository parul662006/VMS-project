package com.example.myproject.service;

import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.model.Department;

import java.util.List;

public interface DeptService {
    Department getDeptById(int deptId);
    Department addDepartment(Department department);

    //get all
    List<Department> getAllDept();


    //delete cve data by id
    void deleteDeptById(int deptId);

    //delete all
    void deleteAllDepartments();

    //get all department along with cve
    List<Department> getAllDeptWithCve();



}
