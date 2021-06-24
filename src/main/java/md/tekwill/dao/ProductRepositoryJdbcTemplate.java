package md.tekwill.dao;

import md.tekwill.entity.product.Product;

import java.util.List;

public interface ProductRepositoryJdbcTemplate {
    List<Product> findByName(String name);
}
