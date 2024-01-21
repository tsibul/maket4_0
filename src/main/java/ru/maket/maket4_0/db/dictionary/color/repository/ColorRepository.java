package ru.maket.maket4_0.db.dictionary.color.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.color.Color;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long>, MaketDictionaryRepository<Color> {
    List<Color> findByDeletedFalse(Sort sort);
}
