package edu.hm.shareit.auth;

import java.util.Random;

/**
 * Created by aykut on 24.05.2017.
 */
public class Token {
    final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public static Token createToken(){
        Random random = new Random();

        String token = "";

        for(int i = 0; i < 50; i++){
            token += (char) random.nextInt();
        }

        return new Token(token);
    }
}
