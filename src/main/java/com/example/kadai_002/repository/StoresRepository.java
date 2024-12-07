package com.example.kadai_002.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kadai_002.entity.Stores;

public interface StoresRepository extends JpaRepository<Stores, Integer>{
 	public Page<Stores> findByStoreNameLike(String keyword, Pageable pageable);
}
