package com.product.star;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {

    private static final List<City> CITIES = List.of(
            new City("Moscow", "Russia"),
            new City("Rostov", "Russia"),
            new City("Vancouver", "Canada"),
            new City("Paris", "France")
    );

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(CityRepository cityRepository, PersonRepository personRepository) {
        return args -> {
            personRepository.deleteAllInBatch();
            cityRepository.deleteAllInBatch();

            var cities = cityRepository.saveAll(CITIES);
            System.out.println("PERSISTED CITIES: " + cities);

            var person = person(cities.get(0));
            Person persistedPerson = personRepository.save(person);

            List<Person> adults = personRepository.findAllByAgeBetweenAndGender(20, 25, Gender.MALE);
            System.out.println(adults);
        };
    }

    private static Person person(City city) {
        var person = new Person();
        person.setAge(25);
        person.setName("Ivan");
        person.setSurname("Ivanov");
        person.setCity(city);
        person.setGender(Gender.MALE);
        return person;
    }
}