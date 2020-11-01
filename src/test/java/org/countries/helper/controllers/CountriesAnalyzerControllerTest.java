package org.countries.helper.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CountriesAnalyzerControllerTest extends ParentTest{

    @Test
    public void listCountriesWithBiggestPopulationDensity() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/rest/countries/biggest/population/{count}", 10)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).isNotNull();
        assertFalse(response.isEmpty());
        assertTrue(response.contains("Monaco"));
        assertTrue(response.contains("Malta"));
    }

    @Test
    public void listCountriesWithGivenCurrency() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/rest/countries/currency/{currency}", "EUR")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).isNotNull();
        assertFalse(response.isEmpty());
        assertTrue(response.contains("EUR"));
        assertTrue(response.contains("Belgium"));
    }

    @Test
    public void listCountriesByPattern() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/rest/countries/pattern/{pattern}", "S*n")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).isNotNull();
        assertFalse(response.isEmpty());
        assertTrue(response.contains("Spain"));
        assertTrue(response.contains("Sweden"));
    }
}