package com.dam.DI.RecuperacionFinal.dao;

import com.dam.DI.RecuperacionFinal.model.User;

public interface UserDAO {
    boolean registerUser(User user);
    User loginUser(String username, String password);
}
