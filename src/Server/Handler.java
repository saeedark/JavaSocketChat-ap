package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class Handler extends Thread {

    //setting thing ready
    private String name ;
    private int id ;
    private Socket socket;
    private BufferedReader input ;
    private PrintWriter output ;

    //constructor with socket
    public Handler(Socket socket){
        this.socket = socket;
    }

    // run function to do all jobs
    public void run() {
        try {
            // set up input and output
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream() ,true );

            // get uset identification
       /* String msg = "G3T!N";
        while (true){
        output.println(msg);
        String getin = input.readLine();
        if (getin == null){
            return;
        }else if(getin == "L0G!N"){
            msg = "L0G!N";

        }
            synchronized (Main.ids){

            }
        }*/
            String callmsg = "SUBMITUSER";
            String Id = "";
            while (true) {
                output.println(callmsg);
                String inpp = input.readLine();
                if (inpp == null) {
                    return;
                }else if(inpp.startsWith("SIGNIN")){
                    System.out.println("Some one is going to Sign in");
                    String name = inpp.substring(7);
                    synchronized (Main.names) {
                        if (!Main.names.contains(name)) {
                            System.out.println("get in: " + name );
                            Main.names.add(name);
                            Id = name;
                            callmsg = "SETPASSWORD" ;
                        }else{
                            callmsg="SIGNINCHANGE";
                        }
                    }
                }else if(inpp.startsWith("LOGIN")){
                    System.out.println("Some one is going to log in");
                    String name = inpp.substring(6);
                    synchronized (Main.names) {
                        if (Main.names.contains(name)) {
                            Id = name;
                            System.out.println("get pass: " + name );
                            callmsg = "GETPASSWORD";
                        }else{
                            callmsg = "SUBMITUSER";
                        }
                    }
                }else if (inpp.startsWith("NEWPASSWORD")){
                    Main.userpassword.put(Id, inpp.substring(13));
                    break;
                }else if(inpp.startsWith("CHECKPASSWORD")){
                    if (Main.userpassword.get(Id).equals(inpp.substring(15))){
                        break;
                    }else{
                        callmsg = "GETPASSWORD";
                    }
                }
            /*synchronized (Main.names) {
                if (!Main.names.contains(name)) {
                    Main.names.add(name);
                    break;
                }
            }*/
            }
            output.println("NAMEACCEPTED");
            Main.writers.add(output);
            while (true) {
                String inputt = input.readLine();
                if (inputt == null) {
                    return;
                }
                for (PrintWriter writer : Main.writers) {
                    writer.println("MESSAGE " + Id + ": " + inputt);
                    System.out.println("MESSAGE " + Id + ": " + inputt);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }finally {
            if (output != null) {
                Main.writers.remove(output);
            }
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}