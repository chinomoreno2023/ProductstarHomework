package com.product.star;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByNameAndSurname(String name, String surname);
    List<Person> findAllByName(String name);
    List<Person> findAllByAgeBetweenAndGender(int startAge, int endAge, Gender gender);


    @Modifying
    @Transactional
    @Query("update Person p set p.age = :age where p.id = :personId")
    Person updateAge(@Param("age") int age, @Param("personId") long personId);
}
