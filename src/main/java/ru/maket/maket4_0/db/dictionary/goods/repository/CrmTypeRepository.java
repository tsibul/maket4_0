package ru.maket.maket4_0.db.dictionary.goods.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.goods.CrmType;

import java.util.List;

public interface CrmTypeRepository extends JpaRepository<CrmType, Long>, MaketDictionaryRepository<CrmType> {
    List<CrmType> findByDeletedFalse(Sort sort);
}
