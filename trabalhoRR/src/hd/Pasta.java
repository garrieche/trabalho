package hd;

import static hd.BetitanderFileSystem.xBinario;
import static hd.Bits.pegabit;
import static hd.Bits.seguranca;
import static hd.Bits.xpegabit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Pasta {

    private short blocoPrimeiroArquivo;
    private byte segurancaPrimeiroArquivo;
    private byte nomePrimeiroArquivo;
    private byte nomeDonoPrimeiroArquivo;

    private short blocoSegundoArquivo;
    private byte segurancaSegundoArquivo;
    private byte nomeSegundoArquivo;
    private byte nomeDonoSegundoArquivo;

    private short blocoTerceiroArquivo;
    private byte segurancaTerceiroArquivo;
    private byte nomeTerceiroArquivo;
    private byte nomeDonoTerceiroArquivo;

    private byte filling;

   
    
    private short proxBloco;
    private long byteFinal;
    private long byteInicial;
    private File hd;
    
    private final int ARQUIVO = 1 ;
    private final int PASTA = 0;

    public short getProxBloco() {
        return proxBloco;
    }

    public Pasta(File hd, long endereco) throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "r");
        bloco.seek(endereco);

        this.blocoPrimeiroArquivo = (short) bloco.readUnsignedShort();
        this.segurancaPrimeiroArquivo = bloco.readByte();
        this.nomePrimeiroArquivo = bloco.readByte();
        this.nomeDonoPrimeiroArquivo = bloco.readByte();

        this.blocoSegundoArquivo = (short) bloco.readUnsignedShort();
        this.segurancaSegundoArquivo = bloco.readByte();
        this.nomeSegundoArquivo = bloco.readByte();
        this.nomeDonoSegundoArquivo = bloco.readByte();

        this.blocoTerceiroArquivo = (short) bloco.readUnsignedShort();
        this.segurancaTerceiroArquivo = bloco.readByte();
        this.nomeTerceiroArquivo = bloco.readByte();
        this.nomeDonoTerceiroArquivo = bloco.readByte();

        this.filling = bloco.readByte();
        this.proxBloco = (short) bloco.readUnsignedShort();
        this.byteFinal = bloco.getFilePointer();
        this.byteInicial = endereco;
        this.hd = hd;
        bloco.close();

    }

    public void gravaNovaPasta(short blocoVazio, byte[] novaPasta, String nome, byte dono) throws FileNotFoundException, IOException {
        boolean terminei = false;
        byte xSeguranca = (byte) seguranca( this.PASTA, 64);
        if (this.blocoPrimeiroArquivo == 0) {
            this.blocoPrimeiroArquivo = blocoVazio;
            this.nomePrimeiroArquivo = (byte) (int) Integer.valueOf(nome);
            this.segurancaPrimeiroArquivo = xSeguranca;
            this.nomeDonoPrimeiroArquivo = dono;
            terminei = true;
        } else {
            if (this.blocoSegundoArquivo == 0) {
                this.blocoSegundoArquivo = blocoVazio;
                this.nomeSegundoArquivo = (byte) (int) Integer.valueOf(nome);
                this.segurancaSegundoArquivo = xSeguranca;
                this.nomeDonoSegundoArquivo = dono;
                terminei = true;
            } else {
                if (this.blocoTerceiroArquivo == 0) {
                    this.blocoTerceiroArquivo = blocoVazio;
                    this.nomeTerceiroArquivo = (byte) (int) Integer.valueOf(nome);
                    this.segurancaTerceiroArquivo = xSeguranca;
                    this.nomeDonoTerceiroArquivo = dono;
                    terminei = true;
                } else {
//                    if (this.blocoQuartoArquivo == 0) {
//                        this.blocoQuartoArquivo = blocoVazio;
//                        this.nomeQuartoArquivo = (byte) (int) Integer.valueOf(nome);
//                        this.segurancaQuartoArquivo = 0;
//                        terminei = true;
//                    }
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
                this.segurancaPrimeiroArquivo = xSeguranca;
                this.nomeDonoPrimeiroArquivo = dono;
                terminei = true;
            } else {
                if (this.blocoSegundoArquivo == 0) {
                    this.blocoSegundoArquivo = blocoVazio;
                    this.nomeSegundoArquivo = (byte) (int) Integer.valueOf(nome);
                    this.segurancaSegundoArquivo = xSeguranca;
                    this.nomeDonoSegundoArquivo = dono;
                    terminei = true;
                } else {
                    if (this.blocoTerceiroArquivo == 0) {
                        this.blocoTerceiroArquivo = blocoVazio;
                        this.nomeTerceiroArquivo = (byte) (int) Integer.valueOf(nome);
                        this.segurancaTerceiroArquivo = xSeguranca;
                        this.nomeDonoTerceiroArquivo = dono;
                        terminei = true;
                    } else {
//                        if (this.blocoQuartoArquivo == 0) {
//                            this.blocoQuartoArquivo = blocoVazio;
//                            this.nomeQuartoArquivo = (byte) (int) Integer.valueOf(nome);
//                            this.segurancaQuartoArquivo = 0;
//                            terminei = true;
//                        }
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

    public boolean existeSubPasta(String tempPath) throws FileNotFoundException, IOException {
        if (tempPath.equals("")) {
            return false;
        }
        int pasta = (int) Integer.valueOf(tempPath);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.PASTA) {
            return true;
        }
        if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.PASTA) {
            return true;
        }
        if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.PASTA) {
            return true;
        }

        while (this.proxBloco != 0) {
            RandomAccessFile bloco = new RandomAccessFile(hd, "r");
            bloco.seek(this.proxBloco);

            this.blocoPrimeiroArquivo = (short) bloco.readUnsignedShort();
            this.segurancaPrimeiroArquivo = bloco.readByte();
            this.nomePrimeiroArquivo = bloco.readByte();
            this.nomeDonoPrimeiroArquivo = bloco.readByte();

            this.blocoSegundoArquivo = (short) bloco.readUnsignedShort();
            this.segurancaSegundoArquivo = bloco.readByte();
            this.nomeSegundoArquivo = bloco.readByte();
            this.nomeDonoSegundoArquivo = bloco.readByte();

            this.blocoTerceiroArquivo = (short) bloco.readUnsignedShort();
            this.segurancaTerceiroArquivo = bloco.readByte();
            this.nomeTerceiroArquivo = bloco.readByte();
            this.nomeDonoTerceiroArquivo = bloco.readByte();

            this.filling = bloco.readByte();
            this.proxBloco = (short) bloco.readUnsignedShort();
            byteFinal = bloco.getFilePointer();
            bloco.close();
            if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.PASTA) {
                return true;
            }
            if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.PASTA) {
                return true;
            }
            if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.PASTA) {
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

        return (int) this.byteFinal;
    }

    public boolean estaCheia() {

        return ((this.blocoPrimeiroArquivo != 0)
                && (this.blocoSegundoArquivo != 0)
                && (this.blocoTerceiroArquivo != 0));

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
        this.nomeDonoPrimeiroArquivo = bloco.readByte();
        this.blocoSegundoArquivo = (short) bloco.readUnsignedShort();
        this.segurancaSegundoArquivo = bloco.readByte();
        this.nomeSegundoArquivo = bloco.readByte();
        this.nomeDonoSegundoArquivo = bloco.readByte();
        this.blocoTerceiroArquivo = (short) bloco.readUnsignedShort();
        this.segurancaTerceiroArquivo = bloco.readByte();
        this.nomeTerceiroArquivo = bloco.readByte();
        this.nomeDonoTerceiroArquivo = bloco.readByte();

        this.filling = bloco.readByte();
        this.proxBloco = (short) bloco.readUnsignedShort();
        this.byteFinal = bloco.getFilePointer();
        bloco.close();
    }

    public short getBlocoPasta(String tempPath) throws FileNotFoundException, IOException {
        if (tempPath.equals("")) {
            return 0;
        }
        int pasta = (int) Integer.valueOf(tempPath);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.PASTA) {
            return this.blocoPrimeiroArquivo;
        }
        if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.PASTA) {
            return this.blocoSegundoArquivo;
        }
        if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.PASTA) {
            return this.blocoTerceiroArquivo;
        }

        while (this.proxBloco != 0) {
            RandomAccessFile bloco = new RandomAccessFile(hd, "r");
            bloco.seek(this.proxBloco);

            this.blocoPrimeiroArquivo = (short) bloco.readUnsignedShort();
            this.segurancaPrimeiroArquivo = bloco.readByte();
            this.nomePrimeiroArquivo = bloco.readByte();
            this.nomeDonoPrimeiroArquivo = bloco.readByte();

            this.blocoSegundoArquivo = (short) bloco.readUnsignedShort();
            this.segurancaSegundoArquivo = bloco.readByte();
            this.nomeSegundoArquivo = bloco.readByte();
            this.nomeDonoSegundoArquivo = bloco.readByte();

            this.blocoTerceiroArquivo = (short) bloco.readUnsignedShort();
            this.segurancaTerceiroArquivo = bloco.readByte();
            this.nomeTerceiroArquivo = bloco.readByte();
            this.nomeDonoTerceiroArquivo = bloco.readByte();

            this.filling = bloco.readByte();
            this.proxBloco = (short) bloco.readUnsignedShort();
            byteFinal = bloco.getFilePointer();
            bloco.close();
            if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.PASTA) {
            return this.blocoPrimeiroArquivo;
            }
            if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.PASTA) {
                return this.blocoSegundoArquivo;
            }
            if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.PASTA) {
                return this.blocoTerceiroArquivo;
            }

        }

        return 0;

    }

    public boolean apagaSubPasta(String splitado) throws IOException {
        //if (this.existeSubPasta(splitado)) {
        //se sim, existe apaga
        boolean terminei = false;
        byte xSeguranca = (byte) seguranca( this.ARQUIVO , 70);
        int pasta = (int) Integer.valueOf(splitado);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.PASTA) {
            this.blocoPrimeiroArquivo = 0;
            this.nomePrimeiroArquivo = 0;
            this.segurancaPrimeiroArquivo = xSeguranca;
            terminei = true;
        }
        if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.PASTA) {
            this.blocoSegundoArquivo = 0;
            this.nomeSegundoArquivo = 0;
            this.segurancaSegundoArquivo = xSeguranca;
            terminei = true;
        }
        if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.PASTA) {
            this.blocoTerceiroArquivo = 0;
            this.nomeTerceiroArquivo = 0;
            this.segurancaTerceiroArquivo = xSeguranca;
            terminei = true;
        }
//        if (p == getNomeQuartoArquivo() && getSegurancaQuartoArquivo() == 0) {
//            this.blocoQuartoArquivo = 0;
//            this.nomeQuartoArquivo = 0;
//            this.segurancaQuartoArquivo = (byte) 10000000;
//            terminei = true;
//        }
        if (terminei) {
            atualizaBytesDaPasta();
            return true;
        }

        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();

            if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.PASTA) {
                this.blocoPrimeiroArquivo = 0;
                this.nomePrimeiroArquivo = 0;
                this.segurancaPrimeiroArquivo = xSeguranca;
                terminei = true;
            }
            if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.PASTA) {
                this.blocoSegundoArquivo = 0;
                this.nomeSegundoArquivo = 0;
                this.segurancaSegundoArquivo = xSeguranca;
                terminei = true;
            }
            if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.PASTA) {
                this.blocoTerceiroArquivo = 0;
                this.nomeTerceiroArquivo = 0;
                this.segurancaTerceiroArquivo = xSeguranca;
                terminei = true;
            }
            if (terminei) {
                atualizaBytesDaPasta();
                return true;
            }
        }

        System.out.println("Incrivel nao achei esta pasta");
        return false; //caso nao apague
    }

    public boolean estaVazia() throws IOException {
        boolean vazia = true;
        byte p = (byte) 0;
        if ( blocoPrimeiroArquivo == 0 && 
             blocoSegundoArquivo  == 0 &&
             blocoTerceiroArquivo == 0 &&  
             proxBloco == 0   ) return true ;
        
        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if ( blocoPrimeiroArquivo == 0 && 
                 blocoSegundoArquivo  == 0 &&
                 blocoTerceiroArquivo == 0 &&  
                 proxBloco == 0   ) return true ;
            if ( !(blocoPrimeiroArquivo == 0 && 
                 blocoSegundoArquivo  == 0 &&
                 blocoTerceiroArquivo == 0) ) return false;
        }
        return false;
    }

    public void mostraPasta() throws IOException {
        System.out.println("List of Files/Directoryes");
        System.out.println("Nome        Segurança       Proprietario     Binario");
        System.out.println("-------------------------------------------------------------------------");
        int arquivos = 0 ;
        int diretorios = 0 ;
        if (getNomePrimeiroArquivo() > 0) {
            String pasta = "";
            if (getTipoArquivo(this.segurancaPrimeiroArquivo) == this.PASTA)  {//é aki que nao conta arquivos
                diretorios++;
                pasta = "/";
            } else arquivos++;
            System.out.println(pasta + this.nomePrimeiroArquivo 
                             + "          " + this.segurancaFormatada(segurancaPrimeiroArquivo)
                             + "          " + this.nomeDonoPrimeiroArquivo
                             + "          " + xBinario( (char) this.segurancaPrimeiroArquivo) );
        }
        if (getNomeSegundoArquivo() > 0) {
            String pasta = "";
            if (getTipoArquivo(this.segurancaSegundoArquivo) == this.PASTA) {
                diretorios++;
                pasta = "/";
            }else arquivos++;
            System.out.println(pasta + this.nomeSegundoArquivo 
                    + "          " + this.segurancaFormatada(segurancaSegundoArquivo) 
                    + "          " + this.nomeDonoSegundoArquivo
                    + "          " + xBinario( (char) this.segurancaSegundoArquivo));
        }
        if (getNomeTerceiroArquivo() > 0) {
            String pasta = "";
            if (getTipoArquivo(this.segurancaTerceiroArquivo) == this.PASTA) {
                diretorios++;
                pasta = "/";
            }else arquivos++;
            System.out.println(pasta + this.nomeTerceiroArquivo  
                    + "          " + this.segurancaFormatada(segurancaTerceiroArquivo)
                    + "          " + this.nomeDonoTerceiroArquivo
                    + "          " + xBinario( (char) this.segurancaTerceiroArquivo));
        }
        
        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if (getNomePrimeiroArquivo() > 0) {
            String pasta = "";
            if (getTipoArquivo(this.segurancaPrimeiroArquivo) == this.PASTA) {
                diretorios++;
                pasta = "/";
            }else arquivos++;
                System.out.println(pasta + this.nomePrimeiroArquivo 
                        + "          " + this.segurancaFormatada(segurancaPrimeiroArquivo)
                        + "          " + this.nomeDonoPrimeiroArquivo
                        + "          " + xBinario( (char) this.segurancaPrimeiroArquivo));
            }
            if (getNomeSegundoArquivo() > 0) {
                String pasta = "";
                if (getTipoArquivo(this.segurancaSegundoArquivo) == this.PASTA) {
                    diretorios++;
                    pasta = "/";
                }else arquivos++;
                System.out.println(pasta + this.nomeSegundoArquivo 
                        + "          " + this.segurancaFormatada(segurancaSegundoArquivo)
                        + "          " + this.nomeDonoSegundoArquivo
                        + "          " + xBinario( (char) this.segurancaSegundoArquivo));
            }
            if (getNomeTerceiroArquivo() > 0) {
                String pasta = "";
                if (getTipoArquivo(this.segurancaTerceiroArquivo) == this.PASTA) {
                    diretorios++;
                    pasta = "/";
                }else arquivos++;
                System.out.println(pasta + this.nomeTerceiroArquivo 
                        + "          " + this.segurancaFormatada(segurancaTerceiroArquivo)
                        + "          " + this.nomeDonoTerceiroArquivo
                        + "          " + xBinario( (char) this.segurancaTerceiroArquivo));
            }
        }
        
        System.out.println("");
        System.out.println("");
        System.out.println(arquivos + " arquivo(s) listado(s).");
        System.out.println(diretorios + " pasta(s) listada(s)");
        System.out.println("");
        
        
    }

    public void novoArquivo(short blocoVazio, String nome, byte usuario) throws IOException {
            // TODO  Implementar Segurança posteriormente.
            //       Preciso pegar o usuario para terminar este metodo.
        byte xSeguranca = (byte) seguranca(1,64);
        boolean terminei = false;
        if (this.blocoPrimeiroArquivo == 0) {
            this.blocoPrimeiroArquivo = blocoVazio;
            this.nomePrimeiroArquivo = (byte) (int) Integer.valueOf(nome);
            this.segurancaPrimeiroArquivo = xSeguranca;
            this.nomeDonoPrimeiroArquivo = usuario;
            terminei = true;
        } else {
            if (this.blocoSegundoArquivo == 0) {
                this.blocoSegundoArquivo = blocoVazio;
                this.nomeSegundoArquivo = (byte) (int) Integer.valueOf(nome);
                this.segurancaSegundoArquivo = xSeguranca;
                this.nomeDonoSegundoArquivo = usuario;
                terminei = true;
            } else {
                if (this.blocoTerceiroArquivo == 0) {
                    this.blocoTerceiroArquivo = blocoVazio;
                    this.nomeTerceiroArquivo = (byte) (int) Integer.valueOf(nome);
                    this.segurancaTerceiroArquivo = xSeguranca;
                    this.nomeDonoTerceiroArquivo = usuario;
                    terminei = true;
                } 
            }
        }
        if (terminei) {
            atualizaBytesDaPasta();
            return;
        }
        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if (this.blocoPrimeiroArquivo == 0) {
            this.blocoPrimeiroArquivo = blocoVazio;
            this.nomePrimeiroArquivo = (byte) (int) Integer.valueOf(nome);
            this.segurancaPrimeiroArquivo = xSeguranca;
            this.nomeDonoPrimeiroArquivo = usuario;
            terminei = true;
        } else {
            if (this.blocoSegundoArquivo == 0) {
                this.blocoSegundoArquivo = blocoVazio;
                this.nomeSegundoArquivo = (byte) (int) Integer.valueOf(nome);
                this.segurancaSegundoArquivo = xSeguranca;
                this.nomeDonoSegundoArquivo = usuario;
                terminei = true;
            } else {
                if (this.blocoTerceiroArquivo == 0) {
                    this.blocoTerceiroArquivo = blocoVazio;
                    this.nomeTerceiroArquivo = (byte) (int) Integer.valueOf(nome);
                    this.segurancaTerceiroArquivo = xSeguranca;
                    this.nomeDonoTerceiroArquivo = usuario;
                    terminei = true;
                } 
            }
        }
            if (terminei) {
                atualizaBytesDaPasta();
                return;
            }
        }

    }

    public void apagaArquivo(String splitado) throws IOException {
        boolean terminei = false;
        byte xSeguranca = (byte) seguranca( this.ARQUIVO , 64);
        int pasta = (int) Integer.valueOf(splitado);
        byte p = (byte) pasta;
        if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.ARQUIVO) {
            this.blocoPrimeiroArquivo = 0;
            this.nomePrimeiroArquivo = 0;
            this.segurancaPrimeiroArquivo = xSeguranca;
            terminei = true;
        }
        if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.ARQUIVO) {
            this.blocoSegundoArquivo = 0;
            this.nomeSegundoArquivo = 0;
            this.segurancaSegundoArquivo = xSeguranca;
            terminei = true;
        }
        if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.ARQUIVO) {
            this.blocoTerceiroArquivo = 0;
            this.nomeTerceiroArquivo = 0;
            this.segurancaTerceiroArquivo = xSeguranca;
            terminei = true;
        }
        
        if (terminei) {
            atualizaBytesDaPasta();
            return;
        }

        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();

            if (p == getNomePrimeiroArquivo() && getTipoArquivo(getSegurancaPrimeiroArquivo()) == this.ARQUIVO) {
            this.blocoPrimeiroArquivo = 0;
            this.nomePrimeiroArquivo = 0;
            this.segurancaPrimeiroArquivo = xSeguranca;
            terminei = true;
        }
        if (p == getNomeSegundoArquivo() && getTipoArquivo(getSegurancaSegundoArquivo()) == this.ARQUIVO) {
            this.blocoSegundoArquivo = 0;
            this.nomeSegundoArquivo = 0;
            this.segurancaSegundoArquivo = xSeguranca;
            terminei = true;
        }
        if (p == getNomeTerceiroArquivo() && getTipoArquivo(getSegurancaTerceiroArquivo()) == this.ARQUIVO) {
            this.blocoTerceiroArquivo = 0;
            this.nomeTerceiroArquivo = 0;
            this.segurancaTerceiroArquivo = xSeguranca;
            terminei = true;
        }
            if (terminei) {
                atualizaBytesDaPasta();
                return;
            }
        }
        System.out.println("Incrivel nao achei esta pasta");
        return;
    }

    public byte getSeguranca(String tempPath) throws IOException {
    
        if (tempPath.equals("")) {
            return 0;
        }
        int pasta = (int) Integer.valueOf(tempPath);
        byte nomeArq = (byte) pasta;
        if (nomeArq == getNomePrimeiroArquivo())  {
            return this.getSegurancaPrimeiroArquivo();
        }
        if (nomeArq == getNomeSegundoArquivo()){
            return this.getSegurancaSegundoArquivo();
        }
        if (nomeArq == getNomeTerceiroArquivo()) {
            return this.getSegurancaTerceiroArquivo();
        }

        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if (nomeArq == getNomePrimeiroArquivo())  {
                return this.getSegurancaPrimeiroArquivo();
            }
            if (nomeArq == getNomeSegundoArquivo()){
                return this.getSegurancaSegundoArquivo();
            }
            if (nomeArq == getNomeTerceiroArquivo()) {
                return this.getSegurancaTerceiroArquivo();
            }

            }

    return 0;
    }
    
    public boolean setSeguranca(String tempPath, byte segurancaNova) throws IOException {

        int pasta = (int) Integer.valueOf(tempPath);
        byte nomeArq = (byte) pasta;
        if (nomeArq == getNomePrimeiroArquivo())  {
            this.segurancaPrimeiroArquivo = segurancaNova;
            this.atualizaBytesDaPasta();
            return true;
        }
        if (nomeArq == getNomeSegundoArquivo()){
            this.segurancaSegundoArquivo = segurancaNova;
            this.atualizaBytesDaPasta();
            return true;
        }
        if (nomeArq == getNomeTerceiroArquivo()) {
            this.segurancaTerceiroArquivo = segurancaNova;
            this.atualizaBytesDaPasta();
            return true;
        }

        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if (nomeArq == getNomePrimeiroArquivo())  {
                this.segurancaPrimeiroArquivo = segurancaNova;
                this.atualizaBytesDaPasta();
                return true;
            }
            if (nomeArq == getNomeSegundoArquivo()){
                this.segurancaSegundoArquivo = segurancaNova;
                this.atualizaBytesDaPasta();
                return true;
            }
            if (nomeArq == getNomeTerceiroArquivo()) {
                this.segurancaTerceiroArquivo = segurancaNova;
                this.atualizaBytesDaPasta();
                return true;
            }

        }
        return false;
    }
    
    public int getNomeDonoArquivo( String nome) throws IOException {
        if (nome.equals("")) {
            return 0;
        }
        
        byte nomeArq = (byte) (int)Integer.valueOf(nome);
        if (nomeArq == getNomePrimeiroArquivo())  {
            return this.getNomeDonoPrimeiroArquivo();
        }
        if (nomeArq == getNomeSegundoArquivo()){
            return this.getNomeDonoSegundoArquivo();
        }
        if (nomeArq == getNomeTerceiroArquivo()) {
            return this.getNomeDonoTerceiroArquivo();
        }

        while (this.proxBloco != 0) {
            leMaisBlocoDaPasta();
            if (nomeArq == getNomePrimeiroArquivo())  {
                return this.getNomeDonoPrimeiroArquivo();
            }
            if (nomeArq == getNomeSegundoArquivo()){
                return this.getNomeDonoSegundoArquivo();
            }
            if (nomeArq == getNomeTerceiroArquivo()) {
                return this.getNomeDonoTerceiroArquivo();
            }

        }
        
        return 0;
    }
    
    /*
    
        Métodos privados de uso exclusivo da propria classe.  
     
    */
    
    
    private byte getNomeDonoPrimeiroArquivo() {
        return nomeDonoPrimeiroArquivo;
    }

    private byte getNomeDonoSegundoArquivo() {
        return nomeDonoSegundoArquivo;
    }

    private byte getNomeDonoTerceiroArquivo() {
        return nomeDonoTerceiroArquivo;
    }
    
    private void gravaBinarioNovaPasta(short blocoVazio, byte[] novaPasta) throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "rw");
        bloco.seek(blocoVazio);
        bloco.write(novaPasta);
        bloco.close();
    }
    
    private void atualizaBytesDaPasta() throws FileNotFoundException, IOException {
        RandomAccessFile bloco = new RandomAccessFile(hd, "rw");
        bloco.seek(this.byteInicial);
        bloco.writeShort(this.blocoPrimeiroArquivo);
        bloco.writeByte(this.segurancaPrimeiroArquivo);
        bloco.writeByte(this.nomePrimeiroArquivo);
        bloco.writeByte(this.nomeDonoPrimeiroArquivo);
        bloco.writeShort(this.blocoSegundoArquivo);
        bloco.writeByte(this.segurancaSegundoArquivo);
        bloco.writeByte(this.nomeSegundoArquivo);
        bloco.writeByte(this.nomeDonoSegundoArquivo);
        bloco.writeShort(this.blocoTerceiroArquivo);
        bloco.writeByte(this.segurancaTerceiroArquivo);
        bloco.writeByte(this.nomeTerceiroArquivo);
        bloco.writeByte(this.nomeDonoTerceiroArquivo);
        bloco.writeByte(this.filling);
        bloco.close();
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

    private static int getTipoArquivo(byte seguranca) {
        
        return (int) xpegabit((char) seguranca, (char) 0);
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
    
    private String segurancaFormatada( byte security) {
        String permissoes = "rwx ";
                
        if( (int)(byte)(xpegabit((char) security, (char) 1)) == 1) permissoes = permissoes.concat("r");
        else permissoes = permissoes.concat("-");
        if( (int)(byte)(xpegabit((char) security, (char) 2)) == 1) permissoes = permissoes.concat("w");
        else permissoes = permissoes.concat("-");
        if( (int)(byte)(xpegabit((char) security, (char) 3)) == 1) permissoes = permissoes.concat("x");
        else permissoes = permissoes.concat("-");
        permissoes = permissoes.concat(" ");
        if((int)(byte)(xpegabit((char) security, (char) 4)) == 1) permissoes = permissoes.concat("r");
        else permissoes = permissoes.concat("-");
        if( (int)(byte)(xpegabit((char) security, (char) 5)) == 1) permissoes = permissoes.concat("w");
        else permissoes = permissoes.concat("-");
        if( (int)(byte)(xpegabit((char) security, (char) 6)) == 1) permissoes = permissoes.concat("x");
        else permissoes = permissoes.concat("-");
        
        return permissoes;
    }

    public boolean existeArquivo(String splitado) {
        
        return false;
    }

}
