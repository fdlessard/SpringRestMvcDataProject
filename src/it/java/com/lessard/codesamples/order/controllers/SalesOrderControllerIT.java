package com.lessard.codesamples.order.controllers;


import com.lessard.codesamples.order.JpaTestConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.*;


@ContextConfiguration(classes = {JpaTestConfiguration.class})
@WebAppConfiguration
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class SalesOrderControllerIT {


    @Before
    public void setup() {
    }

    @Test
    public void testGetSalesOrder() throws Exception {
        when().
                get("http://localhost:8080/RestSpringMvcJpaApp/salesorders/100").
                then().statusCode(200).body("id", equalTo("200"));
        //contentType(ContentType.JSON);
    }


    @Test
    public void testGetAllSalesOrder() throws Exception  {

        when().
                get("http://localhost:8080/RestSpringMvcJpaApp/salesorders").
                then().statusCode(200).body("id", equalTo("200"));
        //contentType(ContentType.JSON);

    }

}