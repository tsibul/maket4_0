package ru.maket.maket4_0.db.dictionary.customer.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;
import ru.maket.maket4_0.db.dictionary.customer.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, MaketDictionaryRepository<Customer> {
    List<Customer> findByDeletedFalse(Sort sort);
}
