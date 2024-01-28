package ru.maket.maket4_0.db.dictionary.color;

import jakarta.persistence.*;
import org.springframework.data.domain.Sort;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;
import ru.maket.maket4_0.db.dictionary.color.repository.ColorSchemeRepository;


import static ru.maket.maket4_0.Maket40Application.dataSource;

@Entity
public class Color extends MaketDictionary {
    @Column(nullable = false, name = "article")
    private String article;
    @Column(nullable = false, name = "pantone")
    private String pantone;
    @Column(nullable = false, name = "hex")
    private String hex;
    @ManyToOne(targetEntity = ColorScheme.class)
    @JoinColumn(name = "color_scheme", referencedColumnName = "id")
    private ColorScheme colorScheme;

    public Color(boolean deleted, Long id, String article, String hex, String name, String pantone, String publicName,
                 String colorScheme, Long colorSchemeId) {
        final ColorSchemeRepository colorSchemeRepository;
        if (deleted) {
            this.isDeleted();
        }
        this.publicName = publicName;
        this.setName(name);
        this.setId(id);
        this.setPublicName(publicName);
        this.article = article;
        this.pantone = pantone;
        this.hex = hex;

        ColorSchemeService colorSchemeService = new ColorSchemeService(dataSource);
        this.colorScheme = colorSchemeService.getColorSchemeById(colorSchemeId);
    }

    public Color(String name, String article, ColorSchemeRepository colorSchemeRepository) {
        super(name);
        this.article = article;
        this.publicName = article + " " + name;
    }

    public Color(Long id, String publicName) {
        super(id, publicName);
    }

    public Color() {
        super();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        this.publicName = this.article + " " + name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
        this.publicName = article + " " + this.getName();
    }

    public String getPantone() {
        return pantone;
    }

    public void setPantone(String pantone) {
        this.pantone = pantone;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    @Override
    public String toString() {
        return this.getName() + " Pantone: " + pantone;
    }

    public static Sort defaultOrder() {
        return Sort.by(
                Sort.Order.asc("colorScheme"),
                Sort.Order.asc("article"),
                // Дополнительные поля и порядок сортировки, если необходимо
                Sort.Order.desc("colorScheme"),
                Sort.Order.desc("article")
        );
    }

    public static String defaultOrderString() {
        return "colorScheme,article";
    }


}
