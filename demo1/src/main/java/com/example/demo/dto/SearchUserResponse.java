package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserResponse {

    private String username;

    private Integer btcPrice;

    private Integer btcAmount;

    private Integer afterBalance;


}
