package com.refyne.carRental.repo;

import com.refyne.carRental.dao.Cars;
import com.refyne.carRental.dao.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends MongoRepository<Cars, String> {


}
