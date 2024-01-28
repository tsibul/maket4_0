package ru.maket.maket4_0.archive;

import java.lang.reflect.InvocationTargetException;

public interface SQLRequestBuilder {
    String selectFromBuilder();
    String joinBuilder();
    String whereBuilder();
    String orderByBuilder()throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    String limitOffsetBuilder();
    default String totalSQLRequest() throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return this.selectFromBuilder() + this.joinBuilder() + this.whereBuilder()
                + this.orderByBuilder() + this.limitOffsetBuilder();
    }
}
