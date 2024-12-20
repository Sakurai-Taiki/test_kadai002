package com.example.kadai_002.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.kadai_002.entity.Review;
import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.entity.Users;
import com.example.kadai_002.form.ReviewEditForm;
import com.example.kadai_002.form.ReviewRegisterForm;
import com.example.kadai_002.repository.ReviewRepository;
import com.example.kadai_002.repository.StoresRepository;
import com.example.kadai_002.security.UsersDetailsImpl;
import com.example.kadai_002.service.ReviewService;

@Controller
@RequestMapping("/houses/{storesId}/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final StoresRepository storesRepository;
    private final ReviewService reviewService;

    public ReviewController(ReviewRepository reviewRepository, StoresRepository storesRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.storesRepository = storesRepository;
        this.reviewService = reviewService;
    }

    // レビュー一覧ページ
    @GetMapping
    public String index(@PathVariable(name = "storesId") Integer storesId,
                        @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
                        Model model) {
        Stores stores = storesRepository.getReferenceById(storesId);
        Page<Review> reviewPage = reviewRepository.findByStoresOrderByCreatedDateDesc(stores, pageable);

        model.addAttribute("stores", stores);
        model.addAttribute("reviewPage", reviewPage);

        return "reviews/index";
    }

    // レビュー登録ページ
    @GetMapping("/register")
    public String register(@PathVariable(name = "storesId") Integer storesId, Model model) {
        Stores stores = storesRepository.getReferenceById(storesId);

        model.addAttribute("stores", stores);
        model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());

        return "reviews/register";
    }

    // レビュー登録処理
    @PostMapping("/create")
    public String create(@PathVariable(name = "storesId") Integer storesId,
                         @AuthenticationPrincipal UsersDetailsImpl usersDetailsImpl,
                         @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (usersDetailsImpl == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login";
        }

        // 権限チェック
        if (!usersDetailsImpl.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_PRIME"))) {
            redirectAttributes.addFlashAttribute("errorMessage", "レビューを投稿できる権限がありません。");
            return "redirect:/houses/" + storesId;
        }

        // ストアとユーザーの情報を取得
        Stores stores = storesRepository.findById(storesId)
                .orElseThrow(() -> new IllegalArgumentException("店舗が見つかりません"));
        Users users = usersDetailsImpl.getUser();

        if (bindingResult.hasErrors()) {
            model.addAttribute("stores", stores);
            return "reviews/register";
        }

        try {
            reviewService.create(stores, users, reviewRegisterForm);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/houses/" + storesId + "/reviews/register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");
        return "redirect:/houses/" + storesId + "/reviews";
    }

    // レビュー編集ページ
    @GetMapping("/{reviewId}/edit")
    public String edit(@PathVariable(name = "storesId") Integer storesId,
                       @PathVariable(name = "reviewId") Integer reviewId,
                       Model model) {
        Stores stores = storesRepository.getReferenceById(storesId);
        Review review = reviewRepository.getReferenceById(reviewId);

        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getScore(), review.getContent());

        model.addAttribute("stores", stores);
        model.addAttribute("review", review);
        model.addAttribute("reviewEditForm", reviewEditForm);

        return "reviews/edit";
    }

    // レビュー更新処理
    @PostMapping("/{reviewId}/update")
    public String update(@PathVariable(name = "storesId") Integer storesId,
                         @PathVariable(name = "reviewId") Integer reviewId,
                         @AuthenticationPrincipal UsersDetailsImpl usersDetailsImpl,
                         @ModelAttribute @Validated ReviewEditForm reviewEditForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("レビューが見つかりません"));

        if (!review.getUsers().getId().equals(usersDetailsImpl.getUser().getId())) {
            throw new SecurityException("このレビューを編集する権限がありません。");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("stores", storesRepository.getReferenceById(storesId));
            model.addAttribute("review", review);
            return "reviews/edit";
        }

        reviewService.update(reviewEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");

        return "redirect:/stores/" + storesId + "/reviews";
    }

    // レビュー削除処理
    @PostMapping("/{reviewId}/delete")
    public String delete(@PathVariable(name = "storesId") Integer storesId,
                         @PathVariable(name = "reviewId") Integer reviewId,
                         RedirectAttributes redirectAttributes) {
        reviewRepository.deleteById(reviewId);

        redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");

        return "redirect:/stores/" + storesId + "/reviews";
    }
}