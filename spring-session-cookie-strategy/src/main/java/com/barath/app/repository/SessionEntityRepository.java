package com.barath.app.repository;

import com.barath.app.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionEntityRepository extends JpaRepository<Session, String> {

}
