package serveur;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ServerChat{
    public List<Socket> sockets = new ArrayList<Socket>();
    private int nombreClient;
    private boolean isActive = true;
    public static void main(String[] args) throws Exception {
        new ServerChat();
    }
    public ServerChat(){
        try {
                ServerSocket ss = new ServerSocket(1234);
                System.out.println("Server is running...");
                while (isActive){
                    Socket socket = ss.accept(); // wait client to connect
                    ++nombreClient;
                    sockets.add(socket);
                    new Child(socket, nombreClient).start();
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
                    pw.println("You can send message to other clients ):\n\nIf you want to exit you can write \"exit\"");
                    while (isActive){
                        String message = br.readLine();
                        if(message.equals("exit")){
                            pw.println("bye");
                            this.socket.close();
                        }
                        for(Socket s : sockets){
                            if(!s.equals(this.socket)){
                                OutputStream OS = s.getOutputStream();
                                PrintWriter PW = new PrintWriter(OS, true);
                                PW.println(IP+" : "+message);
                            }
                        }
                    }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
