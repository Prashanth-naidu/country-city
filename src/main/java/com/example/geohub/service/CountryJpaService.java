package com.example.geohub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

import com.example.geohub.model.Country;
import com.example.geohub.repository.CountryJpaRepository;
import com.example.geohub.repository.CountryRepository;

@Service
public class CountryJpaService implements CountryRepository {

    @Autowired
    public CountryJpaRepository db;

    @Override
    public ArrayList<Country> getCountries() {

        return (ArrayList<Country>) db.findAll();
    }

    @Override
    public Country getCountryById(int countryId) {
        try {
            Country country = db.findById(countryId).get();
            return country;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Country addCountry(Country country) {
        try {
            db.save(country);
            return country;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Country updateCountry(int countryId, Country country) {
        try {
            Country newOne = db.findById(countryId).get();
            if (country.getCountryName() != null) {
                newOne.setCountryName(country.getCountryName());
            }
            if (country.getCurrency() != null) {
                newOne.setCurrency(country.getCurrency());
            }
            if (country.getPopulation() != 0) {
                newOne.setPopulation(country.getPopulation());
            }
            if (country.getLatitude() != null) {
                newOne.setLatitude(country.getLatitude());
            }
            if (country.getLongitude() != null) {
                newOne.setLongitude(country.getLongitude());
            }
            db.save(newOne);
            return newOne;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCountry(int countryId) {
        try {
            db.deleteById(countryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}