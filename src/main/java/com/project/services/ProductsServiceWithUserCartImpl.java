package com.project.services;

import com.project.controllers.sessionModeControllers.enums.ModifyCartItemsResults;
import com.project.models.Product;
import com.project.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("ProductsService")
public class ProductsServiceWithUserCartImpl implements ProductsServiceWithUserCart {

    private List<Product> cartProducts = new ArrayList<>();

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Product> getAllProductsAsList() {
        return productsRepository.selectProducts(new HashMap<>());
    }

    @Override
    public String getAllProductsAsString() {
        List<Product> products = productsRepository.selectProducts(new HashMap<>());
        String result = "";
        for (Product product : products) {
            result = result + product.toString() + " ";
        }
        return result;
    }

    @Override
    public Map<String, Integer> getTitleAmountProductsAsMap(){
        List<Product> products = productsRepository.selectProducts(new HashMap<>());
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            result.put(product.getTitle(), product.getAvailable());
        }
        return result;
    }

    @Override
    public Map<String, Integer> getTitleIdProductsAsMap() {
        List<Product> products = productsRepository.selectProducts(new HashMap<>());
        Map<String, Integer> result = new HashMap<>();
        for (Product product : products) {
            result.put(product.getTitle(), product.getId());
        }
        return result;
    }

    @Override
    public List<Product> selectProducts(Map<String, Object> conditions) {
        return productsRepository.selectProducts(conditions);
    }

    @Override
    public int updateProducts(Map<String, Object> columns, Map<String, Object> conditions) {
        return productsRepository.updateProducts(columns, conditions);
    }

    @Override
    public String addItemToCardProducts(int ASKED_ITEM_ID, int ASKED_QUANTITY) {
        String respond = null;
        Map<String, Object> conditions = new HashMap();
        conditions.put("id", ASKED_ITEM_ID);
        List<Product> selectedProducts = selectProducts(conditions);

        if (selectedProducts.isEmpty()) {
            respond = "There is no product with id " + ASKED_ITEM_ID;
        } else {
            Product foundProduct = selectedProducts.get(0);
            final int AVAILABLE = foundProduct.getAvailable();
            if (AVAILABLE < ASKED_QUANTITY) {
                respond = "There is not enough product";
            } else {
                List<Product> productsFromCartWithAskedId = cartProducts.stream().filter(cartProduct -> cartProduct.getId() == foundProduct.getId()).collect(Collectors.toList());
                if (productsFromCartWithAskedId.isEmpty()) {
                    cartProducts.add(new Product(foundProduct.getId(), foundProduct.getTitle(), ASKED_QUANTITY, foundProduct.getPrice()));
                } else {
                    Product productFromCartWithAskedId = productsFromCartWithAskedId.get(0);
                    productFromCartWithAskedId.setAvailable(productFromCartWithAskedId.getAvailable() + ASKED_QUANTITY);
                }
                Map<String, Object> fieldsToUpdate = new HashMap();
                fieldsToUpdate.put("available", AVAILABLE - ASKED_QUANTITY);
                updateProducts(fieldsToUpdate, conditions);
                respond = ASKED_QUANTITY + " items with id " + ASKED_ITEM_ID + " is added";
            }
        }
        return respond;
    }

    @Override
    public String displayCartContent() {
        String result = "";
        for (Product product : cartProducts) {
            result += "title: " + product.getTitle() + ", amount in cart: " + product.getAvailable() +
                    " total value: " + product.getAvailable()*product.getPrice() + " ";
        }
        if (result.equals("")) {
            result = "Your cart is empty";
        }
        return result;
    }

    @Override
    public boolean removeItemFromCartById(int id) {
        Optional<Product> productToRemove = cartProducts.stream().filter(product -> product.getId() == id).findFirst();
        if (productToRemove.isPresent()) {
            Map<String, Object> conditionsToSelect = new HashMap();
            conditionsToSelect.put("id", id);
            int availableInDB = selectProducts(conditionsToSelect).get(0).getAvailable();
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", availableInDB + productToRemove.get().getAvailable());
            updateProducts(columnsToUpdate, conditionsToSelect);
            cartProducts.remove(productToRemove.get());
            return true;
        }
        return false;
    }

    @Override
    public ModifyCartItemsResults modifyCartItem(int id, int newAmount) {
        List<Product> modifiedProductsFromCart = cartProducts.stream().filter(cartProduct -> cartProduct.getId() == id).collect(Collectors.toList());
        if (modifiedProductsFromCart.isEmpty()) {
            return ModifyCartItemsResults.NOT_FOUND_IN_CART;
        }
        Map<String, Object> conditionsToSelect = new HashMap();
        conditionsToSelect.put("id", id);
        int availableInDB = selectProducts(conditionsToSelect).get(0).getAvailable();
        Product productFromCart = modifiedProductsFromCart.get(0);
        if (newAmount > availableInDB + productFromCart.getAvailable()) {
            return ModifyCartItemsResults.NOT_ENOUGH_IN_DATABASE;
        } else {
            Product productFromDb = selectProducts(conditionsToSelect).get(0);
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDb.getAvailable() - (newAmount - productFromCart.getAvailable()));
            updateProducts(columnsToUpdate, conditionsToSelect);
            productFromCart.setAvailable(newAmount);
        }
        return ModifyCartItemsResults.MODIFIED;
    }

    @Override
    public boolean checkValidityProducts() {
        for (Product product : cartProducts) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", product.getId());
            List<Product> products = selectProducts(conditions);
            int productAvailable = products.get(0).getAvailable();
            if (products.isEmpty() || productAvailable < product.getAvailable()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearCartProducts() {
         cartProducts.clear();
    }

    @Override
    public void returnGoodsToStore() {
        List<Product> productsFromDataBase = getAllProductsAsList();
        cartProducts.forEach(product -> {
            Optional<Product> productFromDB = productsFromDataBase.stream().filter(productFromDBInFilter -> productFromDBInFilter.getId() == product.getId()).findFirst();
            if (!productFromDB.isPresent()) {
                System.out.println("database error");
                return;
            }
            Map<String, Object> columnsToUpdate = new HashMap<>();
            columnsToUpdate.put("available", productFromDB.get().getAvailable() + product.getAvailable());
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("id", product.getId());
            updateProducts(columnsToUpdate, conditions);
        });
    }
}
