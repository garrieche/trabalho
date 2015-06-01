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
	public static char pegabit(char c, char b)
	{	return(((c&(1<<b)) == 0)?(char)0:(char)1);
	}
        //             3        byte          0 ou 1
	//mudar do bit b do char c para o valor v
	public static char mudabit(char c, char b, char v)
	{	return((char)((v == 1)?c|(1<<b):c&(~(1<<b))));
	}
}

