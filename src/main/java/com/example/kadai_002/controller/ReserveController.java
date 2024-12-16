package com.example.kadai_002.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.kadai_002.entity.Reserve;
import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.entity.Users;
import com.example.kadai_002.form.ReserveInputForm;
import com.example.kadai_002.form.ReserveRegisterForm;
import com.example.kadai_002.repository.ReserveRepository;
import com.example.kadai_002.repository.StoresRepository;
import com.example.kadai_002.security.UsersDetailsImpl;
import com.example.kadai_002.service.ReserveService;

@Controller
public class ReserveController {

	private final ReserveRepository reserveRepository;
	private final StoresRepository storesRepository;
	private final ReserveService reserveService;

	public ReserveController(ReserveRepository reserveRepository, StoresRepository storesRepository,
			ReserveService reserveService) {
		this.reserveRepository = reserveRepository;
		this.storesRepository = storesRepository;
		this.reserveService = reserveService;
	}

	@GetMapping("/reserve")
	public String index(
			@AuthenticationPrincipal UsersDetailsImpl usersDetailsImpl,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Users users = usersDetailsImpl.getUser();

		Page<Reserve> reservePage = reserveRepository.findByUsers(users, pageable);

		model.addAttribute("reservePage", reservePage);

		return "reserve/index";
	}

	@GetMapping("/houses/{id}/reserve/input")
	public String input(@PathVariable(name = "id") Integer id,
	                    @ModelAttribute @Validated ReserveInputForm reserveInputForm,
	                    BindingResult bindingResult,
	                    RedirectAttributes redirectAttributes,
	                    Model model) {
	    // 店舗情報を取得
	    Stores stores = storesRepository.findById(id)
	        .orElseThrow(() -> new IllegalArgumentException("指定された店舗IDが存在しません: " + id));

	    Integer numberOfPeople = reserveInputForm.getNumberOfPeople();
	    Integer capacity = stores.getSeats();

	    // 定員チェック
	    if (numberOfPeople != null && !reserveService.isWithinCapacity(numberOfPeople, capacity)) {
	        FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "予約人数が定員を超えています。");
	        bindingResult.addError(fieldError);
	    }

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("stores", stores);
	        model.addAttribute("errorMessage", "予約内容に不備があります。");
	        return "houses/show";
	    }

	    // フォームデータをリダイレクト属性に保存
	    redirectAttributes.addFlashAttribute("reserveInputForm", reserveInputForm);

	    // 確認画面にリダイレクト
	    return "redirect:/houses/{id}/reserve/confirm";
	}
	
	@GetMapping("/houses/{id}/reserve/confirm")
	public String confirm(@PathVariable(name = "id") Integer id,
	                      @ModelAttribute ReserveInputForm reserveInputForm,
	                      Model model) {
	    // データをセット
	    ReserveRegisterForm reserveRegisterForm = new ReserveRegisterForm();
	    reserveRegisterForm.setHouseId(id);
	    reserveRegisterForm.setFromCheckinDate(reserveInputForm.getFromCheckinDate());
	    reserveRegisterForm.setFromCheckinTime(reserveInputForm.getFromCheckinTime());
	    reserveRegisterForm.setNumberOfPeople(reserveInputForm.getNumberOfPeople());

	    // モデルに追加
	    model.addAttribute("reserveRegisterForm", reserveRegisterForm);

	    return "reserve/confirm";
	}
}