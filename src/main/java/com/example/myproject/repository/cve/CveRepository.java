package com.example.myproject.repository.cve;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.model.Cve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CveRepository extends JpaRepository<Cve,Integer> {

    //custom query to get version
    @Query(value="SELECT * FROM cve_info"+
            " WHERE JSON_UNQUOTE(JSON_EXTRACT(versions, '$.cve_version_start')) <= :version "+"AND JSON_UNQUOTE(JSON_EXTRACT(versions, '$.cve_version_end')) >= :version", nativeQuery = true
    )
    List<Cve> getVersionCustom(@Param("version") String version);


    //get by cve package
    List<Cve> findByCvePackage(String cvePackage);
    //get by cve package and status=active
    List<Cve> findByCvePackageAndStatus(String cvePackage, CveStatus.cveStatus status);

    //get cve data by dept id
    List<Cve> findByDepartmentDeptId(int deptId);

    //get cve by status
    List<Cve> findByStatus(CveStatus.cveStatus status);
}
