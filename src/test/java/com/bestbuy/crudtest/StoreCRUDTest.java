package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.BestBuyStoreSteps;
import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBase {
    static String name = "StoreA";//for POST request
    static String type = "Hardware";//for POST request
    static String address = "24, The avenue";
    static String address2 = "PB";
    static String city = "London";
    static String state = "England";
    static String zip = "34dc5";
    static Double lat = 23.00;

    static Double lng = 45.00;
    static String hours = "Mon: 10-9";
    static HashMap<String, Object> services = new HashMap<String, Object>();

    static int storeId;
    static ValidatableResponse response;

    @Steps
    BestBuyStoreSteps steps;

//    @Test
//    public void dryRun() {
//
//    }


    @Title("This will create a new store and verify that store is created")
    @Test
   // @Order(1)
    public void test_001() {
        response = steps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, services);
        response.statusCode(201);

        storeId = response.extract().path("id");
        response.body("name", equalTo(name));
        System.out.println("Store Id created is :" + storeId);
    }


    @Title("This will verify if the store is added to the application")
    @Test
   // @Order(2)
    public void test_002() {
        List<HashMap<String, Object>> storeInfo = steps.getStoreByName(name);//This returns an empty list because the created store is no reflected in all stores
        System.out.println(storeInfo);
        Assert.assertThat(storeInfo.get(0) ,hasValue(name));
    }


    @Title("This will get a store by id and verify the store is created")
    @Test
   // @Order(3)
    public void test_003() {

        response = steps.getStoreById(storeId);
        response.statusCode(200);

        response.body("name", equalTo(name));
        String nameOfStore = response.extract().path("name");
        System.out.println("Name of the store created: " + nameOfStore);
    }


    @Title("This will update the store and verify the update")
    @Test
   // @Order(4)
    public void test_004() {
        name = name + "_updated";
        ValidatableResponse response = steps.updateStoreById(storeId, name, type, address, address2, city, state, zip, lat, lng, hours, services);
        response.statusCode(200);
        response.body("name", equalTo(name));

    }


    @Test
   // @Order(5)
    public void test_005() {
        steps.deleteStoreById(storeId).statusCode(200);
        steps.getStoreById(storeId).statusCode(404);
    }


}
