package edu.hm.shareit.auth;


import edu.hm.shareit.mediaService.MediaServiceResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Path("auth")
public class Authentification {
    private final Users users = new Users();
    private final Set<Token> tokens = new HashSet<>();

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authorize(Token token){
        if(tokens.contains(token)){
            return Response.ok().build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    static class LoginAttempt {
        final String username, password;

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
