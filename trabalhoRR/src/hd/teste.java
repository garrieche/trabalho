/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

/**
 *
 * @author Jair
 */
public class teste {

    public static void main(String args[]) {
        String teste = "/1/2/3";
        System.out.println(teste);
        for (int i = 0; i < teste.length(); i++) {
            System.out.println(teste.substring(1, i));
        }
    }
}
