package account;

import base.IntegrationTestBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.taxionline.core.domain.account.CreateAccountDTO;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;

public class AccountResourceTest extends IntegrationTestBase {

    @Test
    public void testCreateAccount_with200StatusCode() {
        String email = "gon" + Math.random() + "@gmail.com";
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "08102622660", email, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(200)
                .body("identifier", is(notNullValue()))
                .body("name", is("Gon Cruz"))
                .body("cpf", is("08102622660"))
                .body("email", is(email))
                .body("idPassenger", is(false))
                .body("driver", is(false));
    }

    @Test
    public void testCreateAccount_with409StatusCodeCPFDuplicated() {
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "277.979.950-10", "gon@gmail.com", null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(409)
                .body("message", equalTo("Cpf already exists"));
    }

    @Test
    public void testFindAccount_with409StatusCodeEmailDuplicated() {
        String email = "gon" + Math.random() + "@gmail.com";
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "035.296.400-69", email, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(200)
                .extract().body().path("identifier");

        CreateAccountDTO dto2 = new CreateAccountDTO("Gon Cruz", "861.284.280-88", email, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto2)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(409)
                .body("message", equalTo("Email already exists"));
    }

    @Test
    public void testFindAccount_with404StatusCode() {
        String identifier = UUID.randomUUID().toString();
        RestAssured.given()
                .when()
                .get("/api/account/" + identifier)
                .then()
                .statusCode(404)
                .body("message", equalTo(String.format("Account with id [%s] not found", identifier)));
    }

    @Test
    public void testFindAccount_with200StatusCode() {
        String email = "gon" + Math.random() + "@gmai.com";
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "166.956.200-08", email, null, false, false, "123456", "not used");
        String identifier = RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(200)
                .extract().body().path("identifier");

        RestAssured.given()
                .when()
                .get("/api/account/" + identifier)
                .then()
                .statusCode(200)
                .body("identifier", is(identifier));
    }

    @Test
    public void testCreateAccount_with400StatusCodeInvalidCpf() {
        String email = "gon" + Math.random() + "@gmail.com";
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "123456789", email, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Invalid CPF"));
    }

    @Test
    public void testCreateAccount_with400StatusCodeNullAndEmptyCpf() {
        String email = "gon" + Math.random() + "@gmail.com";
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", null, email, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Field CPF mandatory"));

        String email2 = "gon" + Math.random() + "@gmail.com";
        CreateAccountDTO dto2 = new CreateAccountDTO("Gon Cruz", "", email2, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto2)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Field CPF mandatory"));
    }

    @Test
    public void testCreateAccount_with400StatusCodeNullAndEmptyEmail() {
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "086.007.180-40", null, null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Field email mandatory"));

        CreateAccountDTO dto2 = new CreateAccountDTO("Gon Cruz", "909.743.860-81", "", null, false, false, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto2)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Field email mandatory"));
    }

    @Test
    public void testCreateDriverAccount_with200StatusCode() {
        String email = "gon" + Math.random() + "@gmail.com";
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "254.946.140-18", email, "PVH3230", false, true, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(200)
                .body("identifier", is(notNullValue()))
                .body("name", is("Gon Cruz"))
                .body("cpf", is("25494614018"))
                .body("email", is(email))
                .body("idPassenger", is(false))
                .body("driver", is(true))
                .body("carPlate", is("PVH3230"));
    }

    @Test
    public void testCreateDriverAccount_with400StatusCodeInvalidCarPlate() {
        CreateAccountDTO dto = new CreateAccountDTO("Gon Cruz", "816.005.460-21", "gon" + Math.random() + "@gmail.com", null, false, true, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Invalid car plate"));

        CreateAccountDTO dto2 = new CreateAccountDTO("Gon Cruz", "857.275.700-77", "gon" + Math.random() + "@gmail.com", "", false, true, "123456", "not used");
        RestAssured.given()
                .when()
                .body(dto2)
                .header("Content-Type", "application/json")
                .post("/api/account")
                .then()
                .statusCode(400)
                .body("message", equalTo("Invalid car plate"));
    }
}
