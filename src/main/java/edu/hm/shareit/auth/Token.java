package edu.hm.shareit.auth;

import java.util.Random;


public class Token {
    final String token;

    public Token(String token) {
        this.token = token;
    }

    private Token(){
        this.token = "";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        return getToken() != null ? getToken().equals(token1.getToken()) : token1.getToken() == null;

    }

    @Override
    public int hashCode() {
        return getToken() != null ? getToken().hashCode() : 0;
    }
}
