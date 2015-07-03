package shell;

import GU.GU;
import java.io.IOException;
import java.util.Scanner;

public class Shell {

    public static void main(String args[]) throws IOException {
        GU gu = new GU();
        CommandosLibrary interpretador = new CommandosLibrary(gu);
        
        Scanner sc = new Scanner(System.in);
        String cmd = "";
        int usuario;
        
        System.out.println("Shell of Betitander SO:");
        System.out.println("-----------------------");
        while (gu.getLogado() == null) {
            System.out.println("Digite Seu usuario ->");
            cmd = sc.nextLine();
            usuario = Integer.valueOf(cmd);
            if (gu.podeLogar(usuario)){
                System.out.println("Usuario Logado -> "+gu.getLogado().getNome());
            }else {
                System.out.println("Não foi possivel efetuar logon, tente novamente");
            }
        }
        
        System.out.println("");
        System.out.print(interpretador.getPathAtual() + " " + "Type your command:");
     //   int i = 0;
        while (sc.hasNext()) {
       //     i++;
            cmd = sc.nextLine();
            if (cmd.equals("exit")) {
                System.out.println("------------------------");
                System.out.println("Logoff of Betitanter.");
                System.out.println("------------------------");
                System.out.println("Thank you for using.");
                break;
            } else {
                interpretador.command(cmd.trim());
            }
            System.out.print(interpretador.getPathAtual() + " " + "Type your command:");
        }
        sc.close();
    }
}
