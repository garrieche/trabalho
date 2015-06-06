/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import static hd.BetitanderFileSystem.xBinario;
import static hd.Bits.mudabit;
import java.io.IOException;

/**
 *
 * @author Jair
 */
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
        
        
                
    }

}
