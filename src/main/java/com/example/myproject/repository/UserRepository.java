package com.example.myproject.repository;

import com.example.myproject.model.Analyst;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Analyst,Integer> {

    Optional<Analyst> findByEmail(String email);
        Optional<Analyst> findByName(String name);


    }


