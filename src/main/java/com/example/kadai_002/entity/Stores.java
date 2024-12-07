package com.example.kadai_002.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stores")
@Data
public class Stores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "description")
    private String description;

    @Column(name = "min_budget")
    private Integer minBudget;

    @Column(name = "max_budget")
    private Integer maxBudget;

    @Column(name = "open_hour")
    private String openHour;

    @Column(name = "close_hour")
    private String closeHour;

    @Column(name = "store_post_code")
    private String storePostCode;

    @Column(name = "store_address")
    private String storeAddress;

    @Column(name = "store_phone_number")
    private String storePhoneNumber;

    @Column(name = "close_day")
    private String closeDay;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "create_date", insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(name = "update_date", insertable = false, updatable = false)
    private Timestamp updatedDate;
}