package ru.maket.maket4_0.archive;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface DictionaryRepositoryCustom<T> {

    List<T> searchByMultipleParams(String searchString, Integer startRecord,
                                        String shDeleted, String className, Sort sort);

}
