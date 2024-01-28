package ru.maket.maket4_0.db.dictionary;

import org.springframework.data.repository.CrudRepository;


public interface MaketDictionaryRepository<T extends MaketDictionary> extends CrudRepository<MaketDictionary, Long>
{
//    List<T> findByDeletedFalse(Sort sort);
//    Optional<MaketDictionary> findById(Long id);

}
