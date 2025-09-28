public class OrderBuilder {
    private Cart cart;
    private ShippingStrategy ship;
    private PaymentProcessor pay;
    private String details;
    public OrderBuilder withCart(Cart c){cart=c;return this;}
    public OrderBuilder withShipping(ShippingStrategy s){ship=s;return this;}
    public OrderBuilder withPayment(PaymentProcessor p){pay=p;return this;}
    public OrderBuilder withDetails(String d){details=d;return this;}
    public Order build(){
        double total=cart.subtotal()+ship.cost(cart.subtotal());
        Order o=new Order(total);
        if(pay.pay(total,details)) o.setStatus(OrderStatus.PAID);
        else o.setStatus(OrderStatus.FAILED);
        return o;
    }
}
