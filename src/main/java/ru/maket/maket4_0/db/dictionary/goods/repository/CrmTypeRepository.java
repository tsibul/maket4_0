package ru.maket.maket4_0.db.dictionary.goods.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.goods.CrmType;

import java.util.List;

public interface CrmTypeRepository extends JpaRepository<CrmType, Long>
//        ,
//        MaketDictionaryRepository<CrmType>
//        ,
//        DictionaryRepositoryCustom<CrmType>
{
    List<CrmType> findByDeletedFalse(Sort sort);

//    List<CrmType> findTwentyRecordsWithSearch(String seachString, Integer startRecord, String shDeleted,
//                                              Class<CrmType> tClass, Sort sort,
//                                              Set<SingularAttribute<?, ?>> fieldSet);

}
