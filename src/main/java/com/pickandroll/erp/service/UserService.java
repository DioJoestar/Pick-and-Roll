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

@Service("userDetailsService")
@Slf4j
public class UserService implements UserDetailsService, userServiceInterface {

    @Autowired
    private UserDAO usuariDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User u = usuariDAO.findByEmail(email);

        if (u == null) {
            throw new UsernameNotFoundException(email);
        }

        var roles = new ArrayList<GrantedAuthority>();

        for (Role r : u.getRoles()) {
            roles.add(new SimpleGrantedAuthority(r.getName()));
        }

        return new org.springframework.security.core.userdetails.User(u.getName(), u.getPassword(), roles);
    }
    
    @Override
    @Transactional
    public void afegirUsuari(User user){
        
    }

    @Override
    @Transactional
    public List<User> llistarUsuaris() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public void eliminarUsuari(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public void editarUsuari(User user) {
        
        this.usuariDAO.save(user);
        
    }
    
    
}
