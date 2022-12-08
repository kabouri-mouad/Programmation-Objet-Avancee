package fibonacci;
import java.rmi.Naming;
public class Client {
    public static void main(String[] args) {
        //System.setSecurityManager(new SecurityManager());
        // System.setProperty("java.security.policy","file./security.policy");

        try {
            Serveur serveur = (Serveur) Naming.lookup("rmi://127.0.0.1/Fibona");
            System.out.println("The result is Fibonacci(5) = "+serveur.fibonnaci(6));
        } catch (Exception e) {
            System.out.println("hellormi.HelloClient exception: " + e);
        }
    }
}
