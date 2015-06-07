package hd;

import java.io.IOException;

public class teste {

    public static void main(String args[]) throws IOException {
        
        BetitanderFileSystem discoC = new BetitanderFileSystem();
        discoC.formatar();
//        for (int i = 1; i < 16; i++) {
//            String pastinha = "/"+i;
//            System.out.println("Criando a pastinha -> " + pastinha);
//            discoC.criaPasta(pastinha);    
//        }
        discoC.criaPasta("/1");
        discoC.criaPasta("/1/2");
        discoC.criaPasta("/1/2/3");
        discoC.criaPasta("/1/2/3/4");
        discoC.criaPasta("/2");
        discoC.criaPasta("/3");
        discoC.criaPasta("/200");
        System.out.println("apagando pasta vazia que existe");
        discoC.apagaPasta("/2");
        System.out.println("apagando pasta cheia");
        discoC.apagaPasta("/1");
        System.out.println("apagando Pasta em sub pasta vazia");
        discoC.apagaPasta("/1/2/3/4");
        System.out.println("Apagando pasta que nao existe");
        discoC.apagaPasta("/1/3");
        System.out.println("apagando pasta com nome grande 200");
        discoC.apagaPasta("/200");
        System.out.println("Ufa!! que trabalhao!!!");
        
                
    }

}
