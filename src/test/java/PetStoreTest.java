import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class PetStoreTest {
    JsonFileToString jsonFileToString = new JsonFileToString();

    Gson gson = new Gson();
    //1. Реализовать проверку тела ответа через файл из ресурсов

    @Test(priority = -1)
    public void postPetTest() throws IOException {
        Response response = given()
                .header("content-type", ContentType.JSON)
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Lary\"\n" +
                        "  },\n" +
                        "  \"name\": \"cat\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"http://st.sda/sdasd/sadsd\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 1,\n" +
                        "      \"name\": \"He\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();

        assertEquals(response.asString(), jsonFileToString.getStringFromJSON("expectedResultForGetPet"));

    }

    //2. Реализовать проверку через jsonpath
    @Test
    public void putPetTest() {
        given()
                .header("content-type", ContentType.JSON)
                .body("{\n" +
                        "  \"id\": 22,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 22,\n" +
                        "    \"name\": \"Stark\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"https://petstore.swagger/Stark\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 22,\n" +
                        "      \"name\": \"Sdf\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .log().body()
                .body("category.name", equalTo("Stark"))
                .body("id", equalTo(22))
                .body("name", equalTo("doggie"))
                .body("status", equalTo("available"))
                .statusCode(200);


    }

    @Test
    public void getPetTest() {
        Response response = given()
                .header("content-type", ContentType.JSON)
                .when()
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();


    }

    //3. Реализовать проверку через десериализацию и сравнение объектов (enpoint на выбор)
    @Test
    public void getByIdPetTest() throws IOException {
        Response response = given()
                .header("content-type", ContentType.JSON)
                .when()
                .get("https://petstore.swagger.io/v2/pet/1")
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();
        FileReader json = new FileReader(new File("src/test/resources/expectedResultForGetPet"));
        gson.fromJson(json, Pet.class);
        Assert.assertEquals(gson.fromJson(response.asString(), Pet.class), gson.fromJson(jsonFileToString.getStringFromJSON("expectedResultForGetPet"), Pet.class));


    }


    @Test
    public void deletePetByIdTest() {

        given()
                .header("content-type", ContentType.JSON)
                .when()
                .body("")
                .delete("https://petstore.swagger.io/v2/pet/3123")
                .then()
                .log().body()
                .statusCode(404);


    }
}
