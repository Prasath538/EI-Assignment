public class EmailNotifier implements OrderObserver {
    public void update(Order o){System.out.println("Email: order "+o.getId()+" "+o.getStatus());}
}
