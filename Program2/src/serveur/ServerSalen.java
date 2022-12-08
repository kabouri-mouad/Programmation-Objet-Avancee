package serveur;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ServerSalen extends Thread{
    public List<Socket> sockets = new ArrayList<Socket>();
    private int nombreClient;
    public int i;
    public static void main(String[] args) throws Exception {
        new ServerSalen().start();
    }
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Server is running...");
            while (i<2){
                Socket socket = ss.accept(); // wait client to connect
                ++nombreClient;
                sockets.add(socket);
                new Child(socket, nombreClient).start();
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    class Child extends Thread{
        private Socket socket;
        private int numeroClient;
        public Child(Socket s, int numClient) {
            this.socket = s;
            this.numeroClient = numClient;
        }
        public void run(){
            try {
                InputStream is = this.socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String IP = socket.getRemoteSocketAddress().toString();
                pw.println("You\'re welcome you\'re the client number :"+this.numeroClient);
                pw.println("You can send message to other clients ):");
                pw.println("If you want to exit you can write \"exit\"");
                while (true){
                    String message = br.readLine();
                    if(message.equals("exit")){
                        pw.println("bye");
                        i=1;
                        this.socket.close();
                    }
                    for(Socket s : sockets){
                        if(!s.equals(this.socket)){
                            OutputStream OS = s.getOutputStream();
                            PrintWriter PW = new PrintWriter(OS, true);
                            PW.println(IP+" : "+message);
                            PW.println("La valeur de i = "+i);
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
