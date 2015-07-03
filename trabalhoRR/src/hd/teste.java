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
        gu.podeLogar(1);
        BetitanderFileSystem discoC = new BetitanderFileSystem(gu);
        discoC.formatar();
        discoC.criaPasta("/1");
        discoC.criaPasta("/5");
        discoC.mostraPasta("/");
    }
   
}
