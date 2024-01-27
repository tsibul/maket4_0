package ru.maket.maket4_0.db.dictionary.print.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.print.PrintType;

import java.util.List;

public interface PrintTypeRepository extends JpaRepository<PrintType, Long>
//        ,
//        MaketDictionaryRepository<PrintType>
//        ,
//        DictionaryRepositoryCustom<PrintType>
{
    List<PrintType> findByDeletedFalse(Sort sort);
}
