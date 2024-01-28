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
    @Column(nullable = false, name = "public_name")
    protected String publicName;
    @Column(name = "deleted", columnDefinition = "boolean default false", nullable = false)
    private boolean deleted;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public MaketDictionary() {
        this.deleted = false;
    }

    public MaketDictionary(Long id, String publicName) {
        this.id = id;
        this.publicName = publicName;
//        this.name = publicName;
    }

    public MaketDictionary(Long id, String name, String publicName) {
        this.id = id;
        this.name = name;
        this.publicName = publicName;
    }

    public MaketDictionary(String name) {
        this.name = name;
        this.deleted = false;
        this.publicName = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.publicName = name;
    }

    public String getPublicName() {
        return publicName;
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

    public static Sort defaultOrder() {
        return Sort.by(Sort.Order.asc("name"), Sort.Order.desc("name"));
    }

    public static String defaultOrderString() {
        return "name";
    }
}

