package com.lessard.codesamples.order.repositories;


import com.lessard.codesamples.order.domain.SalesOrder;
import org.springframework.data.repository.CrudRepository;


public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long> {

	
}
