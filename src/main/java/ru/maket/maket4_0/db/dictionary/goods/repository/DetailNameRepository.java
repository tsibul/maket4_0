package ru.maket.maket4_0.db.dictionary.goods.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.goods.DetailName;

import java.util.List;

public interface DetailNameRepository extends JpaRepository<DetailName, Long>
//        ,
//        MaketDictionaryRepository<DetailName>
//        , DictionaryRepositoryCustom<DetailName>
{
    List<DetailName> findByDeletedFalse(Sort sort);
}
