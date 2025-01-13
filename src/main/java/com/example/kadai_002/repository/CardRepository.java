package com.example.kadai_002.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.kadai_002.entity.Card;
import com.example.kadai_002.entity.Users;

public interface CardRepository extends JpaRepository<Card, Integer> {
	public Card findByUsers(Users users);
	public Card findBySubscriptionId(String subscriptionId);
	
	@Transactional
	public Integer deleteBySubscriptionId(String subscriptionId);

}