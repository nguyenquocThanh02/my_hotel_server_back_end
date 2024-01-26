package com.myhotel.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPrice;
    private LocalDateTime timePrintBill;
    private boolean paid = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_id")
    private Booked booked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public void setPaid(boolean paid){
        this.paid = paid;
    }
    public boolean getPaid() {
        return this.paid;
    }
}
