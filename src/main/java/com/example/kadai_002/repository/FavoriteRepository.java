package com.example.kadai_002.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.kadai_002.entity.Favorite;
import com.example.kadai_002.entity.Stores;
import com.example.kadai_002.entity.Users;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    // 指定された店舗に紐づくお気に入りを取得
    List<Favorite> findByStores(Stores stores);

    // 指定されたユーザーに紐づくお気に入りを取得
    List<Favorite> findByUsers(Users users);

    // 指定されたユーザーに紐づくお気に入りをページング付きで取得
    Page<Favorite> findByUsers(Users users, Pageable pageable);

    // 特定の店舗とユーザーの組み合わせでお気に入りを削除
    @Transactional
    void deleteByStoresIdAndUsersId(Integer storeId, Integer userId);

    // 特定の店舗とユーザーの組み合わせでお気に入りを取得
    Favorite findByStoresIdAndUsersId(Integer storeId, Integer userId);
}