package com.brandeis.grant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brandeis.grant.model.Award;
import com.brandeis.grant.model.Funder;
import com.brandeis.grant.repository.AwardRepository;

@Service
public class AwardService {

    @Autowired
    private AwardRepository awardRepository;
    
    public List<Award> getAwardsWithAmountLessThan(int maxAmount) {
        return awardRepository.findByAmountLessThan(maxAmount);
    }

    public List<Award> getAwardsWithAmountMoreThan(int minAmount) {
        return awardRepository.findByAmountGreaterThan(minAmount);
    }

    public List<Award> getAwardsByStartYear(int startYear) {
        return awardRepository.findByStartYear(startYear);
    }

    public List<Award> getAwardsByYearAndGreater(int startYear, int minAmount) {
        return awardRepository.findByStartYearAndAmountGreaterThan(startYear, minAmount);
    }


     public List<Award> getAwardsByYearAndLess(int startYear, int minAmount) {
        return awardRepository.findByStartYearAndAmountLessThan(startYear, minAmount);
    }

    public Award addAward(Award award) {
        return awardRepository.save(award);
    }

    public Funder getFunderByAwardId(String awardId) {
        Award award = awardRepository.findById(awardId).orElseThrow();
        return award.getFunderId();
    }

    // Count awards by year
    public int getTotalAmount(int year){
        if (year == -1) {
            return awardRepository.sumAwardAmounts() != null ? awardRepository.sumAwardAmounts() : 0;
        }
        return awardRepository.sumAwardAmountsByStartYear(year) != null ? 
               awardRepository.sumAwardAmountsByStartYear(year) : 0;

    }

    // Find top funders by total award amount
    public List<Object[]> getTopFundersByTotalAwardAmount(int year, int limit) {
        if (year == -1) {
            return awardRepository.findTopFundersByTotalAwardAmount(Pageable.ofSize(limit));
        }
        return awardRepository.findTopFundersByTotalAwardAmountAndYear(year, Pageable.ofSize(limit));
    }

    // Count unique funders
    public int countUniqueFunders() {
        Integer count = awardRepository.countUniqueFunder();
        return count != null ? count : 0;
    }

    // Get the trend of award amounts over the years
    public List<Object[]> getAwardAmountTrend() {
        return awardRepository.findAwardAmountTrend();
    }

    // Count awards by funder
    public List<Object[]> getTopFundersWithMostAwards(int limit, int year){
        if (year == -1) {
            return awardRepository.findTopFundersWithMostAwards(Pageable.ofSize(limit));
        }
        return awardRepository.findTopFundersWithMostAwardsAndByYear(year, Pageable.ofSize(limit));
    }


    // for each faculty, get the top funders with most awards
    public List<Object[]> getTopFundersWithMostAwardsPerFaculty(int limit, String facultyId, int year) {
        if (year == -1) {
            return awardRepository.findTopFunderByFacultyId(facultyId, Pageable.ofSize(limit));
        }
        return awardRepository.findTopFunderByFacultyIdAndByYear(facultyId, year, Pageable.ofSize(limit));

    }

    //get total awards by faculty
    public long getTotalAwardsByFaculty(int year, String facultyId) {
        if (year == -1) {
            return awardRepository.getTotalAwardAmountByFacultyId(facultyId);
        }
        return awardRepository.getTotalAwardAmountByFacultyIdAndYear(facultyId, year);
    }


}
