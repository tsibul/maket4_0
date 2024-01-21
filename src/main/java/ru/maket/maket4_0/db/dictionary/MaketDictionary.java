package ru.maket.maket4_0.db.dictionary;

import jakarta.persistence.*;
import org.springframework.data.domain.Sort;


@MappedSuperclass
public abstract class MaketDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted;

    public MaketDictionary() {
        this.deleted = false;
    }

    public MaketDictionary(String name) {
        this.name = name;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void restore() {
        this.deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted() {
        this.deleted = true;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Sort defaultOrder(){
        return Sort.by(Sort.Order.asc("name"), Sort.Order.desc("name"));
    }
}
