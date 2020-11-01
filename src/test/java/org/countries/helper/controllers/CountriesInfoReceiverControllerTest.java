package org.countries.helper.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CountriesInfoReceiverControllerTest extends ParentTest {

    @Test
    public void testListCountries() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/rest/countries/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).isNotNull();
        assertFalse(response.isEmpty());
        assertTrue(response.contains("name"));
        assertTrue(response.contains("population"));
        assertTrue(response.contains("area"));
        assertTrue(response.contains("capital"));
        assertTrue(response.contains("currencies"));
    }
}