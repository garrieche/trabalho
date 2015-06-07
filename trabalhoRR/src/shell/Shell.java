package shell;

import java.io.IOException;
import java.util.Scanner;
import static shell.CommandosLibrary.command;

public class Shell {
    public static void main(String args[]) throws IOException {
        System.out.println("Bem vindo ao Betitander:");
        System.out.println("------------------------");
        System.out.println("");
        System.out.print("Type your command:");
        int i = 0; 
        Scanner sc = new Scanner(System.in); 
        String cmd = "";
        String cmd2 = "";
        while(sc.hasNext()){
            i++; 
            cmd = sc.next();
            cmd2 = sc.next();
            if( cmd.equals("exit")) {
                System.out.println("------------------------");
                System.out.println("Logoff of Betitanter.");
                System.out.println("------------------------");
                System.out.println("Thank you for using.");
                break;
            }else {
                //System.out.println(command( cmd )) ;
                command(cmd);
            }
            System.out.println("Type yout command:"); 
        } sc.close(); 
    }
    
}
