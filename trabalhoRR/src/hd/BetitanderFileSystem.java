/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import static hd.Bits.mudabit;
import static hd.Bits.pegabit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Jair
 */
public class BetitanderFileSystem {

    private File hd;
    private int tamHD;
    private byte[] vHD;

    public BetitanderFileSystem() throws IOException {
        this.tamHD = (1024 * 18) + 128;
        this.vHD = new byte[this.tamHD];
        this.hd = new File("c:\\betitander.hd");
        if (!hd.exists()) {
            hd.createNewFile();
            formatar();
        }
    }

    void formatar() throws IOException {
        // Vou preparar em Memória os dados 
        for (int i = 0; i < 128; i++) {
            vHD[i] = 0;
        }
        vHD[0] = (byte) 10000000;
        for (int i = 128; i < this.tamHD; i++) {
            this.vHD[i] = 15;
        }
        System.out.println(vHD[0]);
        // Criar a pasta raiz byte 128

        FileWriter formatar = new FileWriter(this.hd);
        for (int i = 0; i < this.tamHD; i++) {
            formatar.write(vHD[i]);
        }
        formatar.flush();
    }

    int getBlocoVazio() throws FileNotFoundException, IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        for (int i = 0; i < 128; i++) {
            arq.seek(i);
            char b = arq.readChar();
            for (int j = 0; j < 8; j++) {
                if (pegabit(b, (char) j) == '0') {
                    return ((i * 8) + j); //numero real do bloco do BLOCO não do byte
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    void setarBlocoUsado(int bloco) throws IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        int numByte = (int) bloco / 8;
        int numBitnoByte = bloco % 8;
        arq.seek(numByte);
        char b = arq.readChar();
        mudabit((char) numBitnoByte, b, (char) 1);
    }

    void setBlocoLivre(int bloco) throws IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        int numByte = (int) bloco / 8;
        int numBitnoByte = bloco % 8;
        arq.seek(numByte);
        char b = arq.readChar();
        mudabit((char) numBitnoByte, b, (char) 0);
    }

    void gravarBloco(int bloco, char dados[]) throws IOException {
        
        this.setarBlocoUsado(bloco);
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        int numByte = (int) bloco / 8;
        int numBitnoByte = bloco % 8;
        arq.seek((bloco*18)+128); //dessa forma chega no byte apropriado
        arq.write(dados); //grava os 16 chars        
    }
}
