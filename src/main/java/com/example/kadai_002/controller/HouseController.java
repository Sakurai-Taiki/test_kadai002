package com.example.kadai_002.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.kadai_002.entity.Review;
import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.entity.Users;
import com.example.kadai_002.form.ReserveInputForm;
import com.example.kadai_002.repository.ReviewRepository;
import com.example.kadai_002.repository.StoresRepository;
import com.example.kadai_002.security.UsersDetailsImpl;
import com.example.kadai_002.service.ReviewService;

@Controller
@RequestMapping("/houses")
public class HouseController {
    private final StoresRepository storesRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    public HouseController(StoresRepository storesRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
        this.storesRepository = storesRepository;
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
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
            housePage = order != null && order.equals("priceAsc")
                ? storesRepository.findByStoreNameLikeOrStoreAddressLikeOrderByMinBudgetAsc("%" + keyword + "%", "%" + keyword + "%", pageable)
                : storesRepository.findByStoreNameLikeOrStoreAddressLikeOrderByCreatedDateDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
        } else if (area != null && !area.isEmpty()) {
            housePage = order != null && order.equals("priceAsc")
                ? storesRepository.findByStoreAddressLikeOrderByMinBudgetAsc("%" + area + "%", pageable)
                : storesRepository.findByStoreAddressLikeOrderByCreatedDateDesc("%" + area + "%", pageable);
        } else if (price != null) {
            housePage = order != null && order.equals("priceAsc")
                ? storesRepository.findByMinBudgetLessThanEqualOrderByMinBudgetAsc(price, pageable)
                : storesRepository.findByMinBudgetLessThanEqualOrderByCreatedDateDesc(price, pageable);
        } else {
            housePage = order != null && order.equals("priceAsc")
                ? storesRepository.findAllByOrderByMinBudgetAsc(pageable)
                : storesRepository.findAllByOrderByCreatedDateDesc(pageable);
        }

        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("price", price);
        model.addAttribute("order", order);

        return "houses/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model, @AuthenticationPrincipal UsersDetailsImpl usersDetailsImpl) {
        Stores store = storesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("指定された店舗IDは存在しません: " + id));

        boolean hasUserAlreadyReviewed = false;
        Users user = null;

        if (usersDetailsImpl != null) {
            user = usersDetailsImpl.getUser();
            hasUserAlreadyReviewed = reviewService.hasUserAlreadyReviewed(store, user);
        }

        List<Review> newReviews = reviewRepository.findTop6ByStoresOrderByCreatedDateDesc(store);
        long totalReviewCount = reviewRepository.countByStores(store);

        model.addAttribute("stores", store);
        model.addAttribute("reserveInputForm", new ReserveInputForm());
        model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);
        model.addAttribute("newReviews", newReviews);
        model.addAttribute("totalReviewCount", totalReviewCount);

        return "houses/show";
    }
}