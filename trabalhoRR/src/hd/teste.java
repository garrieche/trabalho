/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import GU.GU;
import java.io.IOException;

/**
 *
 * @author Jair
 */
public class teste {
    public static void main(String[] args) throws IOException {
        
        GU gu = new GU();
        gu.podeLogar(2);
        BetitanderFileSystem discoC = new BetitanderFileSystem(gu);
        discoC.formatar();
        discoC.criaPasta("/1");
        discoC.criaPasta("/5");
        discoC.mostraPasta("/");
        gu.logout();
        gu.podeLogar(2);
        if (discoC.getSegurança("/5", OperacaoSeguranca.LER)) 
            System.out.println("Pode Ler !!! "); else System.out.println("Nao ler");
        if (discoC.getSegurança("/5", OperacaoSeguranca.ALTERAR)) 
            System.out.println("Pode Alterar !!! "); else System.out.println("nao alterar");
        if (discoC.getSegurança("/5", OperacaoSeguranca.APAGAR)) 
            System.out.println("Pode Apagar !!! "); else System.out.println("nao apagar");
        if (discoC.getSegurança("/5", OperacaoSeguranca.EXECUTAR)) 
            System.out.println("Pode Executar !!! "); else System.out.println("nao executar");
    }
   
}
