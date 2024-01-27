package ru.maket.maket4_0.controller.jsonController;

import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;
import static ru.maket.maket4_0.db.dictionary.FieldNames.dataBaseNames;

@Controller
public class UpdateRecordController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UpdateRecordController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/dict_update/{className}")
    @ResponseBody
    public void dictionaryUpdate(@PathVariable String className, HttpServletRequest request) {
        String dbName = className;
        if (dataBaseNames().get(className) != null) dbName = dataBaseNames().get(className);
        Map<String, String[]> formData = request.getParameterMap();
        formData.forEach((key, value) -> {
            String fieldSimpleType = fieldSimpleType(className, key);
            if (!isFieldNullable(className, key) && value[0].isEmpty()) return;
            else if (fieldSimpleType.equals("int") && !tryParseInt(key)) return;
            else if (fieldSimpleType.equals("Date") && !tryParseDate(key)) return;
//            else if (fieldSimpleType.equals("Date") && )
            if (tryParseLong(formData.get("id")[0]) > 0L) {
            }
        });


        //        String sqlRequest = "UPDATE " + dbName + " SET deleted = true WHERE id = " + idNo;
//        jdbcTemplate.update(sqlRequest);
    }

    private static Field getCurrentField(String className, String fieldName){
        Class<? extends MaketDictionary> dictClass = dictionaryList().get(className);
        return ReflectionUtils.findRequiredField(dictClass, fieldName);
    }


    private static boolean isFieldNullable(String className, String fieldName) {
        Field field = getCurrentField(className, fieldName);
        Column columnAnnotation = field.getAnnotation(Column.class);
        return columnAnnotation != null && columnAnnotation.nullable();
    }

    private static String fieldSimpleType(String className, String fieldName) {
        Field field = getCurrentField(className, fieldName);
        return field.getType().getSimpleName();
    }

    private static Long tryParseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    private static boolean tryParseInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static boolean tryParseDate(String str) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
            dateFormat.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
