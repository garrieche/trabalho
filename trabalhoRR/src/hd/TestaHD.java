package hd;

import java.io.IOException;

public class TestaHD {
    public static void main(String[] args) throws IOException {
        BetitanderFileSystem discoC = new BetitanderFileSystem();
        discoC.formatar();
        //discoC.MostraHD();
        
      // System.out.println(discoC.getBlocoVazio() + " e o proximo bloco vazio");
       
        System.out.println(" testou ok ");
       
    }
}
