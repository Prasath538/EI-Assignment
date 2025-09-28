public class PaymentFactory {
    public static PaymentProcessor create(String t){
        return t.equalsIgnoreCase("CREDIT")?new CreditCardProcessor():new UpiProcessor();
    }
}
