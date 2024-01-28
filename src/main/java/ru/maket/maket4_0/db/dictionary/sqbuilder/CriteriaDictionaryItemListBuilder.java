package ru.maket.maket4_0.db.dictionary.sqbuilder;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;

public class CriteriaDictionaryItemListBuilder implements CriteriaRequestBuilder<Object> {
    private final EntityManager entityManager;
    private final Class<? extends Object> classType;
    private final Set<SingularAttribute<?, ?>> fieldSet;
    private final CriteriaBuilder criteriaBuilder;
    private final CriteriaQuery<Object> criteriaQuery;
    private final Root<? extends Object> root;

    public CriteriaDictionaryItemListBuilder(Set<SingularAttribute<?, ?>> fieldSet, String className, EntityManager entityManager
    ) {
        this.entityManager = entityManager;
        this.classType = dictionaryList().get(className);
        this.fieldSet = fieldSet;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.criteriaQuery = (CriteriaQuery<Object>) criteriaBuilder.createQuery(classType);
        this.root =  criteriaQuery.from(classType);
    }

    public TypedQuery<Object> buildCriteriaQuery() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        root.alias("c");

//        criteriaQuery.select(criteriaBuilder.construct(classType, root.get("id"), root.get("publicName")));
        criteriaQuery.multiselect(root.get("id"), root.get("publicName"));
//        criteriaQuery.select(root);

        addPredicates();
        addOrderBy();

        TypedQuery<Object> finalQuery = entityManager.createQuery(criteriaQuery);

        return finalQuery;
    }

    public void addPredicates() {
//        // Добавляем условия в WHERE-клаузу, используя алиас
//        Predicate[] predicates = fieldSet.stream()
//                .map(attribute -> criteriaBuilder.equal(root.get("c").get(attribute.getName()), "8"))
//                .toArray(Predicate[]::new);
//        criteriaQuery.where(predicates);
    }

    public void addOrderBy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String orderString = (String) classType.getMethod("defaultOrderString").invoke(null);
        for (int i = orderString.split(",").length - 1; i >= 0; i--) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderString.split(",")[i])));
        }
    }
}
