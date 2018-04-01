package com.constantine.ordercapture.repositories;


import com.constantine.ordercapture.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductRepository extends CrudRepository<Product, String> {

}
