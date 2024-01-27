package ru.maket.maket4_0.db.dictionary.color;

import jakarta.persistence.Entity;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

@Entity
public class ColorScheme extends MaketDictionary {

    public ColorScheme(Long id, String publicName) {
        super(id, publicName);
    }

    public ColorScheme() {
        super();
    }
}
