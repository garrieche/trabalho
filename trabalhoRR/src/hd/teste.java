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
        
        //discoC.formatar();
        // discoC.MostraHD();
        
        String teste = "/1";        
//        if (discoC.criaPasta(teste)) {
//            System.out.println("Criou!!!");
//        } else {
//            System.out.println("Deu zica!!!");
//        }
        // discoC.MostraHD();
//        byte teste = (byte) 10000000;
//        char umChar = (char)teste;
//        char charMudado = mudabit( umChar , (char)1 ,(char) 1);
//        System.out.println("Mudado -> " + xBinario(charMudado));
//        teste = "/2";
//        if (discoC.criaPasta(teste)) {
//            System.out.println("Criou!!!");
//        } else {
//            System.out.println("Deu zica!!!");
//        }
//        teste = "/3";
//        if (discoC.criaPasta(teste)) {
//            System.out.println("Criou!!!");
//        } else {
//            System.out.println("Deu zica!!!");
//        }
//        teste = "/4";
//        if (discoC.criaPasta(teste)) {
//            System.out.println("Criou!!!");
//        } else {
//            System.out.println("Deu zica!!!");
//        }
//        teste = "/5";
//        if (discoC.criaPasta(teste)) {
//            System.out.println("Criou!!!");
//        } else {
//            System.out.println("Deu zica!!!");
//        }
        teste = "/5";
        if (discoC.criaPasta(teste)) {
            System.out.println("Criou!!!");
        } else {
            System.out.println("Deu zica!!!");
        }
    }

}
