package org.countries.helper.services;

import org.countries.helper.entity.Country;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface CountryService {
    List<Country> readCountries() throws IOException;
    List<Country> load();
    LinkedList<String> getTopBiggestPopulationDensityCountries(int count);
    List<Country> getListCountriesWithGivenCurrency(String currency);
    List<Country> getListCountriesByPattern(String pattern);
}
