package com.example.myproject.service.impl;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.enumCode.EnumProgram;
import com.example.myproject.enumCode.SeverityLevel;
import com.example.myproject.model.Cve;
import com.example.myproject.dto.CveRequestDto;
import com.example.myproject.dto.CveResponseDto;
import com.example.myproject.model.Department;
import com.example.myproject.model.Versions;
import com.example.myproject.repository.DepartmentRepository;
import com.example.myproject.repository.cve.CveRepository;
import com.example.myproject.service.CveService;
import com.example.myproject.service.DeptService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

    @Autowired
    private DepartmentRepository departmentRepository;


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

    @Override
        public void uploadFromExcel(MultipartFile file) throws Exception {
        System.out.println("Received file: " + file.getOriginalFilename());

        List<Cve> cveList = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    Cve cve = new Cve();
                    cve.setCveData(getCellValue(row.getCell(0))); // CVE ID auto-generated in DB

                    cve.setCveDescription(getCellValue(row.getCell(1)));
                    cve.setCvePackage(getCellValue(row.getCell(2)));

                    Versions versions = new Versions();
                    versions.setCve_version_start(getCellValue(row.getCell(3)));
                    versions.setCve_version_end(getCellValue(row.getCell(4)));
                    cve.setVersions(versions);

                    cve.setSeverity(SeverityLevel.Severity_level.valueOf(getCellValue(row.getCell(5)).toUpperCase()));
                    cve.setStatus(CveStatus.cveStatus.valueOf(getCellValue(row.getCell(6)).toUpperCase()));
                    cve.setRole(EnumProgram.Role.valueOf(getCellValue(row.getCell(7)).toUpperCase()));

                    int deptId = Integer.parseInt(getCellValue(row.getCell(8)));
                    Department department = departmentRepository.findById(deptId)
                            .orElseThrow(() -> new RuntimeException("Invalid Department ID: " + deptId));
                    cve.setDepartment(department);

                    //Generate UUID here
                    String uuid = UUID.randomUUID().toString();
                    cve.setCveId(uuid);

                    cve.setCreated_at(LocalDateTime.now());

                    cveList.add(cve);
                } catch (Exception ex) {
                    throw new RuntimeException("Error in row " + (i + 1) + ": " + ex.getMessage());
                }
            }

            cveRepository.saveAll(cveList);
        }

        // Helper method to safely extract cell value as string
        private String getCellValue(Cell cell) {
            if (cell == null) return "";
            switch (cell.getCellType()) {
                case STRING: return cell.getStringCellValue().trim();
                case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
                case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
                default: return "";
            }
        }

    @Override
    public void updateCveData(CveRequestDto dto) {
        Cve existingCve = cveRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("CVE with ID " + dto.getId() + " not found."));

        // Fields update only if they're not null/blank
        if (dto.getCveData() != null && !dto.getCveData().isBlank()) {
            existingCve.setCveData(dto.getCveData());
        }
        if (dto.getCvePackage() != null && !dto.getCvePackage().isBlank()) {
            existingCve.setCvePackage(dto.getCvePackage());
        }
        if (dto.getVersions() != null) {
            existingCve.setVersions(dto.getVersions());
        }
        if (dto.getCveDescription() != null && !dto.getCveDescription().isBlank()) {
            existingCve.setCveDescription(dto.getCveDescription());
        }
        if (dto.getRole() != null) {
            existingCve.setRole(dto.getRole());
        }

        // Save updated entity
        cveRepository.save(existingCve);
    }
    }




