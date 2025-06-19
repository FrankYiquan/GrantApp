package com.brandeis.grant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandeis.grant.model.Funder;

public interface FunderRepository extends JpaRepository<Funder, String> {}