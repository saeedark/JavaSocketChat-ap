package Client;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CMain {
    static BufferedReader input ;
    static PrintWriter output ;

    public static void main(String [] args) throws IOException {
        JFrame frame = new JFrame("ClientChatScreen");
        ChatGUI a = new ChatGUI();
        frame.setContentPane(a.chmain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        a.run();

    }
}
