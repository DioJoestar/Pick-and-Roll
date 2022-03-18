package com.pickandroll.erp.service;

import com.pickandroll.erp.model.Module;
import java.util.List;

public interface ModuleServiceInterface {

    public List<Module> listModules();

    public void addModule(Module module);

    public void deleteModule(Module module);

    public Module findModule(Module module);
}
