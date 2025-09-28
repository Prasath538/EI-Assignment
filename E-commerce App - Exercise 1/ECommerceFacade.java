public class ECommerceFacade {
    private final ProductRepository repo=new ProductRepository();
    private final Cart cart=new Cart();
    public void listProducts(){for(Product p:repo.list())System.out.println(p);}
    public void addToCart(int id,int q){
        Product p=repo.get(id);
        if(p==null)throw new IllegalArgumentException("No product");
        p.reduce(q);
        cart.add(p,q);
        System.out.println("Added");
    }
    public void viewCart(){System.out.println(cart);}
    public void checkout(String pt,String det,String ship){
        ShippingStrategy s;
        if(ship.equalsIgnoreCase("EXPRESS")) s=new ExpressShipping();
        else if(ship.equalsIgnoreCase("FREE")) s=new FreeShipping();
        else s=new StandardShipping();
        PaymentProcessor pay=PaymentFactory.create(pt);
        Order o=new OrderBuilder().withCart(cart).withShipping(s).withPayment(pay).withDetails(det).build();
        o.addObserver(new EmailNotifier());
        o.addObserver(new SMSNotifier());
        o.setStatus(OrderStatus.COMPLETED);
        System.out.println("Order "+o.getId()+" Total "+o.getTotal());
        cart.clear();
    }
}
