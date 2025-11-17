package integration.ride;

import integration.base.IntegrationTestBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.taxionline.adapter.outbound.account.AccountRepositoryAdapter;
import org.taxionline.adapter.outbound.position.PositionRepositoryAdapter;
import org.taxionline.adapter.outbound.ride.RideRepositoryAdapter;
import org.taxionline.domain.business.account.AccountBusiness;
import org.taxionline.dto.account.AccountDTO;
import org.taxionline.dto.account.CreateAccountDTO;
import org.taxionline.dto.ride.CreateRideDTO;
import org.taxionline.domain.entity.ride.RideStatus;
import org.taxionline.port.outbound.account.AccountRepository;
import org.taxionline.port.outbound.position.PositionRepository;
import org.taxionline.port.outbound.ride.RideRepository;

import static org.hamcrest.CoreMatchers.is;

public class AcceptRideResourceTest extends IntegrationTestBase {

    private AccountDTO createAccount(boolean b, String carPlate, String cpf) {
        var accountBusiness = IntegrationTestBase.registry.getBean(AccountBusiness.class);
        var dto = new CreateAccountDTO("Gon Cruz", cpf, "gon" + Math.random() + "@gmail.com", carPlate, !b, b, "123456", "bcrypt");
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
    public void testAcceptRide_with200StatusCode() {
        var passenger = createAccount(false, null, "08102622660");
        var rideDTO = new CreateRideDTO(passenger.getIdentifier(), 20.0, 20.0, 20.0, 20.0);
        var rideIdentifier = RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride")
                .getBody().path("identifier");

        var driver = createAccount(true, "PVH3230", "25494614018");
        RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride/accept-ride/" + rideIdentifier + "/" + driver.getIdentifier())
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .get("/api/ride/" + rideIdentifier)
                .then()
                .statusCode(200)
                .body("identifier", is(rideIdentifier))
                .body("driver.identifier", is(driver.getIdentifier()))
                .body("status", is(RideStatus.ACCEPTED.name()));
    }

    @Test
    public void testAcceptRide_with404StatusCode() {
        var passenger = createAccount(false, null, "08102622660");
        var rideDTO = new CreateRideDTO(passenger.getIdentifier(), 20.0, 20.0, 20.0, 20.0);
        var rideIdentifier = RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride")
                .getBody().path("identifier");

        var driver = createAccount(true, "PVH3230", "25494614018");
        RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride/accept-ride/" + rideIdentifier + "/" + driver.getIdentifier())
                .then()
                .statusCode(200);

        RestAssured.given()
                .when()
                .body(rideDTO)
                .header("Content-Type", "application/json")
                .post("/api/ride/accept-ride/" + rideIdentifier + "/" + driver.getIdentifier())
                .then()
                .statusCode(400)
                .body("message", is("Driver has an active ride"));
    }
}
