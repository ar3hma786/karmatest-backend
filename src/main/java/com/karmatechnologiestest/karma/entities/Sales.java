package com.karmatechnologiestest.karma.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_id", nullable = false, unique = true)
    private String referenceId; 

    @Column(name = "datetime", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "grand_total", nullable = false)
    private Double grandTotal;

    @Column(name = "paid", nullable = false)
    private Double paid;

    @Column(name = "due", nullable = false)
    private Double due;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "biller", nullable = false)
    private String biller;
    
    @Column(name = "action", nullable = false)
    private String action;
    
    @OneToOne
    private User user;

    @PrePersist
    public void generateReferenceId() {
        this.referenceId = "SL0-" + (100 + this.id);
    }
}
