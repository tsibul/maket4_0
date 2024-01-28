package ru.maket.maket4_0.archive;

import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;
import static ru.maket.maket4_0.db.dictionary.FieldNames.dataBaseNames;
import static ru.maket.maket4_0.db.dictionary.FieldNames.fieldNames;

public class NextTwentyRecordsSQLBuilder implements SQLRequestBuilder {
    private final JdbcTemplate jdbcTemplate;
    private final Class<? extends Object> classType;
    private final Integer lastRecord;
    private final String searchString;
    private final boolean deleted;
    private final Set<SingularAttribute<?, ?>> fieldSet;


    public NextTwentyRecordsSQLBuilder(Set<SingularAttribute<?, ?>> fieldSet, String className, String deleted, String searchString, String lastRecord) {
        this.jdbcTemplate = new JdbcTemplate();
        this.classType = dictionaryList().get(className);
        this.lastRecord = Integer.parseInt(lastRecord);
        this.deleted = !"0".equals(deleted);
        this.searchString = "'%" + searchString.replace("_", " ") + "%'";
        this.fieldSet = fieldSet;
    }

    private HashMap<String, ArrayList<String[]>> fieldList() {
        HashMap<String, ArrayList<String[]>> fields = new HashMap<>();
        ArrayList<String[]> basic = new ArrayList<>();
        ArrayList<String[]> foreign = new ArrayList<>();
        ArrayList<String[]> other = new ArrayList<>();
        fields.put("Basic", basic);
        fields.put("Foreign", foreign);
        fields.put("Other", other);
        fieldSet.forEach(field -> {
            boolean fieldBasic = field.getPersistentAttributeType().toString().equals("BASIC");
            boolean fieldForeign = field.getPersistentAttributeType().toString().equals("MANY_TO_ONE");
            String fieldSimpleType = field.getJavaType().getSimpleName();
            String fieldName = field.getName();
            if (fieldNames().get(fieldName) != null) {
                fieldName = fieldNames().get(fieldName);
            }
            if (fieldBasic && fieldSimpleType.equals("String") || fieldSimpleType.equals("Date")) {
                basic.add(new String[]{fieldName});
            } else if (fieldForeign) {
                foreign.add(new String[]{fieldName, field.getName()});
//                EntityType<?> foreignEntityType = (EntityType<?>) field.getType();
//                Set<SingularAttribute<?, ?>> foreignFieldSet = (Set<SingularAttribute<?, ?>>) foreignEntityType.getSingularAttributes();
            } else {
                other.add(new String[]{fieldName});
            }
        });
        return fields;
    }

    @Override
    public String selectFromBuilder() {
        StringBuilder selectString = new StringBuilder();
        selectString.append(" SELECT ");
        for (int i = 0; i < fieldList().get("Other").size(); i++) {
            selectString.append("c.").append(fieldList().get("Other").get(i)[0]).append(", ");
        }
        for (int i = 0; i < fieldList().get("Basic").size(); i++) {
            selectString.append("c.").append(fieldList().get("Basic").get(i)[0]).append(", ");
        }
        int foreignSize = fieldList().get("Foreign").size();
        if (foreignSize > 0) {
            for (int i = 0; i < foreignSize; i++) {
                selectString.append("c").append(i).append(".public_name as ").append(fieldList().get("Foreign").get(i)[1])
                        .append(", ").append("c").append(i).append(".id as ").append(fieldList().get("Foreign").get(i)[1])
                        .append("_id").append(", ");
            }
        }
        selectString.delete(selectString.length() - 2, selectString.length());

        selectString.append(" FROM ");
        if (dataBaseNames().get(classType.getSimpleName()) != null) {
            String fromName = dataBaseNames().get(classType.getSimpleName());
            selectString.append(fromName).append(" as c ");
        } else {
            selectString.append(classType.getSimpleName()).append(" as c ");
        }
        return selectString.toString();
    }

    @Override
    public String joinBuilder() {
        StringBuilder joinString = new StringBuilder();
        int foreignSize = fieldList().get("Foreign").size();
        if (foreignSize > 0) {
            joinString.append(" JOIN ");
            for (Integer i = 0; i < foreignSize; i++) {
                joinString.append(fieldList().get("Foreign").get(i)[0]).append(" as c").append(i).append(" ")
                        .append(" ON c.").append(fieldList().get("Foreign").get(i)[0]).append(" = ").append("c")
                        .append(i).append(".id, ");
            }
            joinString.delete(joinString.length() - 2, joinString.length());
        }
        return joinString.toString();
    }

    @Override
    public String whereBuilder() {
        StringBuilder whereString = new StringBuilder();
        if (!deleted) {
            whereString.append(" WHERE c.deleted = false ");
        }
        if (!searchString.equals("'%default%'")) {
            whereString.append("AND (");
            for (int i = 0; i < fieldList().get("Basic").size(); i++) {
                whereString.append("c.").append(fieldList().get("Basic").get(i)[0])
                        .append(" LIKE ").append(searchString).append(" OR ");
            }
            int foreignSize = fieldList().get("Foreign").size();
            if (foreignSize > 0) {
                for (int i = 0; i < foreignSize; i++) {
                    whereString.append("c").append(i).append(".public_name LIKE ").append(searchString).append(" OR ");
                }
            }
            whereString.delete(whereString.length() - 4, whereString.length());
            whereString.append(") ");
        }
        return whereString.toString();
    }

    @Override
    public String orderByBuilder() throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String orderString = (String) classType.getMethod("defaultOrderString").invoke(null);
        return " ORDER BY " + orderString + " ";
    }

    @Override
    public String limitOffsetBuilder() {
        return " LIMIT 20 OFFSET " + lastRecord + " ";
    }


}
