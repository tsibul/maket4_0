package ru.maket.maket4_0.db.dictionary.sqbuilder;

import jakarta.persistence.TypedQuery;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

import java.lang.reflect.InvocationTargetException;

public interface CriteriaRequestBuilder<T extends MaketDictionary> {
    TypedQuery<MaketDictionary> buildCriteriaQuery() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
    void addPredicates();
    void addOrderBy() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
