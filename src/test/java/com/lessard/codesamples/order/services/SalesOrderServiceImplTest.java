package com.lessard.codesamples.order.services;

import com.lessard.codesamples.order.domain.SalesOrder;
import com.lessard.codesamples.order.repositories.SalesOrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by fdlessard on 16-07-29.
 */
@RunWith(MockitoJUnitRunner.class)
public class SalesOrderServiceImplTest {

    @Mock
    private SalesOrderRepository salesOrderRepository;

    private Date today = Calendar.getInstance().getTime();


    private SalesOrderServiceImpl salesOrderService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        salesOrderService = new SalesOrderServiceImpl(salesOrderRepository);

        Mockito.when(salesOrderRepository.findByPrimaryKey(0l)).
                thenReturn(new SalesOrder(0l, 0l, "SalesOrder 0", today, new BigDecimal(10.00)));

        List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();

        salesOrderList.add(new SalesOrder(0l, 0l, "SalesOrder 0", today, new BigDecimal(10.00)));
        salesOrderList.add(new SalesOrder(1l, 0l, "SalesOrder 1", today, new BigDecimal(10.00)));
        salesOrderList.add(new SalesOrder(2l, 0l, "SalesOrder 2", today, new BigDecimal(10.00)));

        Mockito.when(salesOrderRepository.findAll()).thenReturn(salesOrderList);

    }

    @Test
    public void testGetSalesOrder() throws Exception {

        SalesOrder salesOrder = salesOrderService.getSalesOrder(0l);
        Assert.assertNotNull(salesOrder);
        Assert.assertEquals(salesOrder.getId(), Long.valueOf(0));
        Assert.assertEquals(salesOrder.getDescription(), "SalesOrder 0");
        Assert.assertEquals(salesOrder.getDate(), today);
        Assert.assertEquals(salesOrder.getTotal(), new BigDecimal(10.00));
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        Iterable<SalesOrder> it = salesOrderService.getAllSalesOrder();

        Assert.assertNotNull(it);
        List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
        it.iterator().forEachRemaining(salesOrders::add);
        Assert.assertEquals(salesOrders.size(), 3);
    }

    @Test
    public void testDeleteSalesOrder() throws Exception {

        salesOrderService.delete(Long.valueOf(0));
        verify(salesOrderRepository).delete(Long.valueOf(0));
    }

    @Test
    public void testCreateSalesOrder() throws Exception {

        salesOrderService.createSalesOrder( new SalesOrder( 3l, 0l, "SalesOrder 3", today, new BigDecimal(10.00)));
        verify(salesOrderRepository).save(new SalesOrder(3l, 0l, "SalesOrder 3", today, new BigDecimal(10.00)));
    }

    @Test
    public void testUpdateSalesOrder() throws Exception {

        salesOrderService.updateSalesOrder( new SalesOrder(3l, 0l, "SalesOrder 3", today, new BigDecimal(10.00)));
        verify(salesOrderRepository).update( new SalesOrder(3l, 0l, "SalesOrder 3", today, new BigDecimal(10.00)));
    }
}
