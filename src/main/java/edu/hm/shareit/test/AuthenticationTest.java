package edu.hm.shareit.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import edu.hm.shareit.auth.Token;

import static org.junit.Assert.*;
import static edu.hm.shareit.auth.Authentication.*;


public class AuthenticationTest {
    static final Client CLIENT = ClientBuilder.newClient();
    static final WebTarget LOG_IN_TARGET = CLIENT.target("http://localhost:8080").path("shareit/auth/login");
    static final WebTarget AUTHORIZE_TARGET = CLIENT.target("http://localhost:8080").path("shareit/auth/authorize");
    static final LoginAttempt entity = new LoginAttempt("Rick Astley", "Never gonna give you up!");

    static final String WEBAPP_DIR = "./src/main/webapp/";
    static final String APP_URL = "/";

    static Server jetty;

    final String logInData = "{\"username\" : \"Rick Astley\", \"password\" : \"Never gonna give you up!\"}";
    final String logInFailure = "{\"username\" : \"Rick\", \"password\" : \"Never gonna give!\"}";

    @Before
    public void setUp() throws Exception {
        jetty = new Server(8080);
        jetty.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));
        jetty.start();
        //jetty.join();
    }

    @After
    public void tearDown() throws Exception {
        jetty.stop();
        jetty.join();
        //jetty.destroy();
    }

    @Test
    public void firstLogIn() throws Exception {
        final Response have = LOG_IN_TARGET
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(logInData, MediaType.APPLICATION_JSON_TYPE));
        final ObjectMapper mapper = new ObjectMapper();

        final Token token = mapper.readValue(have.readEntity(String.class), Token.class);

        assertTrue(have.getStatus() == 200 && token.getToken().length() == 50);
    }

    @Test
    public void LogInTenTimes() throws Exception {
        for(int i = 0; i < 10; i++) {
            final Response have = LOG_IN_TARGET
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(logInData, MediaType.APPLICATION_JSON_TYPE));
            final ObjectMapper mapper = new ObjectMapper();

            final Token token = mapper.readValue(have.readEntity(String.class), Token.class);

            assertTrue(have.getStatus() == 200 && token.getToken().length() == 50);
        }
    }

    @Test
    public void LogInFail() throws Exception {
        final Response have = LOG_IN_TARGET
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(
                        Entity.entity(
                                logInFailure
                                , MediaType.APPLICATION_JSON_TYPE
                        )
                );
        System.out.println(have.getStatus());
        assertTrue(have.getStatus() == 404);
    }

    @Test
    public void LogInFailTenTimes() throws Exception {
        for(int i = 0; i < 10; i++) {
            final Response have = LOG_IN_TARGET
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .post(
                            Entity.entity(
                                    logInFailure
                                    , MediaType.APPLICATION_JSON_TYPE
                            )
                    );
            System.out.println(have.getStatus());
            assertTrue(have.getStatus() == 404);
        }
    }

    private static String convertToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            System.out.println("MediaServiceResult >>> convertToJson() >> Error");
            return "";
        }
    }
}