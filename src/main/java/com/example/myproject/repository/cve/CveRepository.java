package com.example.myproject.repository.cve;

import com.example.myproject.model.Cve;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CveRepository extends JpaRepository<Cve,Integer> {

}
