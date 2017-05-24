package edu.hm.shareit.auth;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;


class Users {
    private Map<String, String> users = new HashMap<>();

    Users() {
        addUser("Rick Astley", "Never gonna give you up!");
    }


    public boolean addUser(@NotNull String username, @NotNull String passwort) {
        if (!users.containsKey(username)) {
            users.put(username, passwort);
            return true;
        }
        return false;
    }

    public boolean isUser(@NotNull String username){
        return users.containsKey(username);
    }

    public boolean logIn(@NotNull String username, @NotNull String passwort){
        if(isUser(username)){
            return users.get(username).equals(passwort);
        }

        return false;
    }
}
