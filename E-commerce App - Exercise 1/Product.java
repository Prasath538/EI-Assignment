public class Product {
    private final int id;
    private final String name;
    private final double price;
    private int stock;
    public Product(int id,String name,double price,int stock){
        this.id=id;this.name=name;this.price=price;this.stock=stock;
    }
    public int getId(){return id;}
    public String getName(){return name;}
    public double getPrice(){return price;}
    public int getStock(){return stock;}
    public void reduce(int q){
        if(q>stock) throw new IllegalArgumentException("stock low");
        stock-=q;
    }
    public String toString(){return id + ": " + name + " Rs" + price + " stock:" + stock;}

}
