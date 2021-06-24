package md.tekwill.dao;

import md.tekwill.entity.product.Drink;
import md.tekwill.entity.product.Food;
import md.tekwill.entity.product.FoodCategory;
import md.tekwill.entity.product.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = null;
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        LocalDate bestBefore = resultSet.getDate("best_before").toLocalDate();
        String category = resultSet.getString("category");
        double volume = resultSet.getDouble("volume");

        if (category != null) {
            product = new Food(name, price, bestBefore, FoodCategory.valueOf(category));
        }

        if (volume != 0.0) {
            product = new Drink(name, price, bestBefore, volume);
        }

        if (product != null) {
            product.setId(id);
        }
        return product;
    }
}
