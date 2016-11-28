package io.fdlessard.codebites.order.repositories;


import io.fdlessard.codebites.order.domain.SalesOrder;
import org.springframework.data.repository.CrudRepository;


public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long> {

	
}
