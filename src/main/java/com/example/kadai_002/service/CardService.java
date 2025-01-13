package com.example.kadai_002.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kadai_002.entity.Card;
import com.example.kadai_002.entity.Users;
import com.example.kadai_002.repository.CardRepository;
import com.example.kadai_002.repository.UsersRepository;

@Service
public class CardService {
	private final CardRepository cardRepository;
	private final UsersRepository usersRepository;
	
	public CardService(CardRepository cardRepository, UsersRepository usersRepository) {
		this.cardRepository = cardRepository;
		this.usersRepository = usersRepository;
	}
	
	//カード情報登録機能
	@Transactional
	public Card create(String email, String customerId, String subscriptionId) {
		Card card = new Card();
		
		Users users = usersRepository.findByMailAddress(email);
		
		card.setUsers(users);
		card.setCustomerId(customerId);
		card.setSubscriptionId(subscriptionId);
		
		return cardRepository.save(card);
	}
	
	//カード情報編集機能
	@Transactional
	public Card update(String email, String customerId) {
		Users users = usersRepository.findByMailAddress(email);
		Card card = cardRepository.findByUsers(users);
		
		card.setUsers(users);
		card.setCustomerId(customerId);
		
		return cardRepository.save(card);
	}



}