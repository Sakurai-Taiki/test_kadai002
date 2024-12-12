package com.example.kadai_002.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.repository.StoresRepository;

@Controller
@RequestMapping("/houses")
public class HouseController {
    private final StoresRepository storesRepository;

    public HouseController(StoresRepository storesRepository) {
        this.storesRepository = storesRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "area", required = false) String area,
                        @RequestParam(name = "price", required = false) Integer price,
                        @RequestParam(name = "order", required = false) String order,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model) {
        Page<Stores> housePage;

        if (keyword != null && !keyword.isEmpty()) {
        	if (order != null && order.equals("priceAsc")) {
                housePage = storesRepository.findByStoreNameLikeOrStoreAddressLikeOrderByMinBudgetAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } else {
                housePage = storesRepository.findByStoreNameLikeOrStoreAddressLikeOrderByCreatedDateDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
            } 
        } else if (area != null && !area.isEmpty()) {
        	if (order != null && order.equals("priceAsc")) {
                housePage = storesRepository.findByStoreAddressLikeOrderByMinBudgetAsc("%" + area + "%", pageable);
            } else {
                housePage = storesRepository.findByStoreAddressLikeOrderByCreatedDateDesc("%" + area + "%", pageable);
            }  
        } else if (price != null) {
        	if (order != null && order.equals("priceAsc")) {
                housePage = storesRepository.findByMinBudgetLessThanEqualOrderByMinBudgetAsc(price, pageable);
            } else {
                housePage = storesRepository.findByMinBudgetLessThanEqualOrderByCreatedDateDesc(price, pageable);
            }     
        } else {
        	 if (order != null && order.equals("priceAsc")) {
                 housePage = storesRepository.findAllByOrderByMinBudgetAsc(pageable);
             } else {
                 housePage = storesRepository.findAllByOrderByCreatedDateDesc(pageable);   
             }   
        }

        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("price", price);
        model.addAttribute("order", order);

        return "houses/index";
    }
}
