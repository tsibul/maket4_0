package ru.maket.maket4_0.db.dictionary.goods.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.goods.Goods;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long>, MaketDictionaryRepository<Goods> {
    List<Goods> findByDeletedFalse(Sort sort);
}
