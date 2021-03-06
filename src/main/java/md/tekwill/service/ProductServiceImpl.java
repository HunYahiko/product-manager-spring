package md.tekwill.service;

import md.tekwill.dao.ProductRepository;
import md.tekwill.dao.ProductRepositoryJdbcTemplate;
import md.tekwill.dao.ProductRepositoryJpa;
import md.tekwill.entity.product.Drink;
import md.tekwill.entity.product.Food;
import md.tekwill.entity.product.FoodCategory;
import md.tekwill.entity.product.Product;
import md.tekwill.exceptions.ProductExistsException;
import md.tekwill.exceptions.ProductNotFoundException;
import md.tekwill.exceptions.ProductUpdateUnknownPropertyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositoryJpa productRepositoryJpa;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductRepositoryJpa productRepositoryJpa) {
        this.productRepository = productRepository;
        this.productRepositoryJpa = productRepositoryJpa;
    }

    @Override
    public void create(String name, double price, LocalDate bestBefore, double volume) {
        productRepository.save(new Drink(name, price, bestBefore, volume));
    }

    @Override
    public void create(String name, double price, LocalDate bestBefore, FoodCategory category) {
        if (productRepository.findByName(name) != null) {
            throw new ProductExistsException("Product with name " + name + " already exists!");
        }
        productRepositoryJpa.save(new Food(name, price, bestBefore, category));
    }

    @Override
    public List<Product> getAll() {
        return productRepositoryJpa.findAll();
    }

    @Override
    public List<Product> getAllNonExpired() {
        List<Product> products = productRepository.findAll();
        Predicate<Product> nonExpiredPredicate = p -> LocalDate.now().isBefore(p.getBestBefore()) || LocalDate.now().isEqual(p.getBestBefore());
        return filter(products, nonExpiredPredicate);
    }

    @Override
    public List<Product> getAllExpired() {
        List<Product> products = productRepository.findAll();
        Predicate<Product> expiredPredicate = p -> LocalDate.now().isAfter(p.getBestBefore());
        return filter(products, expiredPredicate);
    }

    private List<Product> filter(List<Product> products, Predicate<Product> predicate) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (predicate.test(product)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    @Override
    public Product getById(int id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found!");
        }
        return product;
    }

    @Override
    public void update(int id, double volume) throws ProductUpdateUnknownPropertyException {
        Product product = getById(id);
        if (product instanceof Drink) {
            productRepository.update(id, volume);
            return;
        }
        throw new ProductUpdateUnknownPropertyException("Product with id " + id + " is not drink!");
    }

    @Override
    public void update(int id, FoodCategory category) throws ProductUpdateUnknownPropertyException {
        Product product = getById(id);
        if (product instanceof Food) {
            productRepository.update(id, category);
            return;
        }
        throw new ProductUpdateUnknownPropertyException("Product with id " + id + " is not food!");
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> getByName(String name) {
        return productRepositoryJpa.findAllByName(name);
    }

    @Override
    public void save(Product product) {
        productRepositoryJpa.save(product);
    }
}
