package Server;

import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.HashMap;

//import Objects.User;

public class Main {
    public static int portnum ; // port number
    //public static HashSet<User> users = new HashSet<User>(); // users in hashset
    public static HashSet<Integer> ids = new HashSet<Integer>();
    public static HashSet<String> names = new HashSet<String>();
    public static HashMap<String,String> userpassword = new HashMap<String,String>();
    public static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); // for ease of broadcast

    public static void main(String [] args)
    {


        // if baraye entekhab port ya daryaf be sosrate args.
        if(args.length == 1){
            portnum = Integer.parseInt(args[0]);
        }else{
            Scanner a = new Scanner(System.in);
            System.out.println("Input the port:");
            portnum = a.nextInt();
        }

        System.out.println("server is going to run... .. .");
        try {
            ServerSocket listener = new ServerSocket(portnum); // set up a listener
            while (true){
                new Handler(listener.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("something Went Wrong :( ");
        }


    }

}
