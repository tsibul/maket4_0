package ru.maket.maket4_0.db.dictionary.goods.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.goods.MatrixType;

import java.util.List;

public interface MatrixTypeRepository extends JpaRepository<MatrixType, Long>
//        ,
//        MaketDictionaryRepository<MatrixType>
//        ,
//        DictionaryRepositoryCustom<MatrixType>
{
    List<MatrixType> findByDeletedFalse(Sort sort);
}
