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
public class Bits {

    //pegar do char c o bit na posição b
    public static char pegabit(char c, char b) {
        return (((c & (1 << b)) == 0) ? (char) 0 : (char) 1);
    }

    public static char xpegabit(char c, char b) {
        char pos = inverte(b);

        // System.out.println("pos vale ->" + (int)pos);
        return pegabit(c, pos);
    }
    //             3        byte          0 ou 1
    //mudar do bit b do char c para o valor v

    public static char mudabit(char c, char b, char v) {
        char pos = inverte(b);
        return ((char) ((v == 1) ? c | (1 << pos) : c & (~(1 << pos))));
    }

    private static char inverte(char b) {
        char pos = b;
        switch ((int) b) {
            case 0:
                pos = 7;
                break;
            case 1:
                pos = 6;
                break;
            case 2:
                pos = 5;
                break;
            case 3:
                pos = 4;
                break;
            case 4:
                pos = 3;
                break;
            case 5:
                pos = 2;
                break;
            case 6:
                pos = 1;
                break;
            case 7:
                pos = 0;
                break;

        }
        return pos;
    }
    //ardPasta se eh arquivo ou pasta      novaPermissao
    public static char seguranca( int arqPasta, int permissao) {
        if( permissao < 0 || permissao >77){ 
            System.out.println("Permissao invalida");
            return 0;
        }
        byte xSeguranca;
        if (arqPasta == 0) xSeguranca = (byte) 00000000;
        else xSeguranca = (byte) 10000000;
        
        char[] param = String.valueOf(permissao).toCharArray();
        int grupo = (int) Integer.valueOf( String.valueOf(param[0]));
        int outros = (int) Integer.valueOf( String.valueOf(param[1]));
        switch (grupo) {
            case 0:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 0 );
                break;
            case 1:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 1 );
                break;
            case 2:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 0 );
                break;
            case 3:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 1 );
                break;
            case 4:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 0 );
                break;
            case 5:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 1 );
                break;
            case 6:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 0 );
                break;
            case 7:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 1 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 2 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 3 , (char) 1 );
                break;
        }
        switch (outros) {
            case 0:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 0 );
                break;
            case 1:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 1 );
                break;
            case 2:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 0 );
                break;
            case 3:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 1 );
                break;
            case 4:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 0 );
                break;
            case 5:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 0 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 1 );
                break;
            case 6:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 0 );
                break;
            case 7:
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 4 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 5 , (char) 1 );
                xSeguranca = (byte) mudabit( (char) xSeguranca, (char) 6 , (char) 1 );
                break;
        }
        
        return (char) xSeguranca;
    }
    
    
    
}
