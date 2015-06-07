/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shell;

/**
 *
 * @author Jair
 */
public class CommandosLibrary {
    private String returnConsole;
    public static String command( String cmd) {
        String vetorCMD[] = cmd.split(" ");
        String retorno = "";
        for (int i = 0; i < vetorCMD.length ; i++) {
            retorno += ("|" + vetorCMD[i]) ; 
        }
        return retorno;
    }
}
