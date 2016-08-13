package com.lessard.codesamples.order.repositories;

import com.lessard.codesamples.order.domain.SalesOrder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class SalesOrderRepositoryJpaImpl implements SalesOrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SalesOrder findByPrimaryKey(Long id) {

        return entityManager.find(SalesOrder.class, id);
    }

    @Override
    public List<SalesOrder> findAll() {

        String s = "SELECT o FROM SalesOrder o";

        TypedQuery<SalesOrder> query = entityManager.createQuery(s, SalesOrder.class);

        return query.getResultList();

    }

    @Override
    public void save(SalesOrder salesOrder) {

        entityManager.persist(salesOrder);
    }

    @Override
    public void delete(Long id) {

        SalesOrder salesOrder = entityManager.find(SalesOrder.class, id);
        if (salesOrder != null) {
            entityManager.remove(salesOrder);
        }
    }

    @Override
    public void update(SalesOrder salesOrder) {

        entityManager.merge(salesOrder);
    }
}
