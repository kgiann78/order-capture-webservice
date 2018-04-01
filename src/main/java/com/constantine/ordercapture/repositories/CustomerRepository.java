package com.constantine.ordercapture.repositories;

import com.constantine.ordercapture.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CustomerRepository extends CrudRepository<Customer, String> {

    @Query("select c from Customer c where c.lastName = :lastName")
    List<Customer> findByLastName(@Param("lastName") String lastName);

}
