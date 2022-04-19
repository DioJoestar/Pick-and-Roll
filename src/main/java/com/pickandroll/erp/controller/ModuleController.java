package com.pickandroll.erp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pickandroll.erp.model.Module;
import com.pickandroll.erp.service.ModuleServiceInterface;
import org.springframework.stereotype.Controller;

@Controller
public class ModuleController {

    @Autowired
    private ModuleServiceInterface moduleService;

    //Mostrar les mòduls
    @GetMapping("/modules")
    public String modules(Model model) {
        
        //Llistar els mòduls
        List<Module> modules = moduleService.listModules();

        //Afegir els mòduls per mostrar-los a l'html
        model.addAttribute("modules", modules);
        
        return "modules";
    }
}
