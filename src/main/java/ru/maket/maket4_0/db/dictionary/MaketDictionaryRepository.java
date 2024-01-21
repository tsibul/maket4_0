package ru.maket.maket4_0.db.dictionary;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maket.maket4_0.db.dictionary.color.Color;

import java.util.List;

public interface MaketDictionaryRepository<T extends MaketDictionary> {

    //    List<T> findTop20ByDeletedFalseOrderByFieldName(@Param("fieldName") String fieldName);
//
//    List<T> findByDeletedFalseOrderByFieldName(@Param("fieldName") String fieldName);

//    List<T> findTop20ByDeletedFalse();
//
//    List<T> findByDeletedFalse();

    @Query("SELECT mi FROM #{#entityName} mi " +
            "WHERE mi.deleted = false " +
            "ORDER BY :defaultOrder")
    List<T> findByDeletedFalse(String defaultOrder);

    List<T> findByDeletedFalse(Sort sort);

}
