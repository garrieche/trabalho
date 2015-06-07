package hd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Pasta {

    public short getProxBloco() {
        return proxBloco;
    }

    private short blocoPrimeiroArquivo;
    private byte segurancaPrimeiroArquivo;
    private byte nomePrimeiroArquivo;

    private short blocoSegundoArquivo;
    private byte segurancaSegundoArquivo;
    private byte nomeSegundoArquivo;

    private short blocoTerceiroArquivo;
    private byte segurancaTerceiroArquivo;
    private byte nomeTerceiroArquivo;

    private short blocoQuartoArquivo;
    private byte segurancaQuartoArquivo;
    private byte nomeQuartoArquivo;

    private short proxBloco;
    private long byteFinal;
    private long byteInicial;
    private File hd;

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

    public Pasta(File hd, long endereco) throws FileNotFoundException, IOException {
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
        this.byteFinal = bloco.getFilePointer();
        this.byteInicial = endereco;
        this.hd = hd;
        bloco.close();

    }

    public boolean existeSubPasta(String tempPath) throws FileNotFoundException, IOException {
        if (tempPath.equals("")) {
            return false;
        }
        int pasta = (int) Integer.valueOf(tempPath);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0) {
            return true;
        }
        if (p == getNomeSegundoArquivo() && getSegurancaSegundoArquivo() == 0) {
            return true;
        }
        if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0) {
            return true;
        }
        if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
            return true;
        }

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
            if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0) {
                return true;
            }
            if (p == getNomeSegundoArquivo() && getSegurancaSegundoArquivo() == 0) {
                return true;
            }
            if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0) {
                return true;
            }
            if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
                return true;
            }
        }

        return false;

    }

    public int getFileCounter(String tempPath) {
        int pasta = Integer.parseInt(tempPath);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo()) {
            return this.blocoPrimeiroArquivo;
        }
        if (p == getNomeSegundoArquivo()) {
            return this.blocoSegundoArquivo;
        }
        if (p == getNomeTerceiroArquivo()) {
            return this.blocoTerceiroArquivo;
        }
        if (p == getNomeQuartoArquivo()) {
            return this.blocoQuartoArquivo;
        }
        return (int) this.byteFinal;
    }

    void gravaNovaPasta(short blocoVazio, byte[] novaPasta, String nome) throws FileNotFoundException, IOException {
        boolean terminei = false;

        if (this.blocoPrimeiroArquivo == 0) {
            this.blocoPrimeiroArquivo = blocoVazio;
            this.nomePrimeiroArquivo = (byte) (int) Integer.valueOf(nome);
            this.segurancaPrimeiroArquivo = 0;
            terminei = true;
        } else {
            if (this.blocoSegundoArquivo == 0) {
                this.blocoSegundoArquivo = blocoVazio;
                this.nomeSegundoArquivo = (byte) (int) Integer.valueOf(nome);
                this.segurancaSegundoArquivo = 0;
                terminei = true;
            } else {
                if (this.blocoTerceiroArquivo == 0) {
                    this.blocoTerceiroArquivo = blocoVazio;
                    this.nomeTerceiroArquivo = (byte) (int) Integer.valueOf(nome);
                    this.segurancaTerceiroArquivo = 0;
                    terminei = true;
                } else {
                    if (this.blocoQuartoArquivo == 0) {
                        this.blocoQuartoArquivo = blocoVazio;
                        this.nomeQuartoArquivo = (byte) (int) Integer.valueOf(nome);
                        this.segurancaQuartoArquivo = 0;
                        terminei = true;
                    }
                }
            }
        }
        if (terminei) {
            atualizaBytesDaPasta();
            gravaBinarioNovaPasta(blocoVazio, novaPasta);
            return;
        }
        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if (this.blocoPrimeiroArquivo == 0) {
                this.blocoPrimeiroArquivo = blocoVazio;
                this.nomePrimeiroArquivo = (byte) (int) Integer.valueOf(nome);
                this.segurancaPrimeiroArquivo = 0;
                terminei = true;
            } else {
                if (this.blocoSegundoArquivo == 0) {
                    this.blocoSegundoArquivo = blocoVazio;
                    this.nomeSegundoArquivo = (byte) (int) Integer.valueOf(nome);
                    this.segurancaSegundoArquivo = 0;
                    terminei = true;
                } else {
                    if (this.blocoTerceiroArquivo == 0) {
                        this.blocoTerceiroArquivo = blocoVazio;
                        this.nomeTerceiroArquivo = (byte) (int) Integer.valueOf(nome);
                        this.segurancaTerceiroArquivo = 0;
                        terminei = true;
                    } else {
                        if (this.blocoQuartoArquivo == 0) {
                            this.blocoQuartoArquivo = blocoVazio;
                            this.nomeQuartoArquivo = (byte) (int) Integer.valueOf(nome);
                            this.segurancaQuartoArquivo = 0;
                            terminei = true;
                        }
                    }
                }
            }
            if (terminei) {
                atualizaBytesDaPasta();
                gravaBinarioNovaPasta(blocoVazio, novaPasta);
                return;
            }
        }
    }

    public boolean estaCheia() {

        return ((this.blocoPrimeiroArquivo != 0)
                && (this.blocoSegundoArquivo != 0)
                && (this.blocoTerceiroArquivo != 0)
                && (this.blocoQuartoArquivo != 0));
    }

    public void expande(short adress, byte[] folder) throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "rw");
        this.proxBloco = adress;
        bloco.seek(this.byteInicial + 16);
        bloco.writeShort(this.proxBloco);
        bloco.seek(adress);
        bloco.write(folder);

        bloco.close();
    }

    public void leMaisBlocoDaPasta() throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "r");
        bloco.seek(this.proxBloco);
        this.byteInicial = this.proxBloco;
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
        this.byteFinal = bloco.getFilePointer();
        bloco.close();
    }

    private void atualizaBytesDaPasta() throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "rw");
        bloco.seek(this.byteInicial);
        bloco.writeShort(this.blocoPrimeiroArquivo);
        bloco.writeByte(this.segurancaPrimeiroArquivo);
        bloco.writeByte(this.nomePrimeiroArquivo);
        bloco.writeShort(this.blocoSegundoArquivo);
        bloco.writeByte(this.segurancaSegundoArquivo);
        bloco.writeByte(this.nomeSegundoArquivo);
        bloco.writeShort(this.blocoTerceiroArquivo);
        bloco.writeByte(this.segurancaTerceiroArquivo);
        bloco.writeByte(this.nomeTerceiroArquivo);
        bloco.writeShort(this.blocoQuartoArquivo);
        bloco.writeByte(this.segurancaQuartoArquivo);
        bloco.writeByte(this.nomeQuartoArquivo);
        bloco.close();
    }

    private void gravaBinarioNovaPasta(short blocoVazio, byte[] novaPasta) throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "rw");
        bloco.seek(blocoVazio);
        bloco.write(novaPasta);
        bloco.close();
    }

    public short getBlocoPasta(String tempPath) throws FileNotFoundException, IOException {
        if (tempPath.equals("")) {
            return 0;
        }
        int pasta = (int) Integer.valueOf(tempPath);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0) {
            return this.blocoPrimeiroArquivo;
        }
        if (p == getNomeSegundoArquivo() && getSegurancaSegundoArquivo() == 0) {
            return this.blocoSegundoArquivo;
        }
        if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0) {
            return this.blocoTerceiroArquivo;
        }
        if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
            return this.blocoQuartoArquivo;
        }

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
            if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0) {
                return this.blocoPrimeiroArquivo;
            }
            if (p == getNomeSegundoArquivo() && getSegurancaSegundoArquivo() == 0) {
                return this.blocoSegundoArquivo;
            }
            if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0) {
                return this.blocoTerceiroArquivo;
            }
            if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
                return this.blocoQuartoArquivo;
            }
        }

        return 0;

    }

    boolean apagaSubPasta(String splitado) throws IOException {
        //if (this.existeSubPasta(splitado)) {
        //se sim, existe apaga
        boolean terminei = false;

        int pasta = (int) Integer.valueOf(splitado);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0) {
            this.blocoPrimeiroArquivo = 0;
            this.nomePrimeiroArquivo = 0;
            this.segurancaPrimeiroArquivo = (byte) 10000000;
            terminei = true;
        }
        if (p == getNomeSegundoArquivo() && getSegurancaSegundoArquivo() == 0) {
            this.blocoSegundoArquivo = 0;
            this.nomeSegundoArquivo = 0;
            this.segurancaSegundoArquivo = (byte) 10000000;
            terminei = true;
        }
        if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0) {
            this.blocoTerceiroArquivo = 0;
            this.nomeTerceiroArquivo = 0;
            this.segurancaTerceiroArquivo = (byte) 10000000;
            terminei = true;
        }
        if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
            this.blocoQuartoArquivo = 0;
            this.nomeQuartoArquivo = 0;
            this.segurancaQuartoArquivo = (byte) 10000000;
            terminei = true;
        }
        if (terminei) {
            atualizaBytesDaPasta();
            return true;
        }

        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();

            if (p == getNomePrimeiroArquivo() && getSegurancaPrimeiroArquivo() == 0) {
                this.blocoPrimeiroArquivo = 0;
                this.nomePrimeiroArquivo = 0;
                this.segurancaPrimeiroArquivo = (byte) 10000000;
                terminei = true;
            }
            if (p == getNomeSegundoArquivo() && getSegurancaSegundoArquivo() == 0) {
                this.blocoSegundoArquivo = 0;
                this.nomeSegundoArquivo = 0;
                this.segurancaSegundoArquivo = (byte) 10000000;
                terminei = true;
            }
            if (p == getNomeTerceiroArquivo() && getSegurancaTerceiroArquivo() == 0) {
                this.blocoTerceiroArquivo = 0;
                this.nomeTerceiroArquivo = 0;
                this.segurancaTerceiroArquivo = (byte) 10000000;
                terminei = true;
            }
            if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
                this.blocoQuartoArquivo = 0;
                this.nomeQuartoArquivo = 0;
                this.segurancaQuartoArquivo = (byte) 10000000;
                terminei = true;
            }
            if (terminei) {
                atualizaBytesDaPasta();
                return true;
            }
        }
        //} //chave do if
        System.out.println("Incrivel nao achei esta pasta");
        return false; //caso nao apague
    }

    boolean estaVazia() throws IOException {
        boolean vazia = true;

        byte p = (byte) 0;
        if (p < blocoPrimeiroArquivo && getSegurancaPrimeiroArquivo() == 0) {
            vazia = false;
            return vazia;
        } else {
            if (p < blocoSegundoArquivo && getSegurancaSegundoArquivo() == 0) {
                vazia = false;
                return vazia;
            } else {
                if (p < blocoTerceiroArquivo && getSegurancaTerceiroArquivo() == 0) {
                    vazia = false;
                    return vazia;
                } else {
                    if (p < blocoQuartoArquivo && getSegurancaQuartoArquivo() == 0) {
                        vazia = false;
                        return vazia;
                    }
                }
            }
        }
        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();

            if (p < blocoPrimeiroArquivo && getSegurancaPrimeiroArquivo() == 0) {
                vazia = false;
                return vazia;
            } else {
                if (p < blocoSegundoArquivo && getSegurancaSegundoArquivo() == 0) {
                    vazia = false;
                    return vazia;
                } else {
                    if (p < blocoTerceiroArquivo && getSegurancaTerceiroArquivo() == 0) {
                        vazia = false;
                        return vazia;
                    } else {
                        if (p < blocoQuartoArquivo && getSegurancaQuartoArquivo() == 0) {
                            vazia = false;
                            return vazia;
                        }
                    }
                }
            }
        }
        return true;
    }
}
