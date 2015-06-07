package shell;

import hd.BetitanderFileSystem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandosLibrary {
    private String dirAtual; 
    private BetitanderFileSystem SO;  

    public CommandosLibrary() throws IOException {
        this.SO = new BetitanderFileSystem();
        this.dirAtual = "/";
    }
    
    public void command(String cmd) throws IOException {
        String[] comando = formataCMD(cmd);
        String retorno="";
                
        switch (comando[0]) {
            case "format":
                SO.formatar();
                retorno = "Formatação Realizada.";
                break;
            case "mostraHD":
                SO.MostraHD();
                break;
            case "md":
                if (comando.length > 1)
                    makeDirectory( comando[1]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
                break;
            case "ld":
                if (comando.length > 1)
                    listDirectory( comando[1]);
                else listDirectory(getPathAtual());
                break;
            case "gabrielgay":
                System.out.println("Sim ele é gay");
                break;
                        
            case "rd":
                if (comando.length > 1)
                   removeDirectory( comando[1]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
                break;
            case "ma":
                if (comando.length > 2)
                    makeArchive( comando[1] , comando[2]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
                break;
            case "ra":
                if (comando.length >1)
                    removeArchive( comando[1]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
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
                break;
        }    
        if (!retorno.isEmpty())
            System.out.println(retorno);
        
    }
    
    public String getPathAtual() {
        return this.dirAtual;
    }
    
    private static String[] formataCMD( String cmd  ) {
        cmd = cmd.trim(); 
        String splitado[] = cmd.split(" ");
        List comando = new ArrayList();
        for (String splitado1 : splitado) {
             if (!splitado1.isEmpty()) comando.add(splitado1);
        }
        return (String[]) comando.toArray (new String[comando.size()]); 
    }

    private void makeDirectory(String comando) throws IOException {
        SO.criaPasta(comando);
        
    }

    private void listDirectory(String comando) throws IOException {
        SO.mostraPasta(comando);
    }

    private void removeDirectory(String comando) throws IOException {
        SO.apagaPasta(comando);
    }

    private void makeArchive(String pathBetitander, String pathNatural) throws IOException {
        System.out.println("Wait Loading data..");
        if (SO.criaArquivo(pathBetitander, pathNatural)) {
            System.out.println("1 file(s) Copied sucessfull.");
        }else
            System.out.println("Copy failed.");
        
    }

    private void removeArchive(String comando) throws IOException {
        SO.apagaArquivo(comando);
    }
}
//
//case "rd":
//                if (vetorCMD.length > 1) {
//                    SO.apagaPasta(vetorCMD[1]);
//                } else {
//                    System.out.println("falta parametros");
//                }
//                break;
//            case "ld":
//                if (vetorCMD.length > 1) {
//                    SO.mostraPasta(vetorCMD[1]);
//                } else {
//                    System.out.println("falta parametros");
//                }
//                break;
//            case "ma":
//                if (vetorCMD.length > 2) {
//                    SO.criaArquivo(vetorCMD[1], vetorCMD[2]);
//                } else {
//                    System.out.println("falta parametros");
//                }
//                break;
//            case "ra":
//                if (vetorCMD.length > 1) {
//                    SO.apagaArquivo(vetorCMD[1]);
//                } else {
//                    System.out.println("falta parametros");
//                }
//                break;
//            case "ca":
//                if (vetorCMD.length > 2) {
//                    SO.copiaArquivo(vetorCMD[1], vetorCMD[2]);
//                } else {
//                    System.out.println("falta parametros");
//                }
//                break;
//            case "xa":
//                if (vetorCMD.length > 2) {
//                    SO.moveArquivo(vetorCMD[1], vetorCMD[2]);
//                } else {
//                    System.out.println("falta parametros");
//                }
//                break;
            