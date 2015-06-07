package shell;

import hd.BetitanderFileSystem;
import java.io.IOException;

public class CommandosLibrary {

    public static void command(String cmd) throws IOException {
        String vetorCMD[] = cmd.split(" ");
        BetitanderFileSystem SO = new BetitanderFileSystem();
        System.out.println("Comando recebido -> " + cmd);
        switch (vetorCMD[0]) {
            case "md":
                if (vetorCMD.length > 1) {
                    SO.criaPasta(vetorCMD[1]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            case "rd":
                if (vetorCMD.length > 1) {
                    SO.apagaPasta(vetorCMD[1]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            case "ld":
                if (vetorCMD.length > 1) {
                    SO.mostraPasta(vetorCMD[1]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            case "ma":
                if (vetorCMD.length > 2) {
                    SO.criaArquivo(vetorCMD[1], vetorCMD[2]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            case "ra":
                if (vetorCMD.length > 1) {
                    SO.apagaArquivo(vetorCMD[1]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            case "ca":
                if (vetorCMD.length > 2) {
                    SO.copiaArquivo(vetorCMD[1], vetorCMD[2]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            case "xa":
                if (vetorCMD.length > 2) {
                    SO.moveArquivo(vetorCMD[1], vetorCMD[2]);
                } else {
                    System.out.println("falta parametros");
                }
                break;
            default:
                System.out.println("comando Invalido!");
                System.out.println("comandos disponiveis: \n"
                        + "==============================================\n"
                        + "md - MakeDirectory - Cria uma pasta\n"
                        + "Usage md [nomePasta] Exemplo: md /2\n"
                        + "==============================================\n"
                        + "rd - RemoveDirectory - Remove uma pasta Vazia\n"
                        + "Usage rd [nomePasta] Exemplo: md /2\n"
                        + "==============================================\n"
                        + "ld - ListDirectory - Mostra uma pasta"
                        + "Usage ld [nomePasta] Exemplo: ld /2/3\n"
                        + "==============================================\n"
                        + "ma - "
                        + "==============================================\n"
                        + "ra - "
                        + "==============================================\n"
                        + "ca - "
                        + "==============================================\n"
                        + "xa - ");
        }
    }
}
