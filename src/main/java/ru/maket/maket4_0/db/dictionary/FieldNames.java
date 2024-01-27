package ru.maket.maket4_0.db.dictionary;

import java.util.HashMap;

public class FieldNames {

    public static HashMap<String, String> fieldNames(){
        HashMap<String, String> fieldNameList = new HashMap<>();
        fieldNameList.put("colorScheme", "color_scheme");
        fieldNameList.put("crmType", "crmType");
        fieldNameList.put("customerGroup", "customer_group");
        fieldNameList.put("customerType", "customer_type");
        fieldNameList.put("detailName", "detail_name");
        fieldNameList.put("matrixType", "matrix_type");
        fieldNameList.put("printPlace", "print_place");
        fieldNameList.put("printPosition", "print_position");
        fieldNameList.put("printType", "print_type");

        fieldNameList.put("publicName", "public_name");
        return fieldNameList;
    }

    public static HashMap<String, String> dataBaseNames(){
        HashMap<String, String> fieldNameList = new HashMap<>();
        fieldNameList.put("ColorScheme", "color_scheme");
        fieldNameList.put("CrmType", "crmType");
        fieldNameList.put("CustomerGroup", "customer_group");
        fieldNameList.put("CustomerType", "customer_type");
        fieldNameList.put("DetailName", "detail_name");
        fieldNameList.put("MatrixType", "matrix_type");
        fieldNameList.put("PrintPlace", "print_place");
        fieldNameList.put("PrintPosition", "print_position");
        fieldNameList.put("PrintType", "print_type");

        return fieldNameList;
    }


}
