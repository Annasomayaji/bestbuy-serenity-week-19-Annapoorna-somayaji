package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BestBuyStoreSteps {

    @Step("Creating store with name : {0}, type : {1}, address : {2}, address2 : {3}, city : {4}, state : {5}, zip : {6}, latitude : {7} , longitude : {8}, hours : {9}, services : {10}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state, String zip, Double lat, Double lng, String hours, HashMap<String, Object> services) {
//        StorePojo storePojo = new StorePojo();
//        storePojo.setName(name);
//        storePojo.setType(type);
//        storePojo.setAddress(address);
//        storePojo.setAddress2(address2);
//        storePojo.setCity(city);
//        storePojo.setState(state);
//        storePojo.setZip(zip);
//        storePojo.setLat(lat);
//        storePojo.setLng(lng);
//        storePojo.setHours(hours);
//        storePojo.setServices(services);

        StorePojo storePojo=StorePojo.getStorePojo(name, type, address, address2,city, state, zip, lat, lng,  hours, services);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .post(EndPoints.STORE_POST_END_POINT)
                .then().log().all();
    }

    @Step("Getting the store with name : {0}")
    public List<HashMap<String, Object>> getStoreByName(String name) {

        ValidatableResponse response = SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then();
        response.extract().path("data.findAll{it.name == '" + name + "'}");

        //The below extraction gives 'null' list because API  stores are not updated when  store is created
        //It returns the store details when an already existing store is given in the path for extraction, ex: Maplewood as name
        List<HashMap<String, Object>> info = response.extract().path("data.findAll{it.name == '" + name + "'}");
        System.out.println("Store details matching the  Store name in the response is :" + info);
        return info;
    }

    @Step("Getting store by Id : {0}")
    public ValidatableResponse getStoreById(int storeId) {
        return given()
                .pathParam("storeID", storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().log().all();
    }

    @Step("Updating store by Id : {0}, name : {1}, type : {2}, address : {3}, address2 : {4}, city : {5}, state : {6}, zip : {7}, latitude : {8} , longitude : {9}, hours : {10}, services : {11}")
    public ValidatableResponse updateStoreById(int storeId, String name, String type, String address, String address2, String city, String state, String zip, Double lat, Double lng, String hours, HashMap<String, Object> services) {

//        StorePojo storePojo = new StorePojo();
//        storePojo.setName(name);
//        storePojo.setType(type);
//        storePojo.setAddress(address);
//        storePojo.setAddress2(address2);
//        storePojo.setCity(city);
//        storePojo.setState(state);
//        storePojo.setZip(zip);
//        storePojo.setLat(lat);
//        storePojo.setLng(lng);
//        storePojo.setHours(hours);
//        storePojo.setServices(services);

        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .contentType(ContentType.JSON)
                .when()
                .body(StorePojo.getStorePojo(name, type, address, address2,city, state, zip, lat, lng,  hours, services))
                .patch(EndPoints.UPDATE_STORE_BY_ID)
                .then().log().all();

    }

    @Step("Deleting store by Id: {0}")
    public ValidatableResponse deleteStoreById(int storeId){
      return  SerenityRest.given()
                .pathParam("storeID",storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then().log().all();
    }


}
