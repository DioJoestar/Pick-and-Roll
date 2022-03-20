package com.pickandroll.erp.controller;

import com.pickandroll.erp.dao.ModuleDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pickandroll.erp.model.Module;

/**
 *
 * @author santialves
 */
@org.springframework.stereotype.Controller
public class ConfigModuleController {

    @Autowired
    private ModuleDAO moduleDao;

    @GetMapping("/configModule")
    public String modules(Model model) {

        List<Module> modules = moduleDao.findAll();

        model.addAttribute("modules", modules);

        return "configModule";
    }
}
