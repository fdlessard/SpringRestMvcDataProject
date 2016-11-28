package io.fdlessard.codesamples.order.controllers;


import io.fdlessard.codesamples.order.configurations.PersistenceItTestConfiguration;
import io.fdlessard.codesamples.order.configurations.WebMvcItTestConfiguration;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.Matchers.hasItems;


@ContextConfiguration(classes = {PersistenceItTestConfiguration.class, WebMvcItTestConfiguration.class})
@WebAppConfiguration
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class SalesOrderControllerIT {

    private Date today = Calendar.getInstance().getTime();

    @Before
    public void setup() {
    }

    @Test
    public void testGetSalesOrder() throws Exception {
        when().
                get("http://localhost:8080/RestSpringMvcDataApp/salesorders/100").
                then().statusCode(200).body("id", equalTo(100)).
                contentType(ContentType.JSON);
    }


    @Test
    public void testGetSalesOrderWithNonExistingId() throws Exception {

        when().
                get("http://localhost:8080/RestSpringMvcDataApp/salesorders/1000").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetSalesOrderWithInvalidId() throws Exception {

        when().
                get("http://localhost:8080/RestSpringMvcDataApp/salesorders/aaaa").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testGetAllSalesOrder() throws Exception {

        when().
                get("http://localhost:8080/RestSpringMvcDataApp/salesorders").
                then().statusCode(200).body("id", hasItems(100, 200, 300)).
                contentType(ContentType.JSON);
    }

    @Test
    public void testCreateSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = dateFormat.format(today);

        String postStr = "{\"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(postStr ).
            when().post("http://localhost:8080/RestSpringMvcDataApp/salesorders").
                then().statusCode(201);
    }

    @Test
    public void testCreateSalesOrderWithAlreadyExistingSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = dateFormat.format(today);

        String postStr = "{\"id\" : \"100\", \"description\":\"SalesOrder IT\", \"date\": " + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(postStr ).
                when().post("http://localhost:8080/RestSpringMvcDataApp/salesorders").
                then().statusCode(201);
    }

    @Test
    public void testUpdateSalesOrder() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String todayStr = dateFormat.format(today);

        String putStr = "{\"id\": \"100\",\"description\":\"SalesOrder Updated\", \"date\":" + "\"" + todayStr + "\",\"total\": \"10.00\" }";

        given().contentType(ContentType.JSON).body(putStr).
                when().put("http://localhost:8080/RestSpringMvcDataApp/salesorders").
                then().statusCode(200);
    }


    @Test
    public void testDeleteSalesOrderWithNonExistingId() throws Exception {

        when().
                delete("http://localhost:8080/RestSpringMvcDataApp/salesorders/1000").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }

    @Test
    public void testDeleteSalesOrderWithInvalidId() throws Exception {

        when().
                delete("http://localhost:8080/RestSpringMvcDataApp/salesorders/aaaa").
                then().statusCode(404).
                contentType(ContentType.JSON);
    }
}