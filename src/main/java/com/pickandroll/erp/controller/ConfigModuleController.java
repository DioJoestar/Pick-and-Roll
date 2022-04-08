package com.pickandroll.erp.controller;

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
    private ModuleServiceInterface moduleService;

    List<Module> modules = new ArrayList<>();
    
    //Mostrar els moduls a configurar
    @GetMapping("/configModule")
    public String modules(Model model) {

        //Llistar tots els modulss
        modules = moduleService.listModules();

        //Afegir els moduls per veure'ls a l'html
        model.addAttribute("modules", modules);

        //Anar a la pantalla de configurar mòduls
        return "configModule";
    }

    //Guardar un mòdul
    @PostMapping("/guardarModul")
    public String saveModule(Model model, Module module) {
        
        //Actualitzar el mòudl
        moduleService.addModule(module); 
        
        return "redirect:/configModule";
    }

    //Editar el mòdul
    @GetMapping("/editar/{id}")
    public String editar(Module module, Model model) {

        //Cercar el mòdul per editar
        module = moduleService.findModule(module);

        //Afegir el mòdul a editar
        model.addAttribute("module", module);

        return "editModule";
    }
    
}
