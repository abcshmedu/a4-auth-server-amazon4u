package edu.hm.shareit.auth;

import edu.hm.shareit.mediaService.MediaServiceResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;
/**
 *
 */
@Path("auth")
public class Authentication {
    private final static Users users = new Users();
    private final static Set<Token> tokens = new HashSet<>();

    public Authentication() {
        System.out.println("instance of Authentication created");
        tokens.add(new Token("DebugToken"));
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(LoginAttempt loginAttempt) {
        if (users.logIn(loginAttempt.getUsername(), loginAttempt.getPassword())) {
            Token token = Token.createToken();
            while (tokens.contains(token)) {
                token = Token.createToken();
            }
            tokens.add(token);
            return MediaServiceResult.loginSuccess(token);
        }

        return MediaServiceResult.LOGIN_FAILURE.getResponse();
    }

    @GET
    @Path("authorize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorize(@Context HttpHeaders headers){
        String header = headers.getRequestHeader("Token").get(0);
        Token token = new Token(header);
        System.out.println("Authentifiaction >>> authorize >>> " + token);
        if(tokens.contains(token)){
            return Response.ok().build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public static class LoginAttempt {
        final String username, password;
        public LoginAttempt() {
            this.username = "";
            this.password = "";
        }

        public LoginAttempt(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
