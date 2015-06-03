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
        String teste = "/1";
        System.out.println(teste);
        //System.out.println(teste.substring(0,0));
        //System.out.println("" + teste.charAt(0));
        String temp = "";
        for (int i = 0; i < teste.length(); i++) {
           
            if (teste.charAt(i) == '/') {
                if (temp != ""){
                     System.out.println("aqui eu sei a pasta intermediaria para saber o bloco da proxima");
                System.out.println(temp);
                
//                if (umBloco[3] == temp ) //aqui eu acho
//                    return umbloco (0 e 1); //numero do bloco no hd
//                    else
//                    if umbloco[7] == temp // testo o proximo na pasta
//                            return umbloco 4 e 5
//                            else 
//                            if umBloco [11] ....
//                            
//                            if umbloco [15]
//                                    return umbloco [11 e 12]
//                                    else 
//                                    if pasta continua (testa byte 16 e 16)
//                                    le proximo bloco e reinicia esta busca
//                                            else
//                                    return nao existe
//                                            
//                                            
//                
                }
                
                
                temp = "";
               
            }else {
                temp = temp + teste.charAt(i);
                
                
            }
            //
        }
        System.out.println(temp);  
        BetitanderFileSystem b = new BetitanderFileSystem();
        b.criaPasta(teste);
        
    }
}
