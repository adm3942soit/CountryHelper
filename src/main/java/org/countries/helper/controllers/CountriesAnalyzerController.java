package org.countries.helper.controllers;

import org.countries.helper.entity.Country;
import org.countries.helper.entity.Currency;
import org.countries.helper.services.CountryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.inject.Inject;

@RestController
@RequestMapping("/rest/countries")
public class CountriesAnalyzerController {
    @Inject
    CountryService countryService;

    ///Top N countries with the biggest population density (people / square km)
    @GetMapping(value = "/biggest/population/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> listCountriesWithBiggestPopulationDensity(@PathVariable int count) {
        return countryService.getTopBiggestPopulationDensityCountries(count);
    }
    ///All the countries that have the given currency
    @GetMapping(value = "/currency/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> listCountriesWithGivenCurrency(@PathVariable String currency) {
        return countryService.getListCountriesWithGivenCurrency(currency);
    }
    ///All the countries that correspond to a given pattern. Pattern syntax: * represents any
    ///number of any characters, wildcard is case-insensitive. Example "s*N" would match Spain and
    @GetMapping(value = "/pattern/{pattern}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> listCountriesByPattern(@PathVariable String pattern) {
        return countryService.getListCountriesByPattern(pattern);
    }
}
