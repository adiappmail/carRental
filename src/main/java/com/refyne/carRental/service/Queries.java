package com.refyne.carRental.service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Queries {


    public Query timeOverLapQuery(Date start, Date end) {

        Query q = new org.springframework.data.mongodb.core.query.Query(
                        new Criteria().orOperator(
                                new Criteria().andOperator(
                                        Criteria.where("startTime").gte(start),
                                        Criteria.where("startTime").lte(end)
                                ),
                                new Criteria().andOperator(
                                        Criteria.where("endTime").gte(start),
                                        Criteria.where("endTime").lte(end)
                                ),
                                new Criteria().andOperator(
                                        Criteria.where("startTime").lte(start),
                                        Criteria.where("endTime").gte(end)
                                )
                        )
        );
        return q;
    }

}
