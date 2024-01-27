package ru.maket.maket4_0.archive;

import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;
import ru.maket.maket4_0.db.dictionary.sqbuilder.SQLRequestBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;
import static ru.maket.maket4_0.db.dictionary.FieldNames.dataBaseNames;

public class DictionaryItemListBuilder implements SQLRequestBuilder {
    private final JdbcTemplate jdbcTemplate;
    private Class<? extends MaketDictionary> classType;
    private Set<SingularAttribute<?, ?>> fieldSet;


    public DictionaryItemListBuilder(Set<SingularAttribute<?, ?>> fieldSet, String className) {
        this.jdbcTemplate = new JdbcTemplate();
        this.classType = dictionaryList().get(className);
        this.fieldSet = fieldSet;
    }


    @Override
    public String selectFromBuilder() {
        StringBuilder selectString = new StringBuilder();
        selectString.append(" SELECT c.id, c.public_name FROM ");
        if(dataBaseNames().get(classType.getSimpleName()) != null) {
            String fromName = dataBaseNames().get(classType.getSimpleName());
            selectString.append(fromName).append(" as c ");
        } else {
            selectString.append(classType.getSimpleName()).append(" as c ");
        }
        return selectString.toString();
    }

    @Override
    public String joinBuilder() {
        return "";
    }

    @Override
    public String whereBuilder() {
        return "";
    }

    @Override
    public String orderByBuilder() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String orderString = (String) classType.getMethod("defaultOrderString").invoke(null);
        return " ORDER BY " + orderString + " ";
    }

    @Override
    public String limitOffsetBuilder() {
        return "";
    }
}
