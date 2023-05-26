/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unicauca.openmarket.command;

import co.edu.unicauca.openmarket.domain.service.ProductService;
import co.edu.unicauca.openmarket.domain.Product;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class RemoveProductCommand implements Command {
    private final ProductService productService;
    private final Product product;

        public RemoveProductCommand(ProductService productService, Product product) {
        this.productService = productService;
        this.product = product;
    }

    @Override
    public boolean execute() {
          try {
            return productService.deleteProduct(product.getProductId());
        } catch (Exception ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
          
    }

    @Override
    public void undo() {
        String name= product.getName();
        String desc= product.getDescription();
        Long catId=product.getCategoryId();
        Long id= product.getProductId();
        try {
            productService.saveProduct(id, name, desc, catId);
        } catch (Exception ex) {
            Logger.getLogger(RemoveProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }


}
