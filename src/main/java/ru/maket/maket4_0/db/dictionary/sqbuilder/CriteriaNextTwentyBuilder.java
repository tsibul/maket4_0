package ru.maket.maket4_0.db.dictionary.sqbuilder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;
import static ru.maket.maket4_0.db.dictionary.FieldNames.fieldNames;
import static ru.maket.maket4_0.db.dictionary.FieldNames.fieldNamesReverse;

public class CriteriaNextTwentyBuilder implements CriteriaRequestBuilder {

    private final EntityManager entityManager;
    private final Class<Object> classType;
    private final Set<SingularAttribute<?, ?>> fieldSet;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<Map<String,Object>> criteriaQuery;
    private final Root<Object> root;
    private final int lastRecord;
    private final String searchString;
    private final boolean shDeleted;

    public CriteriaNextTwentyBuilder(EntityManager entityManager, String className,
                                     Set<SingularAttribute<?, ?>> fieldSet,
                                     String lastRecord, String searchString, String shDeleted) {
        this.entityManager = entityManager;
        this.classType = (Class<Object>) dictionaryList().get(className);
        this.fieldSet = fieldSet;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = criteriaBuilder.createQuery((Class<Map<String, Object>>) (Class<?>) Map.class);
        try {
            this.lastRecord = Integer.parseInt(lastRecord);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Невозможно преобразовать строку в целое число: " + lastRecord, e);
        }
        this.root =  criteriaQuery.from(classType);
        this.searchString = "%" + searchString.replace("_", " ") + "%";
        this.shDeleted = !"0".equals(shDeleted);
    }

    @Override
    public TypedQuery<Map<String,Object>> buildCriteriaQuery() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

// SELECT
        List<Selection<?>> selections = new ArrayList<>();

// Other fields
        for (String field : fieldList().get("Other")) {
            selections.add(root.get(field)
                    .alias(fieldNames().get(field) != null ? fieldNamesReverse().get(fieldNames().get(field)) : field));
        }

// Basic fields
        for (String field : fieldList().get("Basic")) {
            selections.add(root.get(field)
                    .alias(fieldNames().get(field) != null ? fieldNamesReverse().get(fieldNames().get(field)) : field));
        }

// Foreign fields
        int aliasCounter = 0;
        for (String field : fieldList().get("Foreign")) {
            Join<Object, ? extends MaketDictionary> join = root.join(field, JoinType.LEFT);
            join.alias("c" + aliasCounter);
            selections.add(join.get("publicName").alias(field));
            selections.add(join.get("id").alias(field + "_id"));
            aliasCounter++;
        }

        criteriaQuery.multiselect(selections.toArray(new Selection<?>[0]));

// FROM
            criteriaQuery.from(classType).alias("c");

// Build and execute the query
        addPredicates();
        addOrderBy();
        criteriaQuery.distinct(true);

        TypedQuery<Map<String,Object>> finalQuery = entityManager.createQuery(criteriaQuery);

        return finalQuery.setMaxResults(20).setFirstResult(lastRecord);
    }

    @Override
    public void addPredicates() {
        Predicate condition = null;
        if(!shDeleted){
            condition = criteriaBuilder.equal(root.get("deleted"), false);
        }
        ArrayList<Predicate> predicates = new ArrayList<>();
        if(!searchString.equals("%default%")){
            for (String field : fieldList().get("Basic")) {
                predicates.add(criteriaBuilder.like(root.get(field), searchString));
            }
            for (String field : fieldList().get("Foreign")){
                predicates.add(criteriaBuilder.like(root.join(field, JoinType.LEFT).get("publicName"), searchString));
            }
        }
        if (condition != null && !predicates.isEmpty()) {
            Predicate finalPredicate = criteriaBuilder.and(
                    condition,
                    criteriaBuilder.or(predicates.toArray(new Predicate[0]))
            );
            criteriaQuery.where(finalPredicate);
        } else if (condition != null) {
            criteriaQuery.where(condition);
        } else if (!predicates.isEmpty()) {
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
        }
    }

    @Override
    public void addOrderBy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String orderString = (String) classType.getMethod("defaultOrderString").invoke(null);
        for (int i = orderString.split(",").length - 1; i >= 0; i--) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderString.split(",")[i])));
        }
    }

    private HashMap<String, ArrayList<String>> fieldList() {
        HashMap<String, ArrayList<String>> fields = new HashMap<>();
        ArrayList<String> basic = new ArrayList<>();
        ArrayList<String> foreign = new ArrayList<>();
        ArrayList<String> other = new ArrayList<>();
        fields.put("Basic", basic);
        fields.put("Foreign", foreign);
        fields.put("Other", other);
        fieldSet.forEach(field -> {
            boolean fieldBasic = field.getPersistentAttributeType().toString().equals("BASIC");
            boolean fieldForeign = field.getPersistentAttributeType().toString().equals("MANY_TO_ONE");
            String fieldSimpleType = field.getJavaType().getSimpleName();
            if (fieldBasic && fieldSimpleType.equals("String") || fieldSimpleType.equals("Date")) {
                basic.add(field.getName());
            } else if (fieldForeign) {
                foreign.add(field.getName());
            } else {
                other.add(field.getName());
            }
        });
        Collections.sort(fields.get("Basic"));
        Collections.sort(fields.get("Foreign"));
        Collections.sort(fields.get("Other"));

        return fields;
    }
}
