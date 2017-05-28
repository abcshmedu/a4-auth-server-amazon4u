package edu.hm.shareit.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
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

/**
 * Created by aykut on 28.05.2017.
 */
public class AuthenticationTest {
    final String logInData = "{\"username\" : \"Rick Astley\", \"password\" : \"Never gonna give you up!\"}";
    static final Client CLIENT = ClientBuilder.newClient();
    static final WebTarget LOG_IN_TARGET = CLIENT.target("http://localhost:8080").path("shareit/auth/login");
    static final WebTarget AUTHORIZE_TARGET = CLIENT.target("http://localhost:8080").path("shareit/auth/authorize");
    static final LoginAttempt entity = new LoginAttempt("Rick Astley", "Never gonna give you up!");
    Server jetty;
    static final String WEBAPP_DIR = "./src/main/webapp/";
    static final String APP_URL = "/";

    @Before
    public void setUp() throws Exception {
        jetty = new Server(8080);
        jetty.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));
        jetty.start();
    }

    @Test
    public void firstLogIn() throws Exception {
        final Response have = LOG_IN_TARGET
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(convertToJson(entity), MediaType.APPLICATION_JSON_TYPE));
        System.out.println(convertToJson(entity));
        System.out.println("---------------------AMK");
        System.out.println(have.getEntity());
        System.out.println("---------------------AMK");
        final ObjectMapper mapper = new ObjectMapper();

        Token token = mapper.readValue(have.getEntity().toString(), Token.class);

        assertTrue(have.getStatus() == 200 && token.getToken().length() == 50);
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