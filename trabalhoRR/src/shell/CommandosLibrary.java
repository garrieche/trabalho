package shell;

import hd.BetitanderFileSystem;
import java.io.IOException;

public class CommandosLibrary {

    public static void command(String cmd) throws IOException {
        String vetorCMD[] = cmd.split(" ");
        BetitanderFileSystem SO = new BetitanderFileSystem();

        switch (vetorCMD[0]) {
            case "md":
                SO.criaPasta(vetorCMD[1]);
            case "rd":
                SO.apagaPasta(vetorCMD[1]);
            case "ld":
                System.out.println("nao implementado");
            case "ma":
                System.out.println("nao implementado");
            case "ra":
                System.out.println("nao implementado");
            case "ca":
                System.out.println("nao implementado");
            case "xa":
                System.out.println("nao implementado");
                break;
            default:
                System.out.println("comando Invalido!");
        }

    }
}