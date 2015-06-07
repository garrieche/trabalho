/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shell;

import java.util.Scanner;
import static shell.CommandosLibrary.command;

/**
 *
 * @author Jair
 */
public class Shell {
    public static void main(String args[]) {
        System.out.println("Bem vindo ao Betitander:");
        System.out.println("------------------------");
        System.out.println("");
        System.out.print("Type your command:");
        int i = 0; 
        Scanner sc = new Scanner(System.in); 
        String cmd = "";
        while(sc.hasNext()){
            i++; 
            cmd = sc.next();
            if( cmd.equals("exit")) {
                System.out.println("------------------------");
                System.out.println("Logoff of Betitanter.");
                System.out.println("------------------------");
                System.out.println("Thank you for using.");
                break;
            }else {
                System.out.println(command( cmd )) ;
            }
            System.out.println("Type yout command:"); 
        } sc.close(); 
    }
    
}
