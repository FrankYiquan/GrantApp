package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandeis.grant.model.Award;

public interface AwardRepository extends JpaRepository<Award, String> {
    List<Award> findByAmountLessThan(int amount);
    List<Award> findByAmountMoreThan(int amount);
    List<Award> findByStartYear(int startYear);
    List<Award> findByStartYearAndAmountGreaterThan(int startYear, int amount);
    List<Award> findByStartYearAndAmountLessThan(int startYear, int amount);

}