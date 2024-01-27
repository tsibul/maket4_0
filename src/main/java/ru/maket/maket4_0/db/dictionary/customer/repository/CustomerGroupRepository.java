package ru.maket.maket4_0.db.dictionary.customer.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.customer.CustomerGroup;

import java.util.List;

public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Long>
//        ,
//        MaketDictionaryRepository<CustomerGroup>
//        ,
//        DictionaryRepositoryCustom<CustomerGroup>
{
    List<CustomerGroup> findByDeletedFalse(Sort sort);
}
