package ru.maket.maket4_0.db.dictionary;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MaketDictionaryRepository<T extends MaketDictionary> extends CrudRepository<MaketDictionary, Long>
{
//    List<T> findByDeletedFalse(Sort sort);
//    T findById(Long id);

}
