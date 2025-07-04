package com.brandeis.grant.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandeis.grant.model.Award;

public interface AwardRepository extends JpaRepository<Award, String> {
    List<Award> findByAmountLessThan(int amount);
    List<Award> findByAmountGreaterThan(int amount);
    List<Award> findByStartYear(int startYear);
    List<Award> findByStartYearAndAmountGreaterThan(int startYear, int amount);
    List<Award> findByStartYearAndAmountLessThan(int startYear, int amount);

    // Count ALL awards
    @Query("SELECT SUM(a.amount) FROM Award a")
    Integer sumAwardAmounts();

    // Count awards by start year
    @Query("SELECT SUM(a.amount) FROM Award a WHERE a.startYear = :year")
    Integer sumAwardAmountsByStartYear(@Param("year") int year);


    // Count awards by funder
    @Query("SELECT SUM(a.amount) FROM Award a WHERE a.funderId.funderId = :funderId")
    Integer sumAwardAmountsByFunder(@Param("funderId") String funderId);

    //get top funders by total award amount
    @Query("""
        SELECT a.funderId, SUM(a.amount)
        FROM Award a
        WHERE a.funderId IS NOT NULL
        GROUP BY a.funderId
        ORDER BY SUM(a.amount) DESC
    """)
    List<Object[]> findTopFundersByTotalAwardAmount(Pageable pageable);

      //get top funders by total award amount and by year
    @Query("""
        SELECT a.funderId, SUM(a.amount)
        FROM Award a
        WHERE a.funderId IS NOT NULL AND a.startYear = :startYear
        GROUP BY a.funderId
        ORDER BY SUM(a.amount) DESC
    """)
    List<Object[]> findTopFundersByTotalAwardAmountAndYear(@Param("startYear") int startYear, Pageable pageable);

    // Count unique funders
    @Query("SELECT COUNT(DISTINCT a.funderId) FROM Award a")
    Integer countUniqueFunder();

    @Query("SELECT a.startYear, SUM(a.amount) FROM Award a GROUP BY a.startYear ORDER BY a.startYear")
    List<Object []> findAwardAmountTrend();

    // Find top funders with most awards
    @Query("""
        SELECT a.funderId, COUNT(a)
        FROM Award a
        WHERE a.funderId IS NOT NULL
        GROUP BY a.funderId
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findTopFundersWithMostAwards(Pageable pageable);

    // Find top funders with most awards and by year
    @Query("""
        SELECT a.funderId, COUNT(a)
        FROM Award a
        WHERE a.funderId IS NOT NULL AND a.startYear = :startYear
        GROUP BY a.funderId
        ORDER BY COUNT(a) DESC
    """)
    List<Object[]> findTopFundersWithMostAwardsAndByYear(@Param("startYear") int startYear, Pageable pageable);


    @Query("""
    SELECT aw.funderId, SUM(aw.amount)
    FROM Award aw
    JOIN aw.faculties f
    WHERE f.facultyId = :facultyId AND aw.funderId IS NOT NULL
    GROUP BY aw.funderId
    ORDER BY SUM(aw.amount) DESC
    """)
    List<Object[]> findTopFunderByFacultyId(@Param("facultyId") String facultyId, Pageable pageable);

    @Query("""
    SELECT aw.funderId, SUM(aw.amount)
    FROM Award aw
    JOIN aw.faculties f
    WHERE f.facultyId = :facultyId AND aw.funderId IS NOT NULL AND aw.startYear = :year
    GROUP BY aw.funderId
    ORDER BY SUM(aw.amount) DESC
    """)
    List<Object[]> findTopFunderByFacultyIdAndByYear(@Param("facultyId") String facultyId, @Param("year") int year, Pageable pageable);


    @Query("""
    SELECT SUM(aw.amount)
    FROM Award aw
    JOIN aw.faculties f
    WHERE f.facultyId = :facultyId
    """)
    long getTotalAwardAmountByFacultyId(@Param("facultyId")String facultyId);

    @Query("""
    SELECT SUM(aw.amount)
    FROM Award aw
    JOIN aw.faculties f
    WHERE f.facultyId = :facultyId AND aw.startYear = :year
    """)
    long getTotalAwardAmountByFacultyIdAndYear(@Param("facultyId")String facultyId, @Param("year") int year);

   @Query("SELECT COUNT(a) FROM Award a WHERE a.funderId.funderId = :funderId")
    int countFunderAppearancesByFunderId(@Param("funderId")String funderId);

    @Query("SELECT COUNT(a) FROM Award a WHERE a.funderId.funderId = :funderId AND a.startYear = :year")
    int countFunderAppearancesByFunderIdAndByYear(@Param("funderId")String funderId, @Param("year")int Year);

    @Query("SELECT SUM(a.amount) FROM Award a WHERE a.funderId.funderId = :funderId")
    long getFunderTotalAmount(@Param("funderId")String funderId);

    @Query("SELECT SUM(a.amount) FROM Award a WHERE a.funderId.funderId = :funderId AND a.startYear = :year")
    long getFunderTotalAmountByYear(@Param("funderId")String funderId, @Param("year")int year);

    @Query("""
        SELECT f, SUM(a.amount) AS totalAmount
        FROM Award a
        JOIN a.faculties f
        WHERE a.funderId.funderId = :funderId
        GROUP BY f
        ORDER BY totalAmount DESC
    """)
    List<Object[]> findTopFacultyWithTotalAwardAmountInFunder(
        @Param("funderId") String funderId,
        Pageable pageable
    );

     @Query("""
        SELECT f, SUM(a.amount) AS totalAmount
        FROM Award a
        JOIN a.faculties f
        WHERE a.funderId.funderId = :funderId AND a.startYear = :startYear
        GROUP BY f
        ORDER BY totalAmount DESC
    """)
     List<Object[]> findTopFacultyWithTotalAwardAmountInFunderByYear(
        @Param("funderId") String funderId,
        @Param("startYear") int startYear,
        Pageable pageable
    );

    @Query("""
        SELECT ar, SUM(a.amount) AS totalAmount
        FROM Award a
        JOIN a.articles ar
        WHERE a.funderId.funderId = :funderId
        GROUP BY ar
        ORDER BY totalAmount DESC
    """)
    List<Object[]> findTopArticleWithMostAwardFromFunder(@Param("funderId") String funderId, Pageable pageable);


     @Query("""
        SELECT ar, SUM(a.amount) AS totalAmount
        FROM Award a
        JOIN a.articles ar
        WHERE a.funderId.funderId = :funderId AND a.startYear = :startYear
        GROUP BY ar
        ORDER BY totalAmount DESC
    """)
    List<Object[]> findTopArticleWithMostAwardFromFunderByYear(@Param("funderId") String funderId, @Param("startYear") int startYear, Pageable pageable);

}
