package ru.maket.maket4_0.db.dictionary.color.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.color.Color;
import ru.maket.maket4_0.db.dictionary.color.ColorScheme;

import java.util.List;

public interface ColorSchemeRepository extends JpaRepository<ColorScheme, Long>, MaketDictionaryRepository<ColorScheme> {
    List<ColorScheme> findByDeletedFalse(Sort sort);
}

