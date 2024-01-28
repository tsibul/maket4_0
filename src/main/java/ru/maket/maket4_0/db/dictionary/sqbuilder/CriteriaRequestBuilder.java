package ru.maket.maket4_0.db.dictionary.sqbuilder;

import jakarta.persistence.TypedQuery;

import java.lang.reflect.InvocationTargetException;

public interface CriteriaRequestBuilder<T> {
    TypedQuery<T> buildCriteriaQuery() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
    void addPredicates();
    void addOrderBy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
