/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Usuario
 */
public class Arquivo {
    private byte[] dados = new byte[16];
    private short bloco;

    public Arquivo(File hd, long adress) throws IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "r");
        bloco.seek(adress);
        bloco.read(dados);
        bloco.close();
    }

    public byte[] getDados() {
        return dados;
    }

    public short getBloco() {
        return bloco;
    }
   void jair(){
       System.out.println("");
   }
}
