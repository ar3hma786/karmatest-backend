package com.karmatechnologiestest.karma.entities;

import java.time.LocalDateTime;

import com.karmatechnologiestest.karma.enums.ROLE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private ROLE role;

    @Column(name = "DATETIME", nullable = false)
    private LocalDateTime datetime;

    @OneToOne
    @JoinColumn(name = "SALE_ID", referencedColumnName = "reference_id")
    private Sales sale;

    public Sales getSale() {
        if (role == ROLE.USER) {
            return sale;
        } else {
            return null;
        }
    }

    public void setSale(Sales sale) {
        if (role == ROLE.USER) {
            this.sale = sale;
        } else {
            throw new UnsupportedOperationException("Only users with ROLE.USER can have a sale.");
        }
    }
}
