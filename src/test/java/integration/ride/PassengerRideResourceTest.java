package integration.ride;

import integration.base.IntegrationTestBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.taxionline.adapter.outbound.account.AccountRepositoryAdapter;
import org.taxionline.adapter.outbound.position.PositionRepositoryAdapter;
import org.taxionline.adapter.outbound.ride.RideRepositoryAdapter;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.core.domain.account.AccountDTO;
import org.taxionline.core.domain.account.CreateAccountDTO;
import org.taxionline.core.domain.ride.CreateRideDTO;
import org.taxionline.port.outbound.account.AccountRepository;
import org.taxionline.port.outbound.position.PositionRepository;
import org.taxionline.port.outbound.ride.RideRepository;

import static org.hamcrest.CoreMatchers.*;

public class PassengerRideResourceTest extends IntegrationTestBase {


    private AccountDTO createAccount(boolean b, String carPlate) {
        var accountBusiness = IntegrationTestBase.registry.getBean(AccountBusiness.class);
        var dto = new CreateAccountDTO("Gon Cruz", "08102622660", "gon" + Math.random() + "@gmail.com", carPlate, !b, b, "123456", "bcrypt");
        return accountBusiness.createAccount(dto);
    }

    @BeforeEach
    void cleanDataBase() {
        var accountRepository = (AccountRepositoryAdapter) IntegrationTestBase.registry.getBean(AccountRepository.class);
        var positionRepository = (PositionRepositoryAdapter) IntegrationTestBase.registry.getBean(PositionRepository.class);
        var rideRepository = (RideRepositoryAdapter) IntegrationTestBase.registry.getBean(RideRepository.class);
        positionRepository.deleteAll();
        rideRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateRide_with200StatusCode() {
        var passenger = createAccount(false, null);
        var rideDTO = new CreateRideDTO(passenger.getIdentifier(), 20.0, 20.0, 20.0, 20.0);
        RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride")
                .then()
                .statusCode(200)
                .body("identifier", is(notNullValue()));
    }

    @Test
    public void testCreateRide_with400StatusCodeUserNotPassenger() {
        var driver = createAccount(true, "PVH3230");
        var rideDTO = new CreateRideDTO(driver.getIdentifier(), 20.0, 20.0, 20.0, 20.0);
        RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride")
                .then()
                .statusCode(400)
                .body("message", equalTo("User is not passenger"));
    }

    @Test
    public void testCreateRide_with404StatusCodeUserNotFound() {
        var rideDTO = new CreateRideDTO("1234", 20.0, 20.0, 20.0, 20.0);
        RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride")
                .then()
                .statusCode(404)
                .body("message", equalTo(String.format("Passenger with id [%s] not found", "1234")));
    }

    @Test
    public void testFindRide_with200StatusCode() {
        var passenger = createAccount(false, null);
        var rideDTO = new CreateRideDTO(passenger.getIdentifier(), 20.0, 20.0, 20.0, 20.0);
        var identifier = RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride")
                .then()
                .statusCode(200)
                .extract().body().path("identifier");

        RestAssured.given()
                .when()
                .get("/api/ride/" + identifier)
                .then()
                .statusCode(200)
                .body("identifier", is(identifier))
                .body("fromLat", is(20.0F))
                .body("toLat", is(20.0F))
                .body("fromLon", is(20.0F))
                .body("toLon", is(20.0F));

    }

    @Test
    public void testFindRide_with404StatusCode() {
        RestAssured.given()
                .when()
                .get("/api/ride/" + "1234")
                .then()
                .statusCode(404)
                .body("message", is(String.format("Ride with id [%s] not found", "1234")));
    }
}
