package org.countries.helper.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.countries.helper.entity.Country;
import org.countries.helper.entity.Currency;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
    private static final String BASE_URL = "https://restcountries.eu/rest/v2/all?fields=name;capital;currencies;population;area";
    private TypeReference<List<Country>> typeReference = new TypeReference<List<Country>>() {
    };

    @Override
    public List<Country> readCountries() throws IOException {
        List<Country> countries;
        String jsonString = "";
        try {
            jsonString = read();
            ObjectMapper mapper = new ObjectMapper();
            countries = mapper.readValue(jsonString, typeReference);
        } catch (Exception ex) {
            countries = load();
        }
        return countries;
    }

    private String read() {
        StringBuilder inputBuffer = new StringBuilder();
        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(String.valueOf(HttpMethod.GET));
            urlConnection.setConnectTimeout(30000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                log.error("Can't connect to " + BASE_URL);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                inputBuffer.append(inputLine);
            }
            in.close();

        } catch (IOException | RuntimeException e) {
            log.error(e.getMessage());
        }
        return inputBuffer.toString();
    }

    @Override
    public List<Country> load() {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/json/countries.json");
        List<Country> countries = new ArrayList<>();
        try {
            countries = mapper.readValue(inputStream, typeReference);
            log.info("Countries Saved!");
        } catch (IOException e) {
            log.error("Unable to save countries: " + e.getMessage());
        }
        return countries;
    }

    ///Top N countries with the biggest population density (people / square km)
    @Override
    public LinkedList<String> getTopBiggestPopulationDensityCountries(int count) {
        if (count == 0) {
            return new LinkedList<>();
        }
        List<Country> all = load();
        Map<String, Double> densityMap = new HashMap<>();
        ValueComparator bvc = new ValueComparator(densityMap);
        all.forEach(
                country -> {
                    if (country.getArea() != 0) {
                        Double density = Double.valueOf(country.getPopulation()) / Double.valueOf(country.getArea());
                        densityMap.put(country.getName(), density);
                    }
                });
        TreeMap<String, Double> sortedMap = new TreeMap<>(bvc);
        sortedMap.putAll(densityMap);
        return sortedMap.keySet().stream().limit(count).collect(Collectors.toCollection(LinkedList::new));
    }

    ///All the countries that have the given currency
    public List<Country> getListCountriesWithGivenCurrency(String currency) {
        if (Strings.isNullOrEmpty(currency)) {
            return Collections.emptyList();
        }
        List<Country> all = load();
        List<List<Currency>> currenciesFromCountries = all.stream()
                .map(country -> country.getCurrencies())
                .distinct()
                .collect(Collectors.toList());
        Set<Currency> currencies = new HashSet<>();
        currenciesFromCountries.forEach(list -> currencies.addAll(list));
        Set<Currency> neededCurrency = currencies.stream()
                .filter(curr -> curr.getCode() != null && curr.getCode().equals(currency)).collect(Collectors.toSet());
        if (neededCurrency.isEmpty()) {
            return Collections.emptyList();
        }
        return all.stream().filter(country -> country.getCurrencies().stream().anyMatch(neededCurrency::contains)).collect(Collectors.toList());
    }

    ///All the countries that correspond to a given pattern. Pattern syntax: * represents any
    ///number of any characters, wildcard is case-insensitive. Example "s*N" would match Spain and
    @Override
    public List<Country> getListCountriesByPattern(String pattern) {
        Pattern patternCountries = Pattern.compile(pattern.replaceAll("\\*", ".+"), Pattern.CASE_INSENSITIVE);

        if (Strings.isNullOrEmpty(pattern)) {
            return Collections.emptyList();
        }
        return load().stream().filter(country -> patternCountries.matcher(country.getName()).matches()).collect(Collectors.toList());
    }

    class ValueComparator implements Comparator<String> {
        Map<String, Double> base;

        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
