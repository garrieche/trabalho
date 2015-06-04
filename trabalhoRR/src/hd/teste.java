/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import java.io.IOException;

/**
 *
 * @author Jair
 */
public class teste {

    public static void main(String args[]) throws IOException {
        String teste = "/11/1/3";
        System.out.println(teste);
        String temp = "";
        BetitanderFileSystem discoC = new BetitanderFileSystem();
        System.out.println("retorno-> " + discoC.exist(teste) ); 
//        for (int i = 0; i < teste.length(); i++) {
//            if (teste.charAt(i) == '/') {
//                if (temp != "") {
//                    System.out.println("aqui eu sei a pasta intermediaria para saber o bloco da proxima");
//                    System.out.println(temp);
//                    System.out.println("retorno-> " + discoC.exist(temp) ); 
//                }
//                temp = "";
//            } else {
//                temp = temp + teste.charAt(i);
//            }
//        }
//        System.out.println(temp);
        
        
    }
}
