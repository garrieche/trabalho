package hd;

import static hd.BetitanderFileSystem.xBinario;
import static hd.Bits.seguranca;
import java.io.IOException;

public class teste {

    public static void main(String args[]) throws IOException {
        
       BetitanderFileSystem discoC = new BetitanderFileSystem();
        
        discoC.formatar();
        discoC.criaPasta("/2");
        System.out.println(discoC.getNomeEntidade("/2"));
              
//        for (int i = 1; i < 16; i++) {
//            String pastinha = "/"+i;
//            System.out.println("Criando a pastinha -> " + pastinha);
//            discoC.criaPasta(pastinha);    
//        }
//        discoC.criaPasta("/1");
//        discoC.criaPasta("/1/2");
//        discoC.criaArquivo("/1/7", "c://testebetitander.bin");
//        discoC.mostraPasta("/1");
        
                
    }

}
