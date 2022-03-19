package com.example.poshell.biz;

import com.example.poshell.db.PosDB;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {
        return posDB.getCart();
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public void checkout(Cart cart) {

    }

    @Override
    public double total(Cart cart) {
        double sum = 0;
        for (Item item : this.getCart().getItems()) {
            sum += item.getProduct().getPrice() * item.getAmount();
        }
        return sum;
    }

    @Override
    public boolean add(Product product, int amount) {
        if (product == null) return false;
        if (amount <= 0) return false;
        this.getCart().addItem(new Item(product, amount));
        return true;
    }

    @Override
    public boolean add(String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return false;
        if (amount <= 0) return false;

        this.getCart().addItem(new Item(product, amount));
        return true;
    }

    @Override
    public boolean emptyCart() {
        return false;
    }

    @Override
    public boolean delete(String productId) {
        List<Item> items = posDB.getCart().getItems();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getProduct().getId().equals(productId)) {
                posDB.getCart().getItems().remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean modify(String productId, int amount) {
        if (amount < 0) return false;
        if (amount == 0) return delete(productId);
        List<Item> items = posDB.getCart().getItems();
        for (Item item : items) {
            if (item.getProduct().getId().equals(productId)) {
                item.setAmount(amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }
}
