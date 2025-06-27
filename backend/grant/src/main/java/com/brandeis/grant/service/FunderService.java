package com.brandeis.grant.service;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brandeis.grant.model.Award;
import com.brandeis.grant.model.Funder;
import com.brandeis.grant.repository.FunderRepository;
import com.opencsv.CSVReader;


@Service
public class FunderService {

    @Autowired
    private FunderRepository funderRepository;
    
    public Funder addFunder(Funder funder) {
        return funderRepository.save(funder);
    }

    public Funder getFunderById(String funderId) {
        return funderRepository.findById(funderId)
            .orElseThrow(() -> new RuntimeException("Funder not found with id " + funderId));
    }

     public List<Award> getAwardsByFunderId(String funderId) {
        Funder funder = funderRepository.findById(funderId)
            .orElseThrow(() -> new RuntimeException("Funder not found with id " + funderId));
        return funder.getAwards();
    }

    public void importFundersFromCSV(MultipartFile file) throws Exception {
      try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
        String[] line;
        boolean isFirstLine = true;
        while ((line = reader.readNext()) != null){
            if (isFirstLine) {
                isFirstLine = false; // Skip the header line
                continue;
            }
            String funderID = line[0].trim(); // Assuming the first column is funderId
            String funderName = line[1].trim(); // Assuming the second column is funderName
            
            // Create a new Funder object
            Funder funder = new Funder();
            funder.setFunderId(funderID);
            funder.setFunderName(funderName);
            
            // Save the Funder object to the database
            addFunder(funder);
        }

      }catch(Exception e){
        e.printStackTrace();
      }

    }

}
