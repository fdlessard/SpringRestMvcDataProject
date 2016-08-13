package com.lessard.codesamples.order.controllers;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lessard.codesamples.order.domain.SalesOrder;
import com.lessard.codesamples.order.services.SalesOrderServiceImpl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SalesOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesOrderController.class);

    private SalesOrderServiceImpl salesOrderService;

    public SalesOrderController() {
    }

    @Autowired
    public SalesOrderController(SalesOrderServiceImpl salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    public SalesOrderServiceImpl getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(SalesOrderServiceImpl salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> hello() {

        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<SalesOrder> createSalesOrder(@RequestBody SalesOrder salesOrder) {

        salesOrderService.createSalesOrder(salesOrder);

        return new ResponseEntity<SalesOrder>(salesOrder, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/salesorders/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<SalesOrder> get(@PathVariable Long id) {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(id);

        return new ResponseEntity<SalesOrder>(salesOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<SalesOrder>> getAll() {

        Iterable<SalesOrder> salesOrders = salesOrderService.getAllSalesOrder();

        Iterator<SalesOrder> iterator = salesOrders.iterator();

        List<SalesOrder> list = new ArrayList<SalesOrder>();
        iterator.forEachRemaining(list::add);
        LOGGER .debug("SalesOrderController.getAll list size: " + list.size());
        for (SalesOrder salesOrder: list) {
            LOGGER .debug("SalesOrderController.getAll " + salesOrder);
        }

        return new ResponseEntity<Iterable<SalesOrder>>(salesOrders, HttpStatus.OK);
    }

    @RequestMapping(value = "/salesorders/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        salesOrderService.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/salesorders", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<SalesOrder> updateSalesOrder(@RequestBody SalesOrder salesOrder) {

        salesOrderService.updateSalesOrder(salesOrder);

        return new ResponseEntity<SalesOrder>(salesOrder, HttpStatus.OK);
    }

}
