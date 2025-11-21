package com.example.myproject.utility;

import com.example.myproject.globalException.MyCustomException;
import org.apache.poi.ss.usermodel.Cell;

public class UtilProgram {
    public static void checkPassword(String password,String actualPassword){
        if(!password.equals(actualPassword)){
            throw new MyCustomException.InvalidCredentialsException("Password incorrect");
        }
    }

    public static void matchPassword(String password,String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new MyCustomException.InvalidPasswordException("password doesn't match");
        }
    }


    //for file
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }



}
