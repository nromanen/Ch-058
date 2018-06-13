package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Integer> {
}
