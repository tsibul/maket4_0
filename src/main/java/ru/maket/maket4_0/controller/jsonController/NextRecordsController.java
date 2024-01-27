package ru.maket.maket4_0.controller.jsonController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maket.maket4_0.db.dictionary.*;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.maket.maket4_0.db.dictionary.sqbuilder.NextTwentyRecordsSQLBuilder;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;

@Controller
public class NextRecordsController {

    @Autowired
    private EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NextRecordsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/next_20/{className}/{lastRecord}/{searchString}/{shDeleted}")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> nextRecordJson(@PathVariable String className, @PathVariable String lastRecord,
                                                                    @PathVariable String searchString, @PathVariable String shDeleted)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends MaketDictionary> dictClass = dictionaryList().get(className);
        EntityType<?> entityType = entityManager.getMetamodel().entity(dictClass);
        Set<SingularAttribute<?, ?>> fieldSet = (Set<SingularAttribute<?, ?>>) entityType.getSingularAttributes();
        NextTwentyRecordsSQLBuilder nextTwentyRecordsSQLBuilder =
                new NextTwentyRecordsSQLBuilder(fieldSet, className, shDeleted, searchString, lastRecord);
        String sqlRequest = nextTwentyRecordsSQLBuilder.totalSQLRequest();
        List<Map<String, Object>> itemsList = jdbcTemplate.queryForList(sqlRequest);

        return ResponseEntity.ok(itemsList);
    }


    private static ArrayList<HashMap<String, Object>> reformatQueryToMap(List records, Set<SingularAttribute<?, ?>> fieldSet) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (Object record : records) {
            HashMap<String, Object> map = new HashMap<>();
            Field fieldR;
            for (SingularAttribute<?, ?> field : fieldSet) {
                String attributeName = field.getName();
                try {
                    fieldR = record.getClass().getDeclaredField(attributeName);
                } catch (Exception e) {
                    fieldR = record.getClass().getSuperclass().getDeclaredField(attributeName);
                }
                fieldR.setAccessible(true);
                String value = fieldR.get(record).toString();
                map.put(attributeName, value);
            }
            hashMapList.add(map);
        }
        return hashMapList;
    }

}
