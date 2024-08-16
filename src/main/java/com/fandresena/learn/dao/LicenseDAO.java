package com.fandresena.learn.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fandresena.learn.entity.License;
import com.fandresena.learn.model.LicenseModel;
import com.fandresena.learn.repository.LicenseRepo;

public class LicenseDAO {

    @Autowired
    private LicenseRepo licenseRepo;

    public void createLicense(LicenseModel licenseModel){
        License license = new License();
        license.setStartDate(licenseModel.getStartDate());
        license.setEndDate(licenseModel.getEndDate());
        license.setStatus(licenseModel.getStatus());
        licenseRepo.save(license);

        licenseRepo.save(license);
    }

    public LicenseModel getLicenceById(int id){
         License license = licenseRepo.findById(id).orElse(null);
         if(license != null){
             LicenseModel licenseModel = new LicenseModel();
             licenseModel.setId(license.getId());
             licenseModel.setStartDate(license.getStartDate());
             licenseModel.setEndDate(license.getEndDate());
             licenseModel.setStatus(license.getStatus());
             return licenseModel;
         }
         else return null;
    }

    public List<LicenseModel> getAllLicenses(){
        List<License> licenseList = licenseRepo.findAll();
        List<LicenseModel> licenseModels = new ArrayList<>();
        for(int i=0; i<licenseList.size(); i++){
            LicenseModel licenseModel = new LicenseModel();
            licenseModel.setId(licenseList.get(i).getId());
            licenseModel.setStartDate(licenseList.get(i).getStartDate());
            licenseModel.setEndDate(licenseList.get(i).getEndDate());
            licenseModel.setStatus(licenseList.get(i).getStatus());
            licenseModels.add(licenseModel);
        }
        return licenseModels;
    }

    public License updateLicense(int id, License license){
        License currentLicense = licenseRepo.findById(id).orElse(null);
        if(currentLicense != null){
            currentLicense.setStartDate(license.getStartDate());
            currentLicense.setEndDate(license.getEndDate());
            currentLicense.setStatus(license.getStatus());
            return licenseRepo.save(currentLicense);
        }
        else return null;
    }

    public void deleteLicense(int id){
        licenseRepo.deleteById(id);
    }
}
