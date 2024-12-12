package com.example.kadai_002.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.repository.StoresRepository;

@Controller
public class HomeController {
    private final StoresRepository storesRepository;        
    
    public HomeController(StoresRepository storesRepository) {
        this.storesRepository = storesRepository;            
    }   
    
    
    @GetMapping("/")
    public String index(Model model) {
        List<Stores> newHouses = storesRepository.findTop10ByOrderByCreatedDateDesc();
        model.addAttribute("newHouses", newHouses);        
       
       return "index";
   }   
}
