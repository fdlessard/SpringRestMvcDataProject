package com.lessard.codesamples.order.services;

import com.lessard.codesamples.order.domain.SalesOrder;
import com.lessard.codesamples.order.repositories.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by fdlessard on 16-07-29.
 */
@Component
public class SalesOrderServiceImpl implements SalesOrderService {

    public static final String NULL_SALES_ORDER_MSG = "Null salesOrder";
    public static final String NULL_ID_MSG = "Null id";

    private SalesOrderRepository salesOrderRepository;


    public SalesOrderServiceImpl() {
    }

    @Autowired
    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    @Override
    public void createSalesOrder(SalesOrder salesOrder) {

        if(salesOrder == null) {
            throw new IllegalArgumentException(NULL_SALES_ORDER_MSG);
        }

        salesOrderRepository.save(salesOrder);
    }

    @Override
    public SalesOrder getSalesOrder(Long id) {

        if(id == null) {
            throw new IllegalArgumentException(NULL_ID_MSG);
        }

        SalesOrder salesOrder = salesOrderRepository.findByPrimaryKey(id);

        return salesOrder;
    }

    @Override
    public Iterable<SalesOrder> getAllSalesOrder() {

        Iterable<SalesOrder> salesOrders = salesOrderRepository.findAll();

        return salesOrders;
    }


    @Override
    public void delete(Long id) {

        if(id == null) {
            throw new IllegalArgumentException(NULL_ID_MSG);
        }

        salesOrderRepository.delete(id);

    }

    @Override
    public void updateSalesOrder(SalesOrder salesOrder) {

        if(salesOrder == null) {
            throw new IllegalArgumentException(NULL_SALES_ORDER_MSG);
        }

        salesOrderRepository.update(salesOrder);

    }

}
