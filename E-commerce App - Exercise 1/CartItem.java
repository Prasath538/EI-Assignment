public class CartItem {
    private final Product p;
    private int q;
    public CartItem(Product p,int q){this.p=p;this.q=q;}
    public void increase(int x){q+=x;}
    public double total(){return p.getPrice()*q;}
    public Product getProduct(){return p;}
    public int getQty(){return q;}
    public String toString(){return p.getName()+" x"+q+" ="+total();}
}
