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
    
    private File hd;

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
        this.hd = hd;
        bloco.close();
        
        System.out.println("Criado objeto pasta:");
        System.out.println("---------------------------");
        System.out.println("Byte Inicial..: " + endereco);
        System.out.println("Byte Final....: " + byteFinal);
        System.out.println("Bytes Lidos...: " + (byteFinal - endereco) + "byte(s).");
    }

    public boolean existeSubPasta(String tempPath) throws FileNotFoundException, IOException {
        int pasta = Integer.parseInt(tempPath);
        byte p = (byte)pasta;
        if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0 ) return true;
        if (p == getNomeSegundoArquivo()  && getSegurancaSegundoArquivo()  == 0 ) return true;
        if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0 ) return true;
        if (p == getNomeQuartoArquivo()   && getSegurancaQuartoArquivo()   == 0 ) return true;
        
        while (this.proxBloco != 0) {
            RandomAccessFile bloco = new RandomAccessFile(hd, "r");
            bloco.seek(this.proxBloco);
        
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
            if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0 ) return true;
            if (p == getNomeSegundoArquivo()  && getSegurancaSegundoArquivo()  == 0 ) return true;
            if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0 ) return true;
            if (p == getNomeQuartoArquivo()   && getSegurancaQuartoArquivo()   == 0 ) return true;
        }
        
        return false;
        
    }

    public int getFileCounter(String tempPath) {
        int pasta = Integer.parseInt(tempPath);
        byte p = (byte)pasta;
        if (p == getNomePrimeiroArquivo()) return this.blocoPrimeiroArquivo;
        if (p == getNomeSegundoArquivo()) return this.blocoSegundoArquivo;
        if (p == getNomeTerceiroArquivo()) return this.blocoTerceiroArquivo;
        if (p == getNomeQuartoArquivo()) return this.blocoQuartoArquivo;
        return (int)this.byteFinal;
    }

    private byte getNomePrimeiroArquivo() {
        return nomePrimeiroArquivo;
    }

    private byte getNomeSegundoArquivo() {
        return nomeSegundoArquivo;
    }

    private byte getNomeTerceiroArquivo() {
        return nomeTerceiroArquivo;
    }

    private byte getNomeQuartoArquivo() {
        return nomeQuartoArquivo;
    }

    private byte getSegurancaPrimeiroArquivo() {
        return segurancaPrimeiroArquivo;
    }

    private byte getSegurancaSegundoArquivo() {
        return segurancaSegundoArquivo;
    }

    private byte getSegurancaTerceiroArquivo() {
        return segurancaTerceiroArquivo;
    }

    private byte getSegurancaQuartoArquivo() {
        return segurancaQuartoArquivo;
    }
    
   
}
