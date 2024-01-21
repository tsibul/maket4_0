package ru.maket.maket4_0.db.dictionary.goods.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.goods.Detail;

import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long>, MaketDictionaryRepository<Detail> {
    List<Detail> findByDeletedFalse(Sort sort);
}
