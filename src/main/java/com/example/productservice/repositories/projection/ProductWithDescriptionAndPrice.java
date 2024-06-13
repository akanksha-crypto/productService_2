package com.example.productservice.repositories.projection;

import java.math.BigDecimal;

public interface ProductWithDescriptionAndPrice {
    Long getId();
    BigDecimal getPrice();
}
