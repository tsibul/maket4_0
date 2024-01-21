package ru.maket.maket4_0.db.dictionary.color;

import jakarta.persistence.*;
import org.springframework.data.domain.Sort;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
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
        return article + " " + this.getName() + " Pantone: " + pantone;
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
}
