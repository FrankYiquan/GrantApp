package com.brandeis.grant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandeis.grant.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, String> {}