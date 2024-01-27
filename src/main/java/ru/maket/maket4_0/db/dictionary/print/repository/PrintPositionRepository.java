package ru.maket.maket4_0.db.dictionary.print.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.print.PrintPosition;

import java.util.List;

public interface PrintPositionRepository extends JpaRepository<PrintPosition, Long>
//        ,
//        MaketDictionaryRepository<PrintPosition>
//        ,
//        DictionaryRepositoryCustom<PrintPosition>
{
    List<PrintPosition> findByDeletedFalse(Sort sort);
}
