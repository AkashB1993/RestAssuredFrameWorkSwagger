package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker; // it will generate the fake data
	User userPayload;
	public Logger logger;

	@BeforeClass
	public void setupData() {

		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());// hashCode to generate random number
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("******************** Creating User ********************");
		Response response = UserEndPoints.createUser(userPayload); 
		response.then().log().all();
		System.out.println("user Name is:- "+userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******************** User is created ********************");
	}
	@Test(priority = 2)
	public void testGetUserByName() {
		logger.info("******************** Reading User Info ********************");
		Response response = UserEndPoints.readUser(userPayload.getUsername()); 
		response.then().log().all();	
		Assert.assertEquals(response.getStatusCode(), 200); // when you assert the statusCode then
		 //we need to getStatusCode() as it is in assert
		logger.info("******************** User info displayed  ********************");
	}
	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("******************** Updating User Info ********************");
		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload ); 
		response.then().log().body();	
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******************** Updated  User Info ********************");
		//Checking data after update 
		Response responseAfterUpdate = UserEndPoints.readUser(userPayload.getUsername()); 
		responseAfterUpdate.then().log().all();	
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	@Test(priority = 4)
	public void testDeleteUserByName() {
		logger.info("******************** deleting  User Info ********************");
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername()); 
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******************** deleted  User Info ********************");
	}
	
}
