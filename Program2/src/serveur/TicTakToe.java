package serveur;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TicTakToe implements Serializable {
    private static JFrame frame;
    public static ObjectInputStream oistream1, oistream2;
    public static ObjectOutputStream oostream1, oostream2;
    static int i=0;
    public static List<Socket> sockets = new ArrayList<Socket>();
    public static List<JButton> buttons = new ArrayList<JButton>();
    static String str="X";
    static boolean winner =true;
    public static void main(String[] args){
        try{
            TicTakToe window = new TicTakToe();
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Server is running...");

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JLabel label1 = new JLabel("You are Player X.");
                        JLabel label2 = new JLabel("<html>Valid move,please wait<br/>Opponent moved, your turn.<br/>Congrats, you're the winner</html>");
                        label1.setFont(new Font("Tahoma", Font.PLAIN, 18));
                        label2.setFont(new Font("Tahoma", Font.PLAIN, 18));
                        label1.setBounds(150, 0, 500, 20);
                        label2.setBounds(100, 350, 500, 150);
                        window.frame.add(label1);
                        window.frame.add(label2);
                        oostream1 = new ObjectOutputStream(sockets.get(0).getOutputStream());
                        oostream1.writeObject(window.frame);
                        label1.setText("");
                        label1.setText("You are Player O.");
                        label2.setText("");
                        label2.setText("<html>Other Player connected, please wait.<br/>Opponent moved, your turn.</html>");
                        window.frame.add(label1);
                        oostream2 = new ObjectOutputStream(sockets.get(1).getOutputStream());
                        oostream2.writeObject(window.frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        oistream1 = new ObjectInputStream(sockets.get(0).getInputStream());
                        oistream2 = new ObjectInputStream(sockets.get(1).getInputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    while (true) {
                        frame = null;
                        buttons = null;
                        try {
                            if ((JFrame) oistream1.readObject() != null) {
                                frame = (JFrame) oistream1.readObject();
                                oostream2.writeObject(frame);
                            }
                            if((JFrame) oistream2.readObject() != null) {
                                frame = (JFrame) oistream2.readObject();
                                oostream1.writeObject(frame);
                            }
                            Component[] components = frame.getContentPane().getComponents();
                            for (Component component : components){
                                if (component instanceof JButton) {
                                    JButton button = (JButton)component;
                                    buttons.add(button);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            while (i<2) {
                Socket socket = ss.accept(); // wait client to connect
                System.out.println("Un player est connect !!!");
                sockets.add(socket);
                i++;
            }
            t1.start();
            while (winner){
                if( (buttons.get(0).getText().equals(buttons.get(1).getText())) && (buttons.get(1).getText().equals(buttons.get(2).getText()))){
                    String value = buttons.get(0).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(3).getText().equals(buttons.get(4).getText())) && (buttons.get(4).getText().equals(buttons.get(5).getText()))){
                    String value = buttons.get(3).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(6).getText().equals(buttons.get(7).getText())) && (buttons.get(7).getText().equals(buttons.get(8).getText()))){
                    String value = buttons.get(6).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(0).getText().equals(buttons.get(3).getText())) && (buttons.get(3).getText().equals(buttons.get(6).getText()))){
                    String value = buttons.get(0).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(1).getText().equals(buttons.get(4).getText())) && (buttons.get(4).getText().equals(buttons.get(7).getText()))){
                    String value = buttons.get(1).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(2).getText().equals(buttons.get(5).getText())) && (buttons.get(5).getText().equals(buttons.get(8).getText()))){
                    String value = buttons.get(2).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(0).getText().equals(buttons.get(4).getText())) && (buttons.get(4).getText().equals(buttons.get(8).getText()))){
                    String value = buttons.get(0).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }else if((buttons.get(2).getText().equals(buttons.get(4).getText())) && (buttons.get(4).getText().equals(buttons.get(6).getText()))){
                    String value = buttons.get(2).getText();
                    JOptionPane.showMessageDialog(null, value+" is the winner");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     *  Create the application
     */
    public TicTakToe(){
        initialize();
    }
    private void initialize(){
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btn1 = new JButton("");
        btn1.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn1.setBounds(100, 60, 100, 100);
        frame.getContentPane().add(btn1);

        JButton btn2 = new JButton("");
        btn2.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn2.setBounds(200, 60, 100, 100);
        frame.getContentPane().add(btn2);

        JButton btn3 = new JButton("");
        btn3.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn3.setBounds(300, 60, 100, 100);
        frame.getContentPane().add(btn3);

        JButton btn4 = new JButton("");
        btn4.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn4.setBounds(100, 160, 100, 100);
        frame.getContentPane().add(btn4);

        JButton btn5 = new JButton("");
        btn5.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn5.setBounds(200, 160, 100, 100);
        frame.getContentPane().add(btn5);

        JButton btn6 = new JButton("");
        btn6.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn6.setBounds(300, 160, 100, 100);
        frame.getContentPane().add(btn6);

        JButton btn7 = new JButton("");
        btn7.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn7.setBounds(100, 260, 100, 100);
        frame.getContentPane().add(btn7);

        JButton btn8 = new JButton("");
        btn8.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn8.setBounds(200, 260, 100, 100);
        frame.getContentPane().add(btn8);

        JButton btn9 = new JButton("");
        btn9.setFont(new Font("Tahoma", Font.PLAIN, 79));
        btn9.setBounds(300, 260, 100, 100);
        frame.getContentPane().add(btn9);
    }
}
