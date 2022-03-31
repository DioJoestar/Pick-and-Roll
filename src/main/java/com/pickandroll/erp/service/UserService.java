package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.Role;
import com.pickandroll.erp.model.User;
import com.pickandroll.erp.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserDetailsService")
@Slf4j
public class UserService implements UserDetailsService, UserServiceInterface {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User u = userDAO.findByEmail(email);

        if (u == null) {
            throw new UsernameNotFoundException(email);
        }

        var roles = new ArrayList<GrantedAuthority>();

        for (Role r : u.getRoles()) {
            roles.add(new SimpleGrantedAuthority(r.getName()));
        }

        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), roles);
    }

    public List<User> listUsers() {
        return (List<User>) userDAO.findAll();
    }

    @Transactional
    public void addUser(User user) {
        this.userDAO.save(user);
    }

    @Transactional
    public void deleteUser(User user) {
        this.userDAO.delete(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return this.userDAO.findByEmail(email);
    }

    @Transactional
    public void updateResetPasswordToken(String token, String email) {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            this.userDAO.save(user);
        } else {
            System.out.println("Could not find any customer with the email " + email);
        }
    }

    @Transactional(readOnly = true)
    public User findByResetPasswordToken(String token) {
        return userDAO.findByResetPasswordToken(token);
    }

    @Transactional
    public void updatePassword(User user, String newPassword) {
        Utils u = new Utils();
        String encodedPassword = u.encrypPasswd(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        this.userDAO.save(user);
    }
}