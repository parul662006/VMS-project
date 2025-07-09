package com.example.myproject.converter;

import com.example.myproject.model.Versions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class VersionConverter implements AttributeConverter<Versions,String> {
    ObjectMapper obj=new ObjectMapper();

    //converting data in json
    @Override
        public String convertToDatabaseColumn(Versions versions){
        System.out.println("converter is running.......................");

        try{
            String v= obj.writeValueAsString(versions);
            System.out.println("Data is ................."+v);

            return v;
        }catch(JsonProcessingException e){
           throw new IllegalArgumentException("Exeception in converting version to JSON",e);
    }

}
//converting json in object data
       @Override
    public Versions convertToEntityAttribute(String data){
        try{
            return obj.readValue(data, Versions.class);
        }catch(JsonProcessingException e){
            throw new IllegalArgumentException("Exeception in reading version from JSON",e);
        }

       }

}
