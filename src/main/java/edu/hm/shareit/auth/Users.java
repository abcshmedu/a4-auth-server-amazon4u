package edu.hm.shareit.auth;

import java.util.HashMap;
import java.util.Map;


class Users {
    private Map<String, String> users = new HashMap<>();



    Users() {
        addUser("Rick Astley", "Never gonna give you up!");
    }


    public boolean addUser(String username, String passwort) {
        if (!users.containsKey(username)) {
            users.put(username, passwort);
            return true;
        }
        return false;
    }

    public boolean isUser(String username){
        return users.containsKey(username);
    }

    public boolean logIn(String username,String passwort){
        if(isUser(username)){
            return users.get(username).equals(passwort);
        }
        return false;
    }
}
