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
}
