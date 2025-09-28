import java.util.*;

public class Order {
    private static int seq=1000;
    private final int id;
    private final double total;
    private OrderStatus status=OrderStatus.CREATED;
    private final List<OrderObserver> obs=new ArrayList<>();
    public Order(double total){
        this.id=seq++;this.total=total;
    }
    public int getId(){return id;}
    public double getTotal(){return total;}
    public OrderStatus getStatus(){return status;}
    public void setStatus(OrderStatus s){status=s;notifyObs();}
    public void addObserver(OrderObserver o){obs.add(o);}
    private void notifyObs(){for(OrderObserver o:obs)o.update(this);}
}
