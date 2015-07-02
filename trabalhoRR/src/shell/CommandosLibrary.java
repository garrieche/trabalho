package shell;

import hd.BetitanderFileSystem;
import hd.OperacaoSeguranca;
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
                if (comando.length > 1)
                    removeArchive( comando[1]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
                break;
            case "ca":
                if (comando.length > 2)
                    copyArchive( comando[1], comando[2]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
                break;
            case "xa":
                if (comando.length > 2) 
                    moveArchive(comando[1], comando[2]);
                else retorno = "Parametros Faltantes ou Inexistentes.";
                break;
            default:
                System.out.println("comando Invalido!");
                System.out.println("comandos disponiveis: \n"
                        + "===============================================\n"
                        + "md - MakeDirectory - Cria uma pasta\n"
                        + "Usage md [nomePasta] Exemplo: md /2\n"
                        + "===============================================\n"
                        + "rd - RemoveDirectory - Remove uma pasta Vazia\n"
                        + "Usage rd [nomePasta] Exemplo: md /2\n"
                        + "===============================================\n"
                        + "ld - ListDirectory - Mostra uma pasta"
                        + "Usage ld [nomePasta] Exemplo: ld /2/3\n"
                        + "===============================================\n"
                        + "ma - Make Archive - Cria um arquivo\n"
                        + "Usage ma [PastaOrigem] [pastaDestino]\n"
                        + "Exemplo: ma c://testebetitander.bin /1\n"
                        + "===============================================\n"
                        + "ra - Remove Archive - remove um arquivo\n"
                        + "Usage ra [caminho/nomeArquivo] Exemplo: ra /1/2\n"
                        + "===============================================\n"
                        + "ca - Copy Archive - Copia Arquivo\n"
                        + "Usage ca [caminho/nomeArqOrig] [caminhoDestino]\n"
                        + "Exemplo: ca /1/2 /3\n"
                        + "===============================================\n"
                        + "xa - move Archive - Move Arquivo\n"
                        + "Usage xa [caminho/nomeArqOrig] [caminhoDestino]\n"
                        + "Exemplo: xa /1/2 /3\n");
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
    
    private void copyArchive(String comando, String comando0) throws IOException {
        SO.copiaArquivo(comando, comando0);    
    
    }

    private void moveArchive(String comando, String comando0) throws IOException {
        SO.moveArquivo(comando, comando0);
    }
    
    private void executaArquivo(String comando) throws IOException {
        SO.executaArquivo(comando);
    }
}            