package com.example.kadai_002.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.kadai_002.entity.Users;
import com.example.kadai_002.repository.UsersRepository;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
 private final UsersRepository usersRepository;        
     
     public AdminUsersController(UsersRepository usersRepository) {
         this.usersRepository = usersRepository;                
     }    
     
     @GetMapping
     public String index(@RequestParam(name = "keyword", required = false) String keyword, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
         Page<Users> userPage;
         
         if (keyword != null && !keyword.isEmpty()) {
             userPage = usersRepository.findByUserNameLikeOrFuriganaLike("%" + keyword + "%", "%" + keyword + "%", pageable);                   
         } else {
             userPage = usersRepository.findAll(pageable);
         }        
         
         model.addAttribute("userPage", userPage);        
         model.addAttribute("keyword", keyword);                
         
         return "admin/users/index";
     }
     
     @GetMapping("/{id}")
     public String show(@PathVariable(name = "id") Integer id, Model model) {
         Users users = usersRepository.getReferenceById(id);
         
         model.addAttribute("users", users);
         
         return "admin/users/show";
     }    
}
