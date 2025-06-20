package com.brandeis.grant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


    
}
