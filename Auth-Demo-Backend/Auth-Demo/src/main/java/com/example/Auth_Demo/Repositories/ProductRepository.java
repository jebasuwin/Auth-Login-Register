package com.example.Auth_Demo.Repositories;

import com.example.Auth_Demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

