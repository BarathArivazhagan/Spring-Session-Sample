package com.barath.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barath.app.entities.Session;

public interface SessionEntityRepository extends JpaRepository<Session, String> {

}
