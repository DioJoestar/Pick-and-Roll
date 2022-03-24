package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.ModuleDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pickandroll.erp.model.Module;
import com.pickandroll.erp.service.ModuleServiceInterface;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ConfigModuleController {

    @Autowired
    private ModuleDAO moduleDao;
    
    @Autowired
    private ModuleServiceInterface moduleService;

    List<Module> modules = new ArrayList<>();
    
    @GetMapping("/configModule")
    public String modules(Model model) {

        modules = moduleDao.findAll();

        model.addAttribute("modules", modules);

        return "configModule";
    }

    @PostMapping("/guardarModul")
    public String saveModule(Model model, Module module) {
        
        moduleService.addModule(module); 
        
        return "redirect:/configModule";
    }

    @GetMapping("/editar/{id}")
    public String editar(Module module, Model model) {

        module = moduleService.findModule(module);

        model.addAttribute("module", module);

        return "editModule";
    }
    
}
