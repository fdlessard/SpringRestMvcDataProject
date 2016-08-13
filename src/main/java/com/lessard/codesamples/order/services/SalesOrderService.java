package com.lessard.codesamples.order.services;

import com.lessard.codesamples.order.domain.SalesOrder;

/**
 * Created by fdlessard on 16-08-12.
 */
public interface SalesOrderService {

    void createSalesOrder(SalesOrder salesOrder);

    SalesOrder getSalesOrder(Long id);

    Iterable<SalesOrder> getAllSalesOrder();

    void delete(Long id);

    void updateSalesOrder(SalesOrder salesOrder);
}
