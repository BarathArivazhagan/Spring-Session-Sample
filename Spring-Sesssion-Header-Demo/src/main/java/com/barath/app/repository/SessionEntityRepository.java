package com.barath.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barath.app.entities.SessionEntity;

public interface SessionEntityRepository extends JpaRepository<SessionEntity, String> {

}
