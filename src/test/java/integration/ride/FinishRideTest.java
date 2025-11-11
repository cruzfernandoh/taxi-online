package integration.ride;

import integration.base.IntegrationTestBase;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.taxionline.adapter.outbound.account.AccountRepositoryAdapter;
import org.taxionline.adapter.outbound.position.PositionRepositoryAdapter;
import org.taxionline.adapter.outbound.ride.RideRepositoryAdapter;
import org.taxionline.core.business.account.AccountBusiness;
import org.taxionline.core.business.position.PositionBusiness;
import org.taxionline.core.business.ride.RideBusiness;
import org.taxionline.core.domain.account.AccountDTO;
import org.taxionline.core.domain.account.CreateAccountDTO;
import org.taxionline.core.domain.position.UpdatePositionDTO;
import org.taxionline.core.domain.ride.CreateRideDTO;
import org.taxionline.core.domain.ride.RideDTO;
import org.taxionline.core.domain.ride.RideStatus;
import org.taxionline.port.outbound.account.AccountRepository;
import org.taxionline.port.outbound.position.PositionRepository;
import org.taxionline.port.outbound.ride.RideRepository;

public class FinishRideTest extends IntegrationTestBase {

    private final PositionBusiness positionBusiness;
    private final RideBusiness rideBusiness;

    public FinishRideTest() {
        this.positionBusiness = IntegrationTestBase.registry.getBean(PositionBusiness.class);
        this.rideBusiness = IntegrationTestBase.registry.getBean(RideBusiness.class);
    }

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
    public void testFinishRide() {
        var passenger = createAccount(false, null, "08102622660");
        var rideDTO = new CreateRideDTO(passenger.getIdentifier(), 20.0, 20.0, 20.0, 20.0);
        var rideIdentifier = (String) RestAssured.given()
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
                .post("/api/ride/start-ride/" + rideIdentifier)
                .then()
                .statusCode(200);

        positionBusiness.updatePosition(new UpdatePositionDTO(rideIdentifier, -18.93906840331504, -48.30752306347402));
        positionBusiness.updatePosition(new UpdatePositionDTO(rideIdentifier, -18.952004905817855, -48.3535568383149));
        positionBusiness.updatePosition(new UpdatePositionDTO(rideIdentifier, -18.93906840331504, -48.30752306347402));
        rideBusiness.finishRide(rideIdentifier);
        RideDTO ride = rideBusiness.getRide(rideIdentifier);
        Assertions.assertEquals(RideStatus.COMPLETED, ride.getStatus());
        Assertions.assertEquals(10, ride.getDistance());
        Assertions.assertEquals(21, ride.getFare());
    }
}
