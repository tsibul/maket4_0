package ru.maket.maket4_0.db.dictionary;

import org.springframework.data.repository.CrudRepository;
import ru.maket.maket4_0.db.dictionary.color.*;
import ru.maket.maket4_0.db.dictionary.color.repository.ColorRepository;
import ru.maket.maket4_0.db.dictionary.color.repository.ColorSchemeRepository;
import ru.maket.maket4_0.db.dictionary.customer.*;
import ru.maket.maket4_0.db.dictionary.goods.*;
import ru.maket.maket4_0.db.dictionary.print.*;

import java.util.HashMap;

public class DictionaryList {

    private final ColorRepository colorRepository;
    private final ColorSchemeRepository colorSchemeRepository;

    public DictionaryList(ColorRepository colorRepository, ColorSchemeRepository colorSchemeRepository) {
        this.colorRepository = colorRepository;
        this.colorSchemeRepository = colorSchemeRepository;
    }

    public static HashMap<String, Class<?>> dictionaryList () {
        HashMap<String, Class<? extends Object>> dictList = new HashMap<>();
        dictList.put("Color", Color.class);
        dictList.put("ColorScheme", ColorScheme.class);
        dictList.put("Customer", Customer.class);
        dictList.put("CustomerGroup", CustomerGroup.class);
        dictList.put("CustomerType", CustomerType.class);
        dictList.put("Goods", Goods.class);
        dictList.put("Detail", Detail.class);
        dictList.put("MatrixType", MatrixType.class);
        dictList.put("CrmType", CrmType.class);
        dictList.put("DetailName", DetailName.class);
        dictList.put("PrintPlace", PrintPlace.class);
        dictList.put("PrintPosition", PrintPosition.class);
        dictList.put("PrintType", PrintType.class);
        return dictList;
    }
    public HashMap<String, CrudRepository<? extends MaketDictionary, Long>>repositoryList () {
        HashMap<String, CrudRepository<? extends MaketDictionary, Long>> repList = new HashMap<>();
        repList.put("Color", colorRepository);
        repList.put("ColorScheme", colorSchemeRepository);
        return repList;
    }
}
