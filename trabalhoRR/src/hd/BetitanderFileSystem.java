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

    public void formatar() throws IOException {
        for (int i = 1; i < 128; i++) {
            vHD[i] = 0;
        }
        for (int i = 129; i < this.tamHD; i++) {
            this.vHD[i] = 15;
        }
        vHD[0] = (byte) 10000000;
        for (int i = 128; i < 128 + 18; i++) {
            this.vHD[i] = folder()[i - 128];
        }
        RandomAccessFile formatar = new RandomAccessFile(hd, "rw");
        for (int i = 0; i < this.tamHD; i++) {
            formatar.writeByte(vHD[i]);
        }
        formatar.close();
    }

    public void MostraHD() throws FileNotFoundException, IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        arq.seek(0);
        for (long i = 0; i < arq.length(); i++) {
            int b = arq.readUnsignedByte();
            System.out.println("Byte Nr:" + i + " -> " + b + " bin ->" + xBinario((char) b));
        }
        arq.close();
        //isso eh o motivo de um erro que dava
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean criaPasta(String caminho) throws IOException {
        int blocoVazio;
        int pastaOndeCriaNova;
        byte[] umBloco = new byte[18];
        byte[] umaPasta = folder();
        blocoVazio = this.getBlocoVazio();
        String nomePasta = "";
        
        
        for (int i = 0; i < caminho.length(); i++) {
            if (caminho.charAt(i) == '/' || i == caminho.length()) {
                
                nomePasta = "";
            } else {
                nomePasta = nomePasta + caminho.charAt(i);
            }
        }
        
        int tmp = Integer.valueOf(nomePasta);
        
        String novo = caminho.substring(nomePasta.length());
        
        //tratar o nome do caminho

        pastaOndeCriaNova = this.exist(nomePasta);
        System.out.println("vou criar a pasta nos seguintes parametros:");
        System.out.println("Bloco Vazio -> " + blocoVazio);
        System.out.println("nome da pasta em string -> " + novo);
        System.out.println("pastaOndeCriaNova (retorno do existe -> " + pastaOndeCriaNova);
        System.out.println("nome da pasta em String -> " + nomePasta + "nome INT -> " + tmp + "nome em bytes -> " + ((byte) tmp));
//        if (pastaOndeCriaNova != 0) {
//            this.setBlocoUsado(blocoVazio);
//            RandomAccessFile path = new RandomAccessFile(hd, "r");
//            path.seek(pastaOndeCriaNova);
//            path.read(umBloco);
//
//            //tratar posicao na pasta
//            //tratar gravar nome
//            umBloco[0] = (byte) blocoVazio;
//            
//            umBloco[3] = (byte) tmp;
//            path.seek(blocoVazio);
//            path.write(umaPasta);
//        }
//        //onde?
//
        return true;
    }

    public boolean criaArquivo(String caminho, byte[] binario) {
        return true;
    }

    public boolean apagaPasta(String caminho) {
        // apenas se a pasta estiver vazia
        return true;
    }

    public boolean apagaArquivo(String caminho) {
        return true;
    }

    private static String xBinario(char valor) {
        String ret = "";
        for (int i = 0; i < 8; i++) {
            ret = String.valueOf((int) pegabit(valor, (char) i)) + ret;
        }
        return (ret);
    }

    private short getBlocoVazio() throws FileNotFoundException, IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        for (int i = 0; i < 128; i++) {
            short bitsAnteriores = (short) ((i) * 8);
            arq.seek(i);
            char b = (char) arq.readUnsignedByte();
            for (char j = 0; j < 7; j++) {
                if ((int) xpegabit(b, j) == 0) {
                    return (short) (bitsAnteriores + (j));
                }
            }
        }
        arq.close();
        return 0;
    }

    private static byte[] folder() {
        byte[] folder = new byte[18];

        // =====================================================
        // Arquivo 1
        // =====================================================    
        folder[0] = 0;                   //  2 bytes
        folder[1] = 0;                   //  para bloco inicio  (0 = vazio ) 
        folder[2] = (byte) 10000000;     //  1 Byte para Arquivo / Pasta e Seguranca
        folder[3] = 0;                   //  1 byte para nome do Arquivo

        // =====================================================
        // Arquivo 2
        // =====================================================      
        folder[4] = 0;
        folder[5] = 0;
        folder[6] = (byte) 10000000;
        folder[7] = 0;

        // =====================================================
        // Arquivo 3
        // =====================================================      
        folder[8] = 0;
        folder[9] = 0;
        folder[10] = (byte) 10000000;
        folder[11] = 0;

        // =====================================================
        // Arquivo 4
        // =====================================================  
        folder[12] = 0;
        folder[13] = 0;
        folder[14] = (byte) 10000000;
        folder[15] = 0;

        // =====================================================
        // Continuação da pasta em outros blocos
        // =====================================================      
        folder[16] = 0;
        folder[17] = 0;

        return folder;
    }

    public short exist(String caminho) throws FileNotFoundException, IOException {

        if (caminho == null)  return 0;
        if (caminho.length() == 1 && caminho.charAt(0) != '/') return 0;
        if (caminho.length() == 1 && caminho.charAt(0) == '/') return 128;
        
        String tempPath = "";
        Pasta umaPasta = new Pasta (  hd , 128);
        boolean navegou = false;
        
        for (int i = 0; i < caminho.length(); i++) {
            if (caminho.charAt(i) == '/' ) {
                if (tempPath != ""){
                    navegou = umaPasta.existeSubPasta(tempPath);
                    if (navegou){
                        int proxAdress = umaPasta.getFileCounter( tempPath );
                        umaPasta = new Pasta( hd , proxAdress);
                    }
                }
                tempPath = "";
            } else {
                tempPath = tempPath + caminho.charAt(i);
            }
            navegou = umaPasta.existeSubPasta(tempPath);
            if (navegou) return (short) umaPasta.getFileCounter( tempPath );
        }
        return -1;
    }

    void setBlocoUsado(int bloco) throws IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        int numByte = (int) bloco / 8;
        int numBitnoByte = bloco % 8;
        arq.seek(numByte);
        char b = arq.readChar();
        mudabit((char) numBitnoByte, b, (char) 1);
    }
}

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
