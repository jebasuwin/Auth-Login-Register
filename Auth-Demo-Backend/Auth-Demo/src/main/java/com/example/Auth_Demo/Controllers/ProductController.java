package com.example.Auth_Demo.Controllers;

import com.example.Auth_Demo.Entity.Product;
import com.example.Auth_Demo.Service.ProductService;
import com.example.Auth_Demo.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            return ResponseEntity.ok(productService.getAllProducts());
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            Product product = productService.getProductById(id);
            return product != null ? ResponseEntity.ok(product) : ResponseEntity.status(404).body("Product not found");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            return ResponseEntity.ok(productService.addProduct(product));
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct,
                                           @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            Product product = productService.updateProduct(id, updatedProduct);
            return product != null ? ResponseEntity.ok(product) : ResponseEntity.status(404).body("Product not found");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (validateToken(token)) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", exposedHeaders = "Authorization")
    private boolean validateToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);  // Remove "Bearer " from the token
        }
        return jwtUtil.isValidToken(token);
    }
}
