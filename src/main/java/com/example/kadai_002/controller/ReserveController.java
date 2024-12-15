package com.example.kadai_002.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.kadai_002.entity.Reserve;
import com.example.kadai_002.entity.Users;
import com.example.kadai_002.repository.ReserveRepository;
import com.example.kadai_002.security.UsersDetailsImpl;

@Controller
public class ReserveController {

    private final ReserveRepository reserveRepository; // フィールド名を小文字で修正

    public ReserveController(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    @GetMapping("/reserve")
    public String index(
            @AuthenticationPrincipal UsersDetailsImpl usersDetailsImpl,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {
        Users users = usersDetailsImpl.getUser();

        
        Page<Reserve> reservePage = reserveRepository.findByUsers(users, pageable);

        model.addAttribute("reservePage", reservePage);

        return "reserve/index"; // テンプレート名の修正（小文字で統一）
    }
}
