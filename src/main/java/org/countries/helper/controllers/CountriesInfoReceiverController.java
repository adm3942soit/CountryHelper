package org.countries.helper.controllers;

import org.countries.helper.entity.Country;
import org.countries.helper.services.CountryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;

@RestController
@RequestMapping("/rest/countries")
public class CountriesInfoReceiverController {
    @Inject
    CountryService countryService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> listCountries() {
        List<Country> countries = null;
        try {
            countries = readCountries();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return countries;
    }


    public List<Country> readCountries() throws IOException {
        return countryService.readCountries();
    }
}
