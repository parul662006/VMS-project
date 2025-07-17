package com.example.myproject.service.impl;

import com.example.myproject.model.Department;
import com.example.myproject.repository.DepartmentRepository;
import com.example.myproject.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptImplementation implements DeptService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department getDeptById(int deptId){
        return departmentRepository.findById(deptId)
                .orElseThrow(()->new RuntimeException("department not found with this id"+deptId));
    }

    @Override
    public Department addDepartment(Department department){
        return departmentRepository.save(department);
    }

    // get all dept
    @Override
    public List<Department> getAllDept(){
        return departmentRepository.findAll();
    }

    // delete dept by id
    @Override
    public void deleteDeptById(int deptId){
        Department department=departmentRepository.findById(deptId)
                .orElseThrow(()-> new RuntimeException("not found dept with this id"+deptId));
        departmentRepository.delete(department);
    }

    @Override
    public void deleteAllDepartments(){
        departmentRepository.deleteAll();
    }

    @Override
    public List<Department> getAllDeptWithCve(){
        return departmentRepository.findAll();
    }

}
