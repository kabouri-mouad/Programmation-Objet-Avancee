package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientChatOne extends Thread{
    public static void main(String[] args) throws Exception{
        try {
            Socket socket1 = new Socket("localhost", 1234);
            System.out.println("The client is connected successfully...");
            OutputStream os1 = socket1.getOutputStream();
            PrintWriter pw1 = new PrintWriter(os1, true);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
                        while (true){
                            String message = (String) br1.readLine();
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
            while (true){
                Thread.sleep(1000);
                System.out.print("You : ");
                String string = new Scanner(System.in).nextLine();
                pw1.println(string);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
