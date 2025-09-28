public class SMSNotifier implements OrderObserver {
    public void update(Order o){System.out.println("SMS: order "+o.getId()+" "+o.getStatus());}
}
