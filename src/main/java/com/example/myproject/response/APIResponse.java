package com.example.myproject.response;
import lombok.Data;
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
