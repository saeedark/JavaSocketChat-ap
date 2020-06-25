package Client;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatGUI {
    private JTextField textField;
    private JTextArea messageArea;
    public JPanel chmain;
    private JScrollPane scrollPane;

    //constructor for GUI
    public ChatGUI()  {
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String gettext = textField.getText();
                if (!gettext.equals("")) {
                    CMain.output.println(gettext);
                    textField.setText("");
                }
            }
        });

        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                adjustmentEvent.getAdjustable().setValue(adjustmentEvent.getAdjustable().getMaximum());
            }
        });
    }
    // dialog for get names
    private String getName(){
        String out = JOptionPane.showInputDialog(null,"Write your ID :" , "Id getter" ,JOptionPane.PLAIN_MESSAGE );
        return out ;
    }
    // dialog for get names if have same.
    private String getNameDiff(){
        String out = JOptionPane.showInputDialog(null,"ID is fill ,Write another ID :" , "Id getter" ,JOptionPane.PLAIN_MESSAGE );
        return out ;
    }
    // dialog for get password
    private String getPassword(){
        String out = JOptionPane.showInputDialog(null,"Please type your password" , "Password",JOptionPane.PLAIN_MESSAGE);
        return  out;
    }
    // dialog for set password
    private String setPassword(){
        String out = JOptionPane.showInputDialog(null,"Please set your password" , "Password",JOptionPane.PLAIN_MESSAGE);
        return  out;
    }
    // dialog for Get server ip
    private String getServerAddress(){
        String out = JOptionPane.showInputDialog(null,"your Server location" , "Ip getter" ,JOptionPane.PLAIN_MESSAGE );
        return out ;
    }
    // dialog for get Server port
    private int getServerPort(){
        String out = JOptionPane.showInputDialog(null,"your Server Port" , "Port getter" ,JOptionPane.PLAIN_MESSAGE );
        return Integer.parseInt(out) ;
    }
    // // dialog for choose log in option
    private boolean getUserSignState(){
        String[] tick = {"Sign Up", "Log In"};
        String out = (String) JOptionPane.showInputDialog(null,"Select what you want ?" ,"Get In" ,JOptionPane.QUESTION_MESSAGE,null,tick,tick[0]);
        if (out.equals("Sign Up")){
            return false;
        }
        return true;
    }
    // run function to do jobs
    public void run() throws IOException{
        String serverAddress = getServerAddress();
        int serverPort = getServerPort();

        Socket socket = new Socket(serverAddress,serverPort);
        CMain.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        CMain.output = new PrintWriter(socket.getOutputStream(),true);


        while (true){
            String line = CMain.input.readLine();
            if(line.startsWith("SUBMITUSER")){
                messageArea.append("Connection gained" + "\n");
                if (getUserSignState()){
                    messageArea.append("Log in ,, , " + "\n");
                    String name = getName();
                    messageArea.append("Your id: " + name  + "\n");
                    CMain.output.println("LOGIN:"+name);
                }else{
                    messageArea.append("Sign in ,, , " + "\n");
                    String name = getName();
                    messageArea.append("Your id: " + name  + "\n");
                    CMain.output.println("SIGNIN:"+name);
                }
            }else if (line.startsWith("SETPASSWORD")){
                String pass = setPassword();
                CMain.output.println("NEWPASSWORD:"+pass);
            }else if (line.startsWith("SIGNINCHANGE")){
                String name = getNameDiff();
                messageArea.append("Your id:" + name  + "\n");
                CMain.output.println("SIGNIN:"+name);
            }else if (line.startsWith("GETPASSWORD")){
                String pass = getPassword();
                CMain.output.println("CHECKPASSWORD:"+pass);
            }else if(line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")){
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }

}
