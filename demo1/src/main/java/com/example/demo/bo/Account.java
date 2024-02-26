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
@Table(name ="Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "balance", columnDefinition = "INT DEFAULT 0")
    private Integer balance;

    @Column(name = "btc_amount", columnDefinition = "INT DEFAULT 0")
    private Integer btc_amount;

    @Column(name = "user_id")
    private Integer userId;
}
