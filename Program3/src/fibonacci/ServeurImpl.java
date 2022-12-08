package fibonacci;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurImpl extends UnicastRemoteObject implements Serveur{
    int f0 = 0, f1 = 1, suivant=0;
    protected ServeurImpl() throws RemoteException {
    }
    @Override
    public int fibonnaci(int rang) throws RemoteException {
       if(rang == 0){
           return (0);
       }else if(rang == 1){
           return (1);
       }else{
           return (fibonnaci(rang-1) + fibonnaci(rang-2));
       }
    }
    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            ServeurImpl Fibona = new ServeurImpl();
            Naming.rebind("rmi://localhost/Fibona", Fibona);
            System.out.println("ServerImpl Fibonacci bound in registry at the url ");
            System.out.println("Server is ready.");
        } catch (Exception e) {
            System.out.println("Server failed.\n" + e);
        }
    }
}
