package com.example.kadai_002.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kadai_002.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {	
	public Users findByMailAddress(String mailAddress);
}