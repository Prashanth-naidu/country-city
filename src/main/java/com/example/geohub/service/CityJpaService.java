package com.example.geohub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

import com.example.geohub.model.City;
import com.example.geohub.model.Country;
import com.example.geohub.repository.CityJpaRepository;
import com.example.geohub.repository.CountryJpaRepository;
import com.example.geohub.repository.CityRepository;

@Service
public class CityJpaService implements CityRepository {

	@Autowired
	public CityJpaRepository db;
	@Autowired
	public CountryJpaRepository db1;

	@Override
	public ArrayList<City> getCities() {
		return (ArrayList<City>) db.findAll();
	}

	@Override
	public City getCityById(int cityId) {
		try {
			City city = db.findById(cityId).get();
			return city;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public City addCity(City city) {
		int countryId = city.getCountry().getCountryId();
		try {
			Country country = db1.findById(countryId).get();
			city.setCountry(country);
			db.save(city);

			return city;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public City updateCity(int cityId, City city) {
		try {
			City newOne = db.findById(cityId).get();
			if (city.getCityName() != null) {
				newOne.setCityName(city.getCityName());
			}
			if (city.getPopulation() != 0) {
				newOne.setPopulation(city.getPopulation());
			}
			if (city.getLatitude() != null) {
				newOne.setLatitude(city.getLatitude());
			}
			if (city.getLongitude() != null) {
				newOne.setLongitude(city.getLongitude());
			}
			if (city.getCountry() != null) {
				int countryId = city.getCountry().getCountryId();
				Country country = db1.findById(countryId).get();
				newOne.setCountry(country);
			}

			db.save(newOne);
			return newOne;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteCity(int cityId) {
		try {
			db.deleteById(cityId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public Country getCityCountry(int cityId) {
		try {
			City city = db.findById(cityId).get();
			Country country = city.getCountry();
			return country;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}