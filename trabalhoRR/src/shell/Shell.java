package shell;

import java.io.IOException;
import java.util.Scanner;


public class Shell {
    public static void main(String args[]) throws IOException {
        CommandosLibrary interpretador = new CommandosLibrary();
        System.out.println("Shell of Betitander SO:");
        System.out.println("-----------------------");
        System.out.println("");
        System.out.print(interpretador.getPathAtual() + " " + "Type your command:");
        int i = 0; 
        Scanner sc = new Scanner(System.in); 
        String cmd = "";
        while(sc.hasNext()){
            i++; 
            cmd = sc.nextLine();
            if( cmd.equals("exit")) {
                System.out.println("------------------------");
                System.out.println("Logoff of Betitanter.");
                System.out.println("------------------------");
                System.out.println("Thank you for using.");
                break;
            }else {
                interpretador.command(cmd.trim());
            }
            System.out.print(interpretador.getPathAtual() + " " + "Type your command:");
        } sc.close(); 
    }
    
}
