package ru.maket.maket4_0.db.dictionary.customer.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.customer.CustomerType;

import java.util.List;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long>, MaketDictionaryRepository<CustomerType> {
    List<CustomerType> findByDeletedFalse(Sort sort);
}
