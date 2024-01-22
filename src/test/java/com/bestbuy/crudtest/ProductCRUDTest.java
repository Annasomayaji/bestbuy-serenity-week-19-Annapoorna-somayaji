package com.bestbuy.crudtest;

import com.bestbuy.bestbuyinfo.BestBuyProductSteps;
import com.bestbuy.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class ProductCRUDTest extends TestBase {

    static String name = "Duracell - AAA Batteries";
    static String type = "HardGood";
    static int price = 20;
    static int shipping = 0;
    static String upc = "1333424";
    static String description = "4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String img = "no image";
    static ValidatableResponse response;
    static int productId;

//    @Test
//    public  void dryRun(){
//
//    }

    @Steps
    BestBuyProductSteps steps;

    @Title("This will create a product")
    @Test
    public void test_001() {
        response = steps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, img);
        response.statusCode(201);
        productId = response.extract().path("id");
    }

    @Title("This will get the product by id")
    @Test
    public void test_002() {
        response = steps.getProductById(productId);
        response.statusCode(200);
        response.body("name", equalTo(name));
    }

    @Title("This will update the product")
    @Test
    public void test_003() {
        name = name + "_updated";
        response = steps.updateProductById(name, type, price, shipping, upc, description, manufacturer, model, url, img, productId);
        response.statusCode(200);
        response.body("name", equalTo(name));

//        List<HashMap<String, Object>> list = steps.getProductInfoByName(name);
//        Assert.assertThat(list.get(0) ,hasValue(name));

    }

    @Test
    public void test_005() {
        steps.deleteProductById(productId).statusCode(200);
        steps.getProductById(productId).statusCode(404);
    }


}
