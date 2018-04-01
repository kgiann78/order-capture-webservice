package com.constantine.ordercapture.service;

import com.constantine.ordercapture.exception.ResourceNotFoundException;
import com.constantine.ordercapture.model.Customer;
import com.constantine.ordercapture.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/customers")
public class CustomerController {

    private final String NO_CUSTOMER =  "No customer to add. Please check and try again!";
    private final String MISSING_CUSTOMER_ID = "Customer Id is missing. Please check and try again!";

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Iterable<Customer> findAll() {

        return customerRepository.findAll();
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Customer> find(@RequestParam("id") String id) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_CUSTOMER_ID);

        return customerRepository.findById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    Customer create(@RequestBody Customer customer) {

        if (customer == null) throw new ResourceNotFoundException(NO_CUSTOMER);
        return customerRepository.save(customer);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    void delete(@RequestParam("id") String id) {

        if (id == null || id.isEmpty()) throw new ResourceNotFoundException(MISSING_CUSTOMER_ID);

        customerRepository.deleteById(id);
    }
}
