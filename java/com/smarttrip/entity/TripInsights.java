package com.smarttrip.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trip_insights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TripInsights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weather;
    private String currencyRate;

    private String recommendations; // ✅ Added
    private String safetyTips;      // ✅ Added
    private String budgetTips;      // ✅ Added

    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
