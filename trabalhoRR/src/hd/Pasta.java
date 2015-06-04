/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Jair
 */
public class Pasta {
    private short blocoPrimeiroArquivo;
    private byte  segurancaPrimeiroArquivo;
    private byte  nomePrimeiroArquivo;
    
    private short blocoSegundoArquivo;
    private byte  segurancaSegundoArquivo;
    private byte  nomeSegundoArquivo;
    
    private short blocoTerceiroArquivo;
    private byte  segurancaTerceiroArquivo;
    private byte  nomeTerceiroArquivo;
    
    private short blocoQuartoArquivo;
    private byte  segurancaQuartoArquivo;
    private byte  nomeQuartoArquivo;
    
    private short proxBloco;
    private long byteFinal;

    public Pasta( File hd , long endereco ) throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "r");
        bloco.seek(endereco);
        
        this.blocoPrimeiroArquivo = (short) bloco.readUnsignedShort();
        this.segurancaPrimeiroArquivo = bloco.readByte();
        this.nomePrimeiroArquivo = bloco.readByte();
        
        this.blocoSegundoArquivo = (short) bloco.readUnsignedShort();
        this.segurancaSegundoArquivo = bloco.readByte();
        this.nomeSegundoArquivo = bloco.readByte();
        
        this.blocoTerceiroArquivo = (short) bloco.readUnsignedShort();
        this.segurancaTerceiroArquivo = bloco.readByte();
        this.nomeTerceiroArquivo = bloco.readByte();
        
        this.blocoQuartoArquivo = (short) bloco.readUnsignedShort();
        this.segurancaQuartoArquivo = bloco.readByte();
        this.nomeQuartoArquivo = bloco.readByte();
        
        this.proxBloco = (short) bloco.readUnsignedShort();
        byteFinal = bloco.getFilePointer();
        bloco.close();
        
        System.out.println("Criado objeto pasta:");
        System.out.println("---------------------------");
        System.out.println("Byte Inicial..: " + endereco);
        System.out.println("Byte Final....: " + byteFinal);
        System.out.println("Bytes Lidos...: " + (byteFinal - endereco) + "byte(s).");
    }

    boolean existeSubPasta(String tempPath) {
        int pasta = Integer.parseInt(tempPath);
        byte p = (byte)pasta;
        if (p == getNomePrimeiroArquivo()) return true;
        if (p == getNomeSegundoArquivo()) return true;
        if (p == getNomeTerceiroArquivo()) return true;
        if (p == getNomeQuartoArquivo()) return true;
        
        return false;
        
    }

    int getFileCounter(String tempPath) {
        return (int)this.byteFinal;
    }

    public byte getNomePrimeiroArquivo() {
        return nomePrimeiroArquivo;
    }

    public byte getNomeSegundoArquivo() {
        return nomeSegundoArquivo;
    }

    public byte getNomeTerceiroArquivo() {
        return nomeTerceiroArquivo;
    }

    public byte getNomeQuartoArquivo() {
        return nomeQuartoArquivo;
    }
    
    
    
    
    
}
