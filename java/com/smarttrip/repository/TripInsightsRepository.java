package com.smarttrip.repository;

import com.smarttrip.entity.TripInsights;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TripInsightsRepository extends JpaRepository<TripInsights, Long> {
    Optional<TripInsights> findByTripId(Long tripId);
}
