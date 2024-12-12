package com.example.kadai_002.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kadai_002.entity.Stores;

public interface StoresRepository extends JpaRepository<Stores, Integer> {
    public Page<Stores> findByStoreNameLike(String keyword, Pageable pageable);
	public Page<Stores> findByStoreNameLikeOrStoreAddressLikeOrderByCreatedDateDesc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<Stores> findByStoreNameLikeOrStoreAddressLikeOrderByMinBudgetAsc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<Stores> findByStoreAddressLikeOrderByCreatedDateDesc(String area, Pageable pageable);
    public Page<Stores> findByStoreAddressLikeOrderByMinBudgetAsc(String area, Pageable pageable);
    public Page<Stores> findByMinBudgetLessThanEqualOrderByCreatedDateDesc(Integer price, Pageable pageable);
    public Page<Stores> findByMinBudgetLessThanEqualOrderByMinBudgetAsc(Integer price, Pageable pageable); 
    public Page<Stores> findAllByOrderByCreatedDateDesc(Pageable pageable);
    public Page<Stores> findAllByOrderByMinBudgetAsc(Pageable pageable);    

    public List<Stores> findTop10ByOrderByCreatedDateDesc();
}