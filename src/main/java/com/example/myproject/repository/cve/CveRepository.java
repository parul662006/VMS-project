package com.example.myproject.repository.cve;

import com.example.myproject.model.Cve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CveRepository extends JpaRepository<Cve,Integer> {

    Optional<Cve> findById(int id);
}
