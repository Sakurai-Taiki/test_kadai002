package com.example.kadai_002.entity;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reserve")
@Data
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 外部キーとしてマッピング
    private Users users;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false) // 外部キーとしてマッピング
    private Stores stores;

    @Column(name = "reserve_date", nullable = false)
    private LocalDate reserveDate;

    @Column(name = "reserve_time", nullable = false)
    private LocalTime reserveTime;

    @Column(name = "reserve_count", nullable = false)
    private Integer reserveCount;

    @Column(name = "create_date", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "update_date", insertable = false, updatable = false)
    private Timestamp updatedDate;
}