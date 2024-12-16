package com.example.kadai_002.service;

import org.springframework.stereotype.Service;

@Service
public class ReserveService {
    // 予約人数が定員以下かどうかをチェックする
    public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
        return numberOfPeople <= capacity;
    }
}
