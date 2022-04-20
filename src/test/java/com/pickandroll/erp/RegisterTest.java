package com.pickandroll.erp;

import com.pickandroll.erp.model.User;
import com.pickandroll.erp.service.UserServiceInterface;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegisterTest {

    @Autowired
    private UserServiceInterface userService;

    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("Test");
        testUser.setSurname("Test");
        testUser.setDni("12345678A");
        testUser.setEmail("test@test.com");
        testUser.setPhone("123456789");
        testUser.setPassword("12345");
    }

    @Test
    @Order(1)
    public void findByEmail() throws Exception {
        assertEquals("Raul", userService.findByEmail("jc.raul92@gmail.com").getName());
    }

    @Test
    @Order(2)
    public void addUser() throws Exception {
        userService.addUser(testUser);
        assertEquals("12345678A", userService.findByEmail("test@test.com").getDni());
    }

    @Test
    @Order(3)
    public void deleteUser() throws Exception {
        userService.deleteUser(userService.findByEmail("test@test.com"));
        assertEquals(null, userService.findByEmail("test@test.com"));
    }
}
