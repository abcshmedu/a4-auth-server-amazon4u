package edu.hm.shareit.auth;

import edu.hm.shareit.mediaService.MediaServiceResult;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response logIn(String username, String password) {
        if(users.logIn(username, password)){
            Token token = Token.createToken();
            while(tokens.contains(token)){
                token = Token.createToken();
            }

            tokens.add(token);
            return Response.ok().entity(token).build();

        }

        return null;
    }

}
