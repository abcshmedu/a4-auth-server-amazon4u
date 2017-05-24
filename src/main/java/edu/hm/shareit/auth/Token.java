package edu.hm.shareit.auth;

import java.util.Random;

/**
 * Created by aykut on 24.05.2017.
 */
public class Token {
    final String token;
    boolean vaild, expired;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public boolean isVaild() {
        return vaild;
    }

    public void setVaild(boolean vaild) {
        this.vaild = vaild;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
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
