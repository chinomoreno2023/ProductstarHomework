package com.product.star.homework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Transactional
    @Modifying
    Contact save(Contact contact);

    @Transactional(readOnly = true)
    Optional<Contact> findById(long contactId);

    @Transactional
    @Modifying
    void deleteById(long contactId);

    @Transactional(readOnly = true)
    List<Contact> findAll();

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.phone = :phone WHERE c.id = :contactId")
    void updatePhone(@Param("contactId") long contactId, @Param("phone") String phoneNumber);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.email = :email WHERE c.id = :contactId")
    void updateEmail(@Param("contactId") long contactId, @Param("email") String email);
}
