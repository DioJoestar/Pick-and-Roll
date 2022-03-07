package com.pickandroll.erp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {

    // Retorna una contraseña encriptada
    public String encrypPasswd(String passwd) {
        BCryptPasswordEncoder e = new BCryptPasswordEncoder();
        return e.encode(passwd);
    }
}
