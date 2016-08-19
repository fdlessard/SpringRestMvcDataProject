package com.lessard.codesamples.order.controllers;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lessard.codesamples.order.domain.SalesOrder;

import com.lessard.codesamples.order.services.SalesOrderService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalesOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderController.class);

    private SalesOrderService salesOrderService;

    public SalesOrderController() {
        super();
    }

    @Autowired
    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    public SalesOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public String hello() {

        return "Hello World";
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSalesOrder(@RequestBody SalesOrder salesOrder) {

        salesOrderService.createSalesOrder(salesOrder);
    }

    @RequestMapping(value = "/salesorders/{id}", method = RequestMethod.GET, produces = "application/json")
    public SalesOrder get(@PathVariable Long id) {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(id);

        if(salesOrder == null) {
            throw new OrderNotFoundException(id);
        }

        return salesOrder;
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.GET, produces = "application/json")
    public List<SalesOrder> getAll() {

        Iterable<SalesOrder> salesOrders = salesOrderService.getAllSalesOrder();

        Iterator<SalesOrder> iterator = salesOrders.iterator();

        List<SalesOrder> list = new ArrayList<SalesOrder>();
        iterator.forEachRemaining(list::add);

        return list;
    }

    @RequestMapping(value = "/salesorders/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable Long id) {

        salesOrderService.deleteSalesOrder(id);
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.PUT, produces = "application/json")
    public void updateSalesOrder(@RequestBody SalesOrder salesOrder) {

        salesOrderService.updateSalesOrder(salesOrder);
    }


    // Exception handlers

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String orderNotFound(OrderNotFoundException e) {
        Long orderId = e.getOrderId();
        return "Order " + orderId + " not found !";
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String orderNotFound(NumberFormatException e) {
        return "Order not found !";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody String orderAlreadyExist(Exception e) {
        return "Order already exist !";
    }

}
