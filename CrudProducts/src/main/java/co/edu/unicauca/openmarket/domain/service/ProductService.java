package co.edu.unicauca.openmarket.domain.service;


import co.edu.unicauca.openmarket.access.IProductRepository;
import co.edu.unicauca.openmarket.domain.Category;
import co.edu.unicauca.openmarket.domain.Product;
import co.edu.unicauca.openmarket.observer.Observer;
import co.edu.unicauca.openmarket.observer.Subject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Libardo, Julio
 */
public class ProductService implements Subject{
      
    // Ahora hay una dependencia de una abstracción, no es algo concreto,
    // no sabe cómo está implementado.
   public ProductService(){
    
   }
   private List<Observer> observers = new ArrayList<>();
   
    private IProductRepository repository;

    /**
     * Inyección de dependencias en el constructor. Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param repository una clase hija de IProductRepository
     */
    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }
    

    public boolean saveProduct(Long id,String name, String description,Long categoryId) {
        
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setProductId(id);

        
        
        //Validate product
        if (newProduct.getName().isBlank() ) {
            return false;
        }
        boolean result = repository.save(newProduct,categoryId);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;

    }

    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        products = repository.findAll();;

        return products;
    }
    
    public Product findProductById(Long id){
        return repository.findById(id);
    }
    public List<Product> findProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        products = repository.findByName(name);

        return products;
    }
    public List<Product> findProductsByCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
        products = repository.findByCategory(categoryName);

        return products;
    }
    public boolean deleteProduct(Long id){
      boolean result = repository.delete(id);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;
       
    }

    public boolean editProduct(Long productId, Product prod,Long categoryId) {
     
        //Validate product
        if (prod == null || prod.getName().isBlank() ) {
            return false;
        }
        
        if(repository.edit(productId, prod,categoryId)){
            notifyObservers();
               return true;
        }
        return false;

    }
   @Override
  public void registerObserver(Observer catGui) {
        observers.add(catGui);
    }

   @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

   @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}