public class CreditCardProcessor implements PaymentProcessor {
    public boolean pay(double amt,String d){
        System.out.println("Charging card "+d+" amount "+amt);
        return true;
    }
}
