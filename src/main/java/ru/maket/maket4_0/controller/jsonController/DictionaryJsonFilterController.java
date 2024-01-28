package ru.maket.maket4_0.controller.jsonController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;
import ru.maket.maket4_0.db.dictionary.sqbuilder.CriteriaDictionaryItemListBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;

@Controller
public class DictionaryJsonFilterController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/dictionary_json_filter/{className}")
    @ResponseBody
    public ResponseEntity<List<? extends Object>> dictionaryItemsList(@PathVariable String className)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Class<? extends Object> dictClass = dictionaryList().get(className);
        EntityType<?> entityType = entityManager.getMetamodel().entity(dictClass);
        Set<SingularAttribute<?, ?>> fieldSet = (Set<SingularAttribute<?, ?>>) entityType.getSingularAttributes();
        CriteriaDictionaryItemListBuilder criteriaDictionaryItemListBuilder =
                new CriteriaDictionaryItemListBuilder(fieldSet, className, entityManager);
        TypedQuery<? extends Object> criteriaQuery = criteriaDictionaryItemListBuilder.buildCriteriaQuery();
        List<? extends Object> itemsList = criteriaQuery.getResultList();
//                getResultStream()
//                .map(item -> {
//                    LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//                    map.put("id", item.getId());
//                    map.put("publicName", item.getPublicName());
//                    return map;
//                })
//                .toList();

        return ResponseEntity.ok(itemsList);


    }

}
