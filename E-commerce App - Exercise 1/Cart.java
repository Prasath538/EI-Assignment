import java.util.*;

public class Cart {
    private final Map<Integer,CartItem> items=new LinkedHashMap<>();
    public void add(Product p,int q){
        CartItem i=items.get(p.getId());
        if(i==null) items.put(p.getId(),new CartItem(p,q));
        else i.increase(q);
    }
    public Collection<CartItem> getItems(){return items.values();}
    public double subtotal(){
        double s=0;
        for(CartItem c:items.values()) s+=c.total();
        return s;
    }
    public void clear(){items.clear();}
    public String toString(){
        if(items.isEmpty()) return "Cart empty";
        StringBuilder sb=new StringBuilder();
        for(CartItem c:items.values()) sb.append(c).append("\n");
        sb.append("Subtotal: ").append(subtotal());
        return sb.toString();
    }
}
