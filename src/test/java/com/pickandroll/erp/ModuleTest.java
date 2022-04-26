package com.pickandroll.erp;

import com.pickandroll.erp.service.ModuleServiceInterface;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.pickandroll.erp.model.Module;
import org.junit.jupiter.api.Order;

@SpringBootTest
class ModuleTest {

    @Autowired
    private ModuleServiceInterface moduleService;

    Module module;
    Module m2;

    //Inicialitzar l'objecte mòdul
    @BeforeEach
    public void setUp() {
        module = new Module();
        module.setDescription("Desc test");
        module.setEnable(true);
        module.setIcon("bi bicycle");
        module.setId(13L);
        module.setName("prueba");
        module.setPath("/test");
    }

    @Test
    @Order(1)
    public void addModule() throws Exception {
        moduleService.addModule(module);
        assertEquals("prueba", moduleService.findModuleByName("prueba").getName());
    }

    @Test
    @Order(2)
    public void findModule() throws Exception {
        assertEquals("prueba", moduleService.findModuleByName("prueba").getName());
    }

    @Test
    @Order(3)
    public void deleteModule() throws Exception {
        Module mmm = moduleService.findModuleByName("prueba");
        moduleService.deleteModule(mmm);
        assertEquals(null, moduleService.findModule(mmm));
    }

}
