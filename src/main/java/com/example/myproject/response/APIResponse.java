package com.example.myproject.response;

import com.example.myproject.enumCode.CveStatus;
import com.example.myproject.model.Cve;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    public LocalDateTime timestamp=LocalDateTime.now();

    public  int status;
     public String message;
     T object;

    public APIResponse(int status,String message,T object){
        this.timestamp = LocalDateTime.now();
        this.status=status;
        this.message=message;
        this.object=object;

    }


}
