/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.command;

import co.edu.unicauca.openmarket.domain.Product;
import co.edu.unicauca.openmarket.domain.service.ProductService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class AddProductCommand implements Command {
    
    private final ProductService productService;
    private final Product product;

    public AddProductCommand(ProductService productService, Product product) {
        this.productService = productService;
        this.product = product;
    }

    @Override
    public boolean execute() {
        String name= product.getName();
        String desc= product.getDescription();
        Long catId=product.getCategoryId();
            Long id= product.getProductId();
        try {
            return productService.saveProduct(id,name, desc, catId);
        } catch (Exception ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
    
    }

    @Override
    public void undo() {
        try {
            productService.deleteProduct(product.getProductId());
        } catch (Exception ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}