package hd;

import GU.GU;
import java.io.IOException;

public class teste {

    public static void main(String args[]) throws IOException {
        
//        BetitanderFileSystem discoC = new BetitanderFileSystem();
//        discoC.formatar();
//        for (int i = 1; i < 16; i++) {
//            String pastinha = "/"+i;
//            System.out.println("Criando a pastinha -> " + pastinha);
//            discoC.criaPasta(pastinha);    
//        }
//        discoC.criaPasta("/1");
//        discoC.criaPasta("/1/2");
//        discoC.criaArquivo("/1/7", "c://testebetitander.bin");
//        discoC.mostraPasta("/1");
        GU gu = new GU();
        
        System.out.println(gu.podeLogar(1));
        System.out.println(gu.getLogado().getNome());
        System.out.println(gu.estaNoGrupo(3));
        
                
    }

}
