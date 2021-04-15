import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.matcher.RestAssuredMatchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

@Test
public class IntegralTest {
    Response response = RestAssured.get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZMzNDdUyZ");

    @Test
    public void test() {


        System.out.println(response.statusCode());
        System.out.println(response.asString());
        System.out.println(response.getBody().asString());
        System.out.println(response.statusLine());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

    }

    @Test
    public void test1() {

        given().get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZMzNDdUyZ").then()

                .statusCode(200).body("$", not(hasValue(nullValue())))
                .body("promotions.localizedTexts[0]", hasKey("ar"))
                .body("promotions.localizedTexts[1]", hasKey("en"))
        .body("promotions.promoType",hasItems("REWIND","VOD","SERIES"))
        .log().all();

        boolean price, showText;
        price = Boolean.parseBoolean(given().get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZMzNDdUyZ").then().extract()
                .path("promotions.showPrice").toString());
        showText = Boolean.parseBoolean(given().get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZMzNDdUyZ").then().extract()
                .path("promotions.showText").toString());
        System.out.println(price);
        Assert.assertNotNull((price));
        Assert.assertNotNull((showText));

        }
        @Test
    public void test2()
        {
            given().get("http://api.intigral-ott.net/popcorn-api-rs-7.9.10/v1/promotions?apikey=GDMSTGExy0sVDlZ").then()

                    .statusCode(403)
                    .body("error.message", equalTo("invalid api key"))
                    .body("error.code", equalTo("8001"))
                    .body("error.requestId",not(hasValue(nullValue())))
                    .log().all();



        }
    }




