package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
