package com.example.poshell.db;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PosInMemoryDB implements PosDB {
    private List<Product> products = new ArrayList<>();

    private Cart cart;

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Cart saveCart(Cart cart) {
        this.cart = cart;
        return this.cart;
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean modifyCart(String productId, int amount) {
        for (int i = 0; i < cart.getItems().size(); ++i) {
            if (cart.getItems().get(i).getProduct().getId().equals(productId)) {
                cart.getItems().get(i).getProduct().setId(productId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String productId) {
        for (int i = 0; i < cart.getItems().size(); ++i) {
            if (cart.getItems().get(i).getProduct().getId().equals(productId)) {
                cart.getItems().remove(i);
                return true;
            }
        }
        return false;
    }


    private PosInMemoryDB() {
        this.products.add(new Product("PD1", "iPhone 13", 8999));
        this.products.add(new Product("PD2", "MacBook Pro", 29499));
    }

}
