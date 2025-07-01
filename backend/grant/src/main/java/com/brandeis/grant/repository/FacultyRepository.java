package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandeis.grant.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, String> {

    //Find faculty with most award amount
    @Query("""
    SELECT f
    FROM Faculty f
    JOIN f.awards a
    GROUP BY f
    ORDER BY SUM(a.amount) DESC
""")
List<Faculty> findTopFacultyByTotalAwardAmount(Pageable pageable);

    

}





