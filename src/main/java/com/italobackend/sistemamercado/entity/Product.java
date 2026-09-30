package com.italobackend.sistemamercado.entity;

import com.italobackend.sistemamercado.enums.Category;
import jakarta.persistence.*;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CÓDIGO SKU INTERNO
    @Column(name = "codigo", nullable = false, unique = true, length = 100, updatable = false)
    private String code;

    @Column(name = "nome", nullable = false, length = 100)
    private String nameProduct;

    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    @Column(name = "marca", nullable = false, length = 100)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 100)
    private Category category;

    // CÓDIGO DO FORNECEDOR P/ENTRADA E SAIDA DE PRODUTOS
    @Column(name = "codigo_barras", nullable = false, length = 14)
    private String barCode;

    public Product() {
    }

    public Product(String nameProduct, int quantity, BigDecimal price, String brand, Category category, String barCode) {
        validateBarCode(barCode);
        validateQuantity(quantity);
        validatePrice(price);
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.barCode = barCode;

    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        validateBarCode(barCode);
        this.barCode = barCode;
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço é obrigatório e não pode ser negativo.");
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
    }

    private static void validateBarCode(String barCode) {
        boolean gtinValid = barCode != null
                && barCode.matches("\\d{8}|\\d{12,14}")
                && EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(barCode);

        if (!gtinValid) {
            throw new IllegalArgumentException("Código de barras inválido: " + barCode);
        }
    }

    @PrePersist
    private void generateCode() {
        if (this.code == null) {
            this.code = "SKU-" + UUID.randomUUID().toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();
        }
    }
}
