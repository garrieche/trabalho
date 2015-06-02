/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import static hd.Bits.mudabit;
import static hd.Bits.pegabit;
import static hd.Bits.xpegabit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.AccessDeniedException;

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
        
        
        char  tmp = (char) ~(127);
        vHD[0] = (byte)tmp;
        for (int i = 1; i < 128; i++) {
            vHD[i] = 0;
        }
        for (int i = 128; i < 128+18; i++) {
            vHD[i] = 7;
        }
        for (int i = 128+18; i < this.tamHD; i++) {
            this.vHD[i] = 15;
        }
        
        FileWriter formatar = new FileWriter(this.hd);
        for (int i = 0; i < this.tamHD; i++) {
            formatar.write(vHD[i]);
            System.out.println("vHD linha " + i + " -> " + (int)vHD[i]);
        }
        formatar.flush();
        formatar.close();
    }

    short getBlocoVazio() throws FileNotFoundException, IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        for (int i = 0; i < 128; i++) {
            short bitsAnteriores = (short)((i)*8);
            arq.seek(i);
            char b = (char)arq.readUnsignedByte();
            System.out.println("Byte Lido.........: " + arq.getFilePointer());
            System.out.println("Valor do Byte.....: " + (int)b) ;
            System.out.println("Binario do Byte...: " + xBinario(b));
            System.out.println("===============================================");
            
            for (char j = 0; j < 7; j++) {
                if ( (int)xpegabit(b , j)== 0 )
                   return (short) ( bitsAnteriores + (j));
            }
         
        }
        arq.close();
        return 0;
    }

//    void setBlocoUsado(int bloco) throws IOException {
//        RandomAccessFile arq = new RandomAccessFile(hd, "r");
//        int numByte = (int) bloco / 8;
//        int numBitnoByte = bloco % 8;
//        arq.seek(numByte);
//        char b = arq.readChar();
//        mudabit((char) numBitnoByte, b, (char) 1);
//    }
//    void setBlocoLivre(int bloco) throws IOException {
//        RandomAccessFile arq = new RandomAccessFile(hd, "r");
//        int numByte = (int) bloco / 8;
//        int numBitnoByte = bloco % 8;
//        arq.seek(numByte);
//        char b = arq.readChar();
//        mudabit((char) numBitnoByte, b, (char) 0);
//    }
//
//    void gravarBloco(int bloco, char dados[]) throws IOException {
//        
//      //  this.setarBlocoUsado(bloco);
//        RandomAccessFile arq = new RandomAccessFile(hd, "r");
//        int numByte = (int) bloco / 8;
//        int numBitnoByte = bloco % 8;
//        arq.seek((bloco*18)+128); //dessa forma chega no byte apropriado
//        arq.write(dados); //grava os 16 chars        
//    }

    void MostraHD() throws FileNotFoundException, IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        arq.seek(0);
        for (long i = 0; i < arq.length(); i++) {
            int b = arq.readUnsignedByte();
            System.out.println("Byte Nr:" + i + " -> " + b + " bin ->" + xBinario((char) b));
        }
        arq.close();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    static String xBinario( char valor ){
        String ret = "";
        for (int i = 0; i < 8; i++){
            ret = String.valueOf((int)pegabit(valor , (char)i)) + ret ;
        }
        return (ret);
    }

   
}
