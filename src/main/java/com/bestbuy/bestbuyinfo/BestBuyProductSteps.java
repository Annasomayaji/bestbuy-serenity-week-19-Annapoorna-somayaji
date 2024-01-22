package com.bestbuy.bestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;
import java.util.List;

public class BestBuyProductSteps {

    @Step("Creating a product with name : {0}, type : {1}, price : {2}, shipping : {3}, upc : {4}, description : {5}, manufacturer : {6}, model : {7}, url : {8}, image : {9}")
    public ValidatableResponse createProduct(String name, String type, int price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post(EndPoints.PRODUCT_POST_END_POINT)
                .then().log().all();
    }

    @Step("Getting product with id : {0}")
    public ValidatableResponse getProductById(int productId) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .get(EndPoints.PRODUCT_BY_ID)
                .then().log().all();
    }

    @Step("Creating a product with name : {0}, type : {1}, price : {2}, shipping : {3}, upc : {4}, description : {5}, manufacturer : {6}, model : {7}, url : {8}, image : {9}, productId : {10}")
    public ValidatableResponse updateProductById(String name, String type, int price, int shipping, String upc, String description, String manufacturer, String model, String url, String image, int productId) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .contentType(ContentType.JSON)
                .when()
                .body(ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image))
                .patch(EndPoints.PRODUCT_BY_ID)
                .then().log().all();
    }

    @Step("Getting product information by name : {0}")
    public List<HashMap<String, Object>> getProductInfoByName(String name) {

        ValidatableResponse response = SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200);
        List<HashMap<String, Object>> list = response.extract().path("data.findAll{it.id == '" + name + "'}");
        System.out.println(list.get(0));
        return list;

    }

    @Step("Deleting product by id : {0}")
    public ValidatableResponse deleteProductById(int productId) {
        return SerenityRest.given()
                .pathParam("productID", productId)
                .when()
                .delete(EndPoints.PRODUCT_BY_ID)
                .then().log().all();
    }


}
