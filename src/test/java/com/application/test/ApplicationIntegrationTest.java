package com.application.test;

import com.application.Application;
import com.application.container.MongoDBContainer;
import com.application.factory.UserFactory;
import com.application.model.User;
import com.application.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = ApplicationIntegrationTest.MongoDbInitializer.class)
public class ApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    private static MongoDBContainer mongoDbContainer;
    private static TestRestTemplate restTemplate;
    private static HttpHeaders headers;

    @BeforeAll
    public static void startMongoDBContainer() {
        mongoDbContainer = new MongoDBContainer();
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        mongoDbContainer.start();
    }

    @Test
    public void testMongoDBcontainer() {
        //Testing if MongoDB is available
        User user = UserFactory.getGeneratedUser();
        userService.save(user);
        Collection<User> users = userService.findAll();
        Assertions.assertEquals(1, users.size());
    }

    @Test
    public void testUserController() throws IOException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> responsePost = restTemplate.exchange(
                createURLWithPort("/createUser?username=Test User 1"),
                HttpMethod.POST, entity, String.class);

        Assertions.assertEquals(HttpStatus.OK, responsePost.getStatusCode());

        ResponseEntity<String> responseGet = restTemplate.exchange(
                createURLWithPort("/getUsers"),
                HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        List users = mapper.readValue(responseGet.getBody(),  new TypeReference<List<User>>(){});
        Assertions.assertTrue(users.size() > 0);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    //This class simulates the mongoDB properties to make it available for the tests.
    public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoDbContainer.getPort()

            );
            values.applyTo(configurableApplicationContext);
        }
    }

}
