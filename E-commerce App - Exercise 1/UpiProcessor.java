public class UpiProcessor implements PaymentProcessor {
    public boolean pay(double amt,String d){
        System.out.println("UPI "+d+" amount "+amt);
        return true;
    }
}
