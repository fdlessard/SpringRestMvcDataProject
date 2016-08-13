package com.lessard.codesamples.order.repositories;


import com.lessard.codesamples.order.domain.SalesOrder;

public interface SalesOrderRepository {

	SalesOrder findByPrimaryKey(Long id);

	Iterable<SalesOrder> findAll();

	void save(SalesOrder salesOrder);

	void delete(Long id);

	void update(SalesOrder salesOrder);
	
}
