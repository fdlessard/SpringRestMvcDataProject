package io.fdlessard.codesamples.order.services;


import io.fdlessard.codebites.order.services.SalesOrderService;
import io.fdlessard.codesamples.order.configurations.PersistenceItTestConfiguration;
import io.fdlessard.codesamples.order.configurations.WebMvcItTestConfiguration;
import io.fdlessard.codebites.order.domain.SalesOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ContextConfiguration(classes = {PersistenceItTestConfiguration.class, WebMvcItTestConfiguration.class})
@WebAppConfiguration
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class SalesOrderServiceIT {

    @Autowired
    private SalesOrderService salesOrderService;

    @Before
    public void setup() {
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        Iterable<SalesOrder> it = salesOrderService.getAllSalesOrder();
        Iterator<SalesOrder> iterator = it.iterator();

        List<SalesOrder> list = new ArrayList<SalesOrder>();
        iterator.forEachRemaining(list::add);

        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testGetSalesOrder() throws Exception {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(100l);

        Assert.assertNotNull(salesOrder);
        Assert.assertEquals(Long.valueOf(100), salesOrder.getId());
        Assert.assertEquals(Long.valueOf(1), salesOrder.getVersion());
        Assert.assertEquals("SalesOrder 0", salesOrder.getDescription());
        //Assert. "2016-08-01 12:00:00",  salesOrder.getDate());
        Assert.assertEquals(new BigDecimal(10.00), salesOrder.getTotal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSalesOrderWithNullId() throws Exception {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteSalesOrderWithNullId() throws Exception {

       salesOrderService.deleteSalesOrder(null);
    }


    @Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        salesOrderService.deleteSalesOrder(null);
    }

}