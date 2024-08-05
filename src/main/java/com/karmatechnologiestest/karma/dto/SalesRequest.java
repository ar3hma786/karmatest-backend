package com.karmatechnologiestest.karma.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRequest {

	private String referenceId;
    private LocalDateTime localDateTime;
    private String status;
    private Double grandTotal;
    private Double paid;
    private Double due;
    private String paymentStatus;
    private String biller;
    private String action;
	
}
