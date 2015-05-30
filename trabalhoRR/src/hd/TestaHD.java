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
public class TestaHD {
    public static void main(String[] args) throws IOException {
        BetitanderFileSystem discoC = new BetitanderFileSystem();
        discoC.formatar();
        System.out.println(" testou ok ");
       
    }
}
