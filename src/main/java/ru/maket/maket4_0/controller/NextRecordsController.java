package ru.maket.maket4_0.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maket.maket4_0.db.dictionary.*;
import ru.maket.maket4_0.db.dictionary.color.repository.*;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
public class NextRecordsController<e> {

    @Autowired
    private EntityManager entityManager;

    private final ColorRepository colorRepository;
    private final ColorSchemeRepository colorSchemeRepository;

    public NextRecordsController(ColorSchemeRepository colorSchemeRepository, ColorRepository colorRepository) {
        this.colorSchemeRepository = colorSchemeRepository;
        this.colorRepository = colorRepository;
    }

    @GetMapping("/next_20/{className}/{lastRecord}/{searchString}/{shDeleted}")
    @ResponseBody
    public ResponseEntity nextRecordJson(HttpServletRequest request,
                                         @PathVariable String className, @PathVariable String lastRecord,
                                         @PathVariable String searchString, @PathVariable String shDeleted)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        boolean shDeletedBool = !shDeleted.equals("0");
        Integer lastRecordInt = Integer.parseInt(lastRecord);
        searchString = searchString.replace("_", " ");
        DictionaryList dictList = new DictionaryList(colorRepository, colorSchemeRepository);
        Class<?> dictClass = (Class<?>) dictList.dictionaryList().get(className);
        Sort order = (Sort) dictClass.getMethod("defaultOrder").invoke(null);
        MaketDictionaryRepository<? extends MaketDictionary> repository =
                (MaketDictionaryRepository<? extends MaketDictionary>) dictList.repositoryList().get(className);
        EntityType<?> entityType = entityManager.getMetamodel().entity(dictClass);
        Set<SingularAttribute<?, ?>> fieldSet = (Set<SingularAttribute<?, ?>>) entityType.getSingularAttributes();

        List<? extends MaketDictionary> records = repository.findByDeletedFalse(order);
        ArrayList<HashMap<String, Object>> hashMapList = reformatQueryToMap(records, fieldSet);
        return ResponseEntity.ok(hashMapList);
    }

    private static  ArrayList<HashMap<String, Object>> reformatQueryToMap(List records, Set<SingularAttribute<?, ?>> fieldSet) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (Object record : records) {
            HashMap<String, Object> map = new HashMap<>();
            Field fieldR = null;
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
