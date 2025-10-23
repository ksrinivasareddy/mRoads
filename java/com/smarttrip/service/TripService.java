package com.smarttrip.service;

import com.smarttrip.entity.Trip;
import com.smarttrip.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    // Create or Save Trip
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    // Get All Trips
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    // Delete Trip by ID
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}
