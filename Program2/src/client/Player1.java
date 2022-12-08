package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player1 {
    int clicked=0;
    int indexButton;
    public JFrame windowClient;
    public List<JButton> buttons = new ArrayList<JButton>();
    public static void main(String[] args){
        new Player1();
    }
    public Player1(){
        try {
            Socket socket = new Socket("100.91.176.173", 1234);
            System.out.println("Je suis connecté ):");

            ObjectInputStream oistream = new ObjectInputStream(socket.getInputStream());
            windowClient = (JFrame)oistream.readObject();
            windowClient.setVisible(true);

            Component[] components = windowClient.getContentPane().getComponents();
            for (Component component : components) {
                if (component instanceof JButton) {
                    JButton button = (JButton)component;
                    buttons.add(button);
                }
            }
            ObjectOutputStream oostream = new ObjectOutputStream(socket.getOutputStream());
            interact(oostream);
            while (true){
                windowClient = (JFrame)oistream.readObject();
                windowClient.setVisible(true);

                Component[] componentss = windowClient.getContentPane().getComponents();
                buttons = null;
                for (Component component : componentss) {
                    if (component instanceof JButton) {
                        JButton button = (JButton)component;
                        buttons.add(button);
                    }
                }
                interact(oostream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void interact(ObjectOutputStream oos){
        for (JButton button:buttons){
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                        clicked++;
                        if(clicked%2 == 0){
                            button.setLabel("O");
                            button.setEnabled(true);
                        }else{
                            button.setLabel("X");
                            button.setEnabled(true);
                            try {
                                oos.writeObject(windowClient);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
            });
        }
    }
}
