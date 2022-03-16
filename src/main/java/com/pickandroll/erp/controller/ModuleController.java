package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.ModuleDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pickandroll.erp.model.Module;

@org.springframework.stereotype.Controller
public class ModuleController {

    @Autowired
    private ModuleDAO moduleDao;

    @GetMapping("/modules")
    public String modules(Model model) {
        
        List<Module> modules = moduleDao.findAll();

        model.addAttribute("modules", modules);
        
        return "modules";
    }

}
