package md.tekwill.dao;

import md.tekwill.entity.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryJpa extends CrudRepository<Product, Integer> {
    List<Product> findAllByName(String name);
    List<Product> findAll();
}
