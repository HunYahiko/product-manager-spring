package md.tekwill.controller;

import md.tekwill.entity.product.Drink;
import md.tekwill.entity.product.Food;
import md.tekwill.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/food")
    public ResponseEntity<Void> createFood(@RequestBody Food food) {
        productService.save(food);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/admin/drink")
    public ResponseEntity<Void> createDrink(@RequestBody Drink drink) {
        productService.save(drink);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
