package org.countries.helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class SpringBootCountriesRestAPIExample  {

    public static void main(final String... args) {
        SpringApplication.run(SpringBootCountriesRestAPIExample.class, args);
    }
/*
    @Bean
    CommandLineRunner runner(CountryService countryService) {
        return args -> {
            // read json and write to db
              countryService.load();
        };
    }

 */
}
