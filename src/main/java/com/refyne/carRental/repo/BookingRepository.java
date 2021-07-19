package com.refyne.carRental.repo;

import com.refyne.carRental.dao.Booking;
import com.refyne.carRental.dto.BookingsByUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<BookingsByUser> findByUserId(String userId);
    List<BookingsByUser> findByCarIdAndStartTimeGreaterThan(String carId, Date startTime);
}
