package com.smarttrip.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;
    private Double budget;
    private LocalDate startDate;
    private LocalDate endDate;

    // ✅ Link each trip to a user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
