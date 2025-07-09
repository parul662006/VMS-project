package com.example.myproject.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Versions {

    private String cve_version_start;


    private String cve_version_end;

}
