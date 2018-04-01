package com.constantine.ordercapture;

import com.constantine.ordercapture.model.Customer;
import com.constantine.ordercapture.repositories.CustomerRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class CustomerIntegrationTest extends BaseDatabaseTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void createCustomer() {
        List<Customer> customers = (List<Customer>) customerRepository.findAll();

        assertThat(customers).isNotNull();
        assertThat(customers).isNotEmpty();

        int initialSize = customers.size();
        Customer.Builder builder = new Customer.Builder();
        Customer customer = customerRepository.save(builder.withId("alekos-2")
                .withFirstName("alekos")
                .withLastName("sakelarios")
                .withAddress("tepeleni 2").build());

        assertThat(customer).isNotNull();

        customers = (List<Customer>) customerRepository.findAll();

        assertThat(customers.size()).isGreaterThan(initialSize);
    }

    @Test
    public void findCustomerById() {
        Optional<Customer> customer = customerRepository.findById("alekos-1");

        assertThat(customer).isNotNull();
    }

    @Test
    public void findCustomerByLastName() {
        List<Customer> customers = customerRepository.findByLastName("Alexiou");

        assertThat(customers).isNotNull();
        assertThat(customers).isNotEmpty();
    }

    @Test
    public void deleteCustomer() {
        Optional<Customer> customer = customerRepository.findById("alekos-1");

        assertThat(customer).isNotNull().isPresent();

        customer.ifPresent(customer1 -> customerRepository.delete(customer1));

        customer = customerRepository.findById("alekos-1");

        assertThat(customer).isEmpty();
    }
}
