package dev.tiagomarks.java_demo.product.runner;

import org.springframework.stereotype.Component;

import dev.tiagomarks.java_demo.product.repository.ProductRepository;
import dev.tiagomarks.java_demo.product.entity.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CliRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        log.info("Running CLI Runner");
        log.info("Product count: {}", productRepository.count());
        boolean isValidProductFlag = false;
        Map<String, String> options = new HashMap<>();
        Product product = new Product();

        for (String arg : args) {
            if (arg.startsWith("--product=")) {
                isValidProductFlag = true;
            } else if (isValidProductFlag && arg.startsWith("--") && arg.contains("=")) {
                String[] parts = arg.substring(2).split("=", 2);
                options.put(parts[0], parts[1]);
            }
        }

        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "name" -> product.setName(option.getValue());
                case "description" -> product.setDescription(option.getValue());
                case "price" -> product.setPrice(new BigDecimal(option.getValue()));
                case "quantity" -> product.setQuantity(Integer.parseInt(option.getValue()));
                default -> log.warn("Unknown option: {}", option.getKey());
            }
        }

        Product result = productRepository.save(product);
        log.info("Saved product: {}", result.toString());
    }
}
