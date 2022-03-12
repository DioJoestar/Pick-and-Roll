package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.UserDAO;
import com.pickandroll.erp.model.Role;
import com.pickandroll.erp.model.User;
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

@Service
public class UserService implements UserDetailsService {

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

        return new org.springframework.security.core.userdetails.User(u.getName(), u.getPassword(), roles);
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
    public User findUserId(User user) {
        return this.userDAO.findById(user.getId()).orElse(null);
    }
}
