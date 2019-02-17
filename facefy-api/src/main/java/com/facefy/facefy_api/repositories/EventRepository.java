package com.facefy.facefy_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facefy.facefy_api.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

}
