package widget;

import widget.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Cart {

    HashMap<String, ArrayList<HashMap<String, Product>>> cart;
    public int amountPayable, itemCount;
    public boolean isContainNonWSR;
    public static String productTitleInCart = "";

    public Cart() {
        cart = new HashMap<>();
        amountPayable = 0;
        itemCount = 0;
        isContainNonWSR = false;
    }

    public void addProduct(String sellerId, Product product) {
        ArrayList<HashMap<String, Product>> productList = new ArrayList<>();
        HashMap<String, Product> productHashMap = new HashMap<>();
        if(cart.containsKey(sellerId)) {
            productList = cart.get(sellerId);
            productHashMap.put(product.getTitle(), product);
            productTitleInCart = product.getTitle();
            productList.add(productHashMap);
            cart.put(sellerId, productList);

        } else {
            productHashMap.put(product.getTitle(), product);
            productTitleInCart = product.getTitle();
            productList.add(productHashMap);
            cart.put(sellerId, productList);
        }
    }

    public Product getProduct(String sellerId, String productTitle) {
        ArrayList<HashMap<String, Product>> productList = null;
        HashMap<String, Product> productHashMap = null;
        Product product = null;
        if(!cart.containsKey(sellerId)) {
            System.err.println("Cart does not contain the seller id maintain.");
        } else {
            productList = cart.get(sellerId);
            for (HashMap<String, Product> aProductList : productList) {
                productHashMap = aProductList;
                if (productHashMap.containsKey(productTitle)) {
                    product = productHashMap.get(productTitle);
                    break;
                } else if (productTitleInCart.contains(productTitle)) {
                    product = productHashMap.get(productTitleInCart);
                    break;
                }
            }
        }
        return product;
    }

    public boolean isContainSeller(String sellerName) {
        return cart.containsKey(sellerName);
    }

    public boolean isSellerContainProduct(String sellerId, String listId) {
        ArrayList<HashMap<String, Product>> productList = null;
        HashMap<String, Product> productHashMap = null;
        boolean found = false;
        if(!cart.containsKey(sellerId)) {
            System.err.println("Cart does not contain the seller id maintain.");
        } else {
            productList = cart.get(sellerId);
            for (HashMap<String, Product> aProductList : productList) {
                productHashMap = aProductList;
                if (productHashMap.containsKey(listId)) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public Product getProductByTitle(String sellerId, String title) {
        ArrayList<HashMap<String, Product>> productList = null;
        HashMap<String, Product> productHashMap = null;
        Product product = null;
        String text;
        boolean found = false;
        if(!cart.containsKey(sellerId)) {
            System.err.println("Cart does not contain the seller id maintain.");
        } else {
            productList = cart.get(sellerId);
            for (HashMap<String, Product> aProductList : productList) {
                productHashMap = aProductList;
                for (Map.Entry entry : productHashMap.entrySet()) {
                    product = (Product) entry.getValue();
                    title = title.toLowerCase();
                    text = product.getTitle().toLowerCase();
                    if (text.contains(title) || title.contains(text)) {
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }
        }
        return product;
    }
}