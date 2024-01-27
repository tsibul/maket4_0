package ru.maket.maket4_0.controller.jsonController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.maket.maket4_0.db.dictionary.MaketDictionary;

import static ru.maket.maket4_0.db.dictionary.DictionaryList.dictionaryList;

@Controller
public class DeleteRecordController {

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/dict_delete/{className}/{idNo}")
    @ResponseBody
    @Transactional
    public void dictionaryDelete(@PathVariable String className, @PathVariable String idNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        Class<MaketDictionary> entityClass = (Class<MaketDictionary>) dictionaryList().get(className);
        CriteriaUpdate<MaketDictionary> update = cb.createCriteriaUpdate(entityClass);
        Root<MaketDictionary> root = update.from(entityClass);
        update.set("deleted", true)
                .where(cb.equal(root.get("id"), Integer.parseInt(idNo)));
        entityManager.createQuery(update).executeUpdate();
    }
}
