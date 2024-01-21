package ru.maket.maket4_0.db.dictionary.print.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.print.PrintPlace;

import java.util.List;

public interface PrintPlaceRepository extends JpaRepository<PrintPlace, Long>, MaketDictionaryRepository<PrintPlace> {
    List<PrintPlace> findByDeletedFalse(Sort sort);
}
