package md.tekwill.dao;

import md.tekwill.entity.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryJdbcTemplateImpl implements ProductRepositoryJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findByName(String name) {
        final String query = "select * from product where name = ?";
        return jdbcTemplate.query(query, new ProductRowMapper(), name);
    }
}
