package ru.maket.maket4_0.archive;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;
import ru.maket.maket4_0.db.dictionary.MaketDictionaryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;

public class DictionaryRepositoryCustomImplement<T extends MaketDictionary> implements DictionaryRepositoryCustom<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<T> searchByMultipleParams(String searchString, Integer startRecord,
                                               String shDeleted, String className, Sort sort) {

        Class <T> tClass = (Class<T>) dictionaryList().get(className);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tClass);
        Root<T> dict = query.from(tClass);

        List<Predicate> wherePredicates = new ArrayList<>();
        Predicate deletedPredicate = null;
        if (shDeleted.equals("0")) {
            Path deletedPath = dict.get("deleted");
            deletedPredicate = cb.isFalse(deletedPath);
            wherePredicates.add(deletedPredicate);
        }
//        if (!searchString.equals("default")) {
//            String finalSearchString = searchString.replace("_", " ");
//            List<Predicate> searchPredicates = new ArrayList<>();
////            EntityType<T> entityType = entityManager.getMetamodel().entity(tClass);
////            Set<SingularAttribute<?, ?>> fieldSet = (Set<SingularAttribute<?, ?>>) entityType.getSingularAttributes();
//            fieldSet.forEach(field -> {
//                boolean fieldBasic = field.getPersistentAttributeType().toString().equals("BASIC");
//                boolean fieldForeign = field.getPersistentAttributeType().toString().equals("MANY_TO_ONE");
//                String fieldSimpleType = field.getJavaType().getSimpleName();
//                String fieldName = field.getName();
//                if (fieldBasic && fieldSimpleType.equals("String") || fieldSimpleType.equals("Date")) {
//                    Path<String> fieldPath = dict.get(fieldName);
//                    searchPredicates.add(cb.like(fieldPath, finalSearchString));
//                } else if (fieldForeign) {
//                    EntityType<?> foreignEntityType = (EntityType<?>) field.getType();
//                    Set<SingularAttribute<?, ?>> foreignFieldSet = (Set<SingularAttribute<?, ?>>) foreignEntityType.getSingularAttributes();
//
//                    boolean hasArticleField = foreignFieldSet.stream().anyMatch(foreignField -> foreignField.getName().equals("article"));
//
//                    if (hasArticleField) {
//                        Path<String> articlePath = dict.get(fieldName).get("article");
//                        Path<String> namePath = dict.get(fieldName).get("name");
//
//                        Expression<String> articleNameExpression = cb.concat(cb.concat(articlePath, " "), namePath);
//
//                        searchPredicates.add(cb.like(articleNameExpression, finalSearchString));
//                    } else {
//                        Path<String> namePath = dict.get(fieldName).get("name");
//                        searchPredicates.add(cb.like(namePath, finalSearchString));
//                    }
//                }
//            });
//            Predicate searchPredicate = cb.or(searchPredicates.toArray(new Predicate[0]));
//            if(!wherePredicates.isEmpty()){
//                wherePredicates.add(searchPredicate);
//                Predicate finalPredicate = cb.and(wherePredicates.toArray(new Predicate[0]));
//                query.where(finalPredicate);
//            } else {
//                query.where(searchPredicate);
//            }
//        } else {
            if(deletedPredicate != null){
                query.where(deletedPredicate);
            }
//        }

        if (sort != null) {
            List<Order> orders = orderList(sort, dict, cb);
            query.orderBy(orders);
        }

        return entityManager.createQuery(query)
                .setFirstResult(startRecord)
                .setMaxResults(20)
                .getResultList();
    }

    private static List<Order> orderList(Sort sort, Root dict, CriteriaBuilder cb) {
        List<Order> orders = new ArrayList<>();
        for (Sort.Order sortOrder : sort) {
            String property = sortOrder.getProperty();
            Path<Object> fieldPath = dict.get(property);

            Order order = sortOrder.isAscending() ? cb.asc(fieldPath) : cb.desc(fieldPath);
            orders.add(order);
        }
        return orders;
    }
}


