import java.util.*;

public class ProductRepository {
    private final Map<Integer,Product> map=new HashMap<>();
    public ProductRepository(){
        map.put(1,new Product(1,"Widget",100,50));
        map.put(2,new Product(2,"Gadget",200,40));
        map.put(3,new Product(3,"Thing",75,60));
    }
    public Collection<Product> list(){return map.values();}
    public Product get(int id){return map.get(id);}
}
