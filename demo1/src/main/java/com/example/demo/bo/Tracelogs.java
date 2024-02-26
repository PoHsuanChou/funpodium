package com.example.demo.bo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="tracelogs")
public class Tracelogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "btc_price", columnDefinition = "INT DEFAULT 0")
    private Integer btc_price;

    @Column(name = "btc_amount", columnDefinition = "INT DEFAULT 0")
    private Integer btc_amount;

    @Column(name = "after_balance", columnDefinition = "INT DEFAULT 0")
    private Integer after_balance;

    @Column(name = "user_id")
    private Integer user_id;

}
