package com.example.kadai_002.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.repository.StoresRepository;

@Controller
@RequestMapping("/admin/Stores")

public class StoresController {
    private final StoresRepository storesRepository;         
    
    public StoresController(StoresRepository storesRepository) {
        this.storesRepository = storesRepository;                
    }	
    
    @GetMapping
    public String index(Model model) {
        List<Stores> stores = storesRepository.findAll();       
        
        model.addAttribute("stores", stores);             
        
        return "admin/stores/index";
    }  
    
}
