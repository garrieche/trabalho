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
    }

    public boolean criaPasta(String caminho) throws IOException {
        short blocoVazio;
        
        String caminhoFinal=""; 
        
        if ( (exist(caminho) == 0) || (exist(caminho) == 128) ){
            System.out.println("Arquivo ou Pasta já existente.");
            return false ;
        }
        else
        {
            String splitado[] = caminho.split("/");
            for (int i = 1; i < splitado.length-1; i++)
                caminhoFinal += ("/" + splitado[i]) ;
            
            if (caminhoFinal.equals("")) caminhoFinal = "/";
            //quando estou tentando criar a subpasta preciso receber aki o endereço 
            //que vai criar e o caminho final esta incorreto
            if (exist(caminhoFinal) == 0) return false;
            temEspacoNaPasta(caminhoFinal);
            byte[] novaPasta = folder();
            Pasta pastaExistente = new Pasta( hd, exist(caminhoFinal));
            blocoVazio = getBlocoVazio();
            if (blocoVazio > 0){
                pastaExistente.gravaNovaPasta( blocoVazio, novaPasta, splitado[splitado.length-1] );
            } else {
                System.out.println("Acabou o espaço em disco!!!! ");
                return false;
            }
        }
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

     static String xBinario(char valor) {
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
                   setBlocoUsado( bitsAnteriores + (j));
                   return (short) (128 + (18 * (bitsAnteriores + (j))) );
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
        Pasta pastaRaiz = new Pasta (  hd , 128);
        boolean navegou = false;
        short retorno = 0;
        for (int i = 0; i < caminho.length(); i++) {
            if (caminho.charAt(i) == '/' ) {
                //teste se a string esta em /
                System.out.println("Caminho caractere do for -> " + caminho.charAt(i));
                if (!"".equals(tempPath)){
                    System.out.println("SubPasta001");
                    navegou = pastaRaiz.existeSubPasta(tempPath);
                    if (navegou){
                        int proxAdress = pastaRaiz.getFileCounter( tempPath );
                        pastaRaiz = new Pasta( hd , proxAdress);
                    }
                }
                tempPath = "";
            } else {
                tempPath = tempPath + caminho.charAt(i);
            }
            
            navegou = pastaRaiz.existeSubPasta(tempPath);
            //esse que retorna o endereço da sub pasta???
            if (navegou) retorno = (short) pastaRaiz.getFileCounter( tempPath );
            System.out.println("o retorno da função exit é -> " + retorno);
        }
        return retorno;
    }

    private void setBlocoUsado(int bloco) throws IOException {
        char novoBit;
        long pointer;
        char b;
        char a;
        RandomAccessFile arq = new RandomAccessFile(hd, "rw");
        pointer = ((bloco/8));
        arq.seek(pointer);
        byte leitura;
        leitura = arq.readByte();
        b = (char)leitura;
        a = (char) bloco;
        novoBit = mudabit(b, a, (char) 1);
        arq.seek(pointer);
        arq.writeByte(novoBit);
        
    }

    private void temEspacoNaPasta(String caminhoFinal) throws IOException {
        short bytePasta = exist(caminhoFinal);
        Pasta minhaPasta = new Pasta( hd, bytePasta);
        if( minhaPasta.estaCheia() ){
            short blocoExtendePasta = getBlocoVazio();
            minhaPasta.expande( blocoExtendePasta, folder());
        }     
    }


}

