package com.pickandroll.erp.service;

import com.pickandroll.erp.model.User;
import java.util.List;

/**
 *
 * @author santialves
 */
public interface userServiceInterface {

    public List<User> llistarUsuaris();

    public void afegirUsuari(User user);

    public void eliminarUsuari(User user);

    public void editarUsuari(User user);

}
