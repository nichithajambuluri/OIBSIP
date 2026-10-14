package com.oasis.reservation.service;
import com.oasis.reservation.dao.UserDAO;
import com.oasis.reservation.model.User;

public class AuthService {
    private final UserDAO dao = new UserDAO();
    public User login(String username,String password) throws Exception {
        if (username==null || username.isBlank() || password==null || password.isBlank()) return null;
        return dao.authenticate(username.trim(),password);
    }
}
