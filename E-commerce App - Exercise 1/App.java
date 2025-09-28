import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ECommerceFacade facade = new ECommerceFacade();
        boolean running = true;
        while (running) {
            System.out.println("\n1 List Products\n2 Add To Cart\n3 View Cart\n4 Checkout\n5 Exit");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": facade.listProducts(); break;
                case "2":
                    System.out.print("Product ID: ");
                    int pid = Integer.parseInt(sc.nextLine());
                    System.out.print("Qty: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    facade.addToCart(pid, qty);
                    break;
                case "3": facade.viewCart(); break;
                case "4":
                    System.out.print("Payment (CREDIT/UPI): ");
                    String ptype = sc.nextLine();
                    System.out.print("Details: ");
                    String details = sc.nextLine();
                    System.out.print("Shipping (STANDARD/EXPRESS/FREE): ");
                    String ship = sc.nextLine();
                    facade.checkout(ptype, details, ship);
                    break;
                case "5": running = false; break;
                default: System.out.println("Invalid");
            }
        }
        sc.close();
    }
}
