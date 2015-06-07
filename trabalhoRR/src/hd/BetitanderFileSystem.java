package hd;

import static hd.Bits.mudabit;
import static hd.Bits.pegabit;
import static hd.Bits.xpegabit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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

        String caminhoFinal = "";

        if ((exist(caminho) != 0)) {
            System.out.println("Arquivo ou Pasta já existente.");
            return false;
        } else {
            String splitado[] = caminho.split("/");
            for (int i = 1; i < splitado.length - 1; i++) {
                caminhoFinal += ("/" + splitado[i]);
            }

            if (caminhoFinal.equals("")) {
                caminhoFinal = "/";
            }
            //quando estou tentando criar a subpasta preciso receber aki o endereço 
            //que vai criar e o caminho final esta incorreto
            if (exist(caminhoFinal) == 0) {
                return false;
            }
            temEspacoNaPasta(caminhoFinal);
            byte[] novaPasta = folder();
            Pasta pastaExistente = new Pasta(hd, exist(caminhoFinal));
            blocoVazio = getBlocoVazio();
            if (blocoVazio > 0) {
                pastaExistente.gravaNovaPasta(blocoVazio, novaPasta, splitado[splitado.length - 1]);
            } else {
                System.out.println("Acabou o espaço em disco!!!! ");
                return false;
            }
        }
        return true;
    }

    public boolean criaArquivo(String caminho, String local) throws IOException {
        short blocoVazio;
        short blocoInicial;
        String caminhoFinal = "";
        if ((exist(caminho) != 0)) {
            System.out.println("Arquivo ou Pasta já existente.");
            return false;
        } else {
            String splitado[] = caminho.split("/");
            for (int i = 1; i < splitado.length - 1; i++) {
                caminhoFinal += ("/" + splitado[i]);
            }

            if (caminhoFinal.equals("")) {
                caminhoFinal = "/";
            }

            if (exist(caminhoFinal) == 0) {
                return false;
            }
            temEspacoNaPasta(caminhoFinal);
            File fileOrigin = new File(local);
            byte segCodigo[] = new byte[256];
            int x;
            int i = 0;
            try {
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileOrigin));
                while ((x = in.read()) != -1) {
                    segCodigo[i] = (byte) x;
                    i++;
                }
                in.close();
            } catch (Exception e) {
                System.out.println("Erro = " + e);
            }
            blocoInicial = getBlocoVazio();
            if (blocoInicial > 0) {
                RandomAccessFile arq = new RandomAccessFile(hd, "rw");
                arq.seek(blocoInicial);
                boolean sairfor = false;
                for (int j = 0; j < segCodigo.length; j++) {
                    for (int k = 0; k < 16; k++) {
                        if (j < segCodigo.length) {
                            arq.writeByte(segCodigo[(j * 16) + k]);
                            if (segCodigo[(j * 16) + k] == (byte) 15) {
                                sairfor = true;
                            }
                        }
                    }
                    if (sairfor) {
                        arq.writeShort(0);
                        j = segCodigo.length + 2;
                    } else {
                        blocoVazio = getBlocoVazio();
                        arq.writeShort(blocoVazio);
                        arq.seek(blocoVazio);
                    }
                }
                arq.close();
            } else {
                System.out.println("Acabou o espaço em disco!!!! ");
                return false;
            }
            Pasta pastaExistente = new Pasta(hd, exist(caminhoFinal));
            pastaExistente.novoArquivo(blocoInicial, splitado[splitado.length-1]);
        }
        return true;
    }

    public void apagaPasta(String caminho) throws IOException {
        if (caminho == null) {
            System.out.println("Caminho Invalido");
            return;
        }
        if (caminho.length() == 1 && caminho.charAt(0) != '/') {
            System.out.println("Caminho Invalido");
            return;
        }
        if (caminho.length() == 1 && caminho.charAt(0) == '/') {
            System.out.println("Nao eh possivel apagar pásta raiz");
            return;
        }

        String caminhoAnterior = "";

        String splitado[] = caminho.split("/");
        for (int i = 1; i < splitado.length - 1; i++) {
            caminhoAnterior += ("/" + splitado[i]);
        }
        if (caminhoAnterior.equals("")) {
            caminhoAnterior = "/";
        }

        short retCaminhoAnt = exist(caminhoAnterior);
        short retCaminho = exist(caminho);
        Pasta paiPasta = new Pasta(hd, retCaminhoAnt);
        short blocoFilho = paiPasta.getBlocoPasta(splitado[splitado.length - 1]);
        Pasta filhoPasta = new Pasta(hd, retCaminho);

        if (filhoPasta.estaVazia() == false) {
            System.out.println("a pasta nao esta vazia");
            return;
        }
        if (paiPasta.apagaSubPasta(splitado[splitado.length - 1]) == true) {
            setBlocoLivre(blocoFilho);
        }
    }

    public void apagaArquivo(String caminho) throws IOException {
        
        if (caminho == null) {
            System.out.println("Caminho Invalido");
            return;
        }
        if (caminho.length() == 1 && caminho.charAt(0) != '/') {
            System.out.println("Caminho Invalido");
            return;
        }
        if (caminho.length() == 1 && caminho.charAt(0) == '/') {
            System.out.println("Nao eh possivel apagar pásta raiz");
            return;
        }

        String caminhoAnterior = "";

        String splitado[] = caminho.split("/");
        for (int i = 1; i < splitado.length - 1; i++) {
            caminhoAnterior += ("/" + splitado[i]);
        }
        if (caminhoAnterior.equals("")) {
            caminhoAnterior = "/";
        }

        short retCaminhoAnt = exist(caminhoAnterior);
        short retCaminho = exist(caminho);
        Pasta paiPasta = new Pasta(hd, retCaminhoAnt);
        short blocoFilho = paiPasta.getBlocoPasta(splitado[splitado.length - 1]);
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        boolean flag = true;
        while (flag) {
            arq.seek(blocoFilho+16);
            setBlocoLivre(blocoFilho);
            blocoFilho = arq.readShort();
            if (blocoFilho == 0){
                flag = false ;
            }    
        }
        paiPasta.apagaArquivo(splitado[splitado.length - 1]); 
    }

    static String xBinario(char valor) {
        String ret = "";
        for (int i = 0; i < 8; i++) {
            ret = String.valueOf((int) pegabit(valor, (char) i)) + ret;
        }
        return (ret);
    }

    public short getBlocoVazio() throws FileNotFoundException, IOException {
        RandomAccessFile arq = new RandomAccessFile(hd, "r");
        for (int i = 0; i < 128; i++) {
            short bitsAnteriores = (short) ((i) * 8);
            arq.seek(i);
            char b = (char) arq.readUnsignedByte();
            for (char j = 0; j < 8; j++) {
                if ((int) xpegabit(b, j) == 0) {
                    setBlocoUsado(bitsAnteriores + (j));
                    arq.close();
                    return (short) (128 + (18 * (bitsAnteriores + (j))));
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

        if (caminho == null) {
            return 0;
        }
        if (caminho.length() == 1 && caminho.charAt(0) != '/') {
            return 0;
        }
        if (caminho.length() == 1 && caminho.charAt(0) == '/') {
            return 128;
        }

        String tempPath = "";
        Pasta pastaRaiz = new Pasta(hd, 128);
        boolean navegou = false;
        short retorno = 0;
        String splitado[] = caminho.split("/");

        if (pastaRaiz.existeSubPasta(splitado[1])) {
            int x = 2;
            Pasta umaPasta = new Pasta(hd, pastaRaiz.getBlocoPasta(splitado[1]));
            retorno = pastaRaiz.getBlocoPasta(splitado[1]);

            while (x < splitado.length) {
                if (umaPasta.existeSubPasta(splitado[x])) {
                    retorno = umaPasta.getBlocoPasta(splitado[x]);
                    umaPasta = new Pasta(hd, umaPasta.getBlocoPasta(splitado[x]));

                    x++;
                } else {
                    return 0;
                }

            }

        }
        return retorno;
    }

    private void setBlocoUsado(int bloco) throws IOException {
        char novoBit;
        long pointer;
        int bit;
        byte leitura;
        RandomAccessFile arq = new RandomAccessFile(hd, "rw");
        pointer = ((bloco / 8));
        bit = ((bloco % 8));
        arq.seek(pointer);
        leitura = arq.readByte();
        novoBit = mudabit((char) leitura, (char) bit, (char) 1);
        arq.seek(pointer);
        arq.writeByte(novoBit);
        arq.close();

    }

    private void temEspacoNaPasta(String caminhoFinal) throws IOException {
        short bytePasta = exist(caminhoFinal);
        Pasta minhaPasta = new Pasta(hd, bytePasta);

        if (!minhaPasta.estaCheia()) {
            return;
        }
        while (minhaPasta.estaCheia()) {
            if (minhaPasta.estaCheia() && minhaPasta.getProxBloco() > 0) {
                minhaPasta.leMaisBlocoDaPasta();
            } else {
                break;
            }

        }
        if (minhaPasta.estaCheia() && minhaPasta.getProxBloco() == 0) {
            short blocoExtendePasta = getBlocoVazio();
            minhaPasta.expande(blocoExtendePasta, folder());
        }

    }

    private void setBlocoLivre(short blocoFilho) throws FileNotFoundException, IOException {
        System.out.println("aqui libera o bloco");
        char novoBit;
        long pointer;
        int bit;
        byte leitura;
        RandomAccessFile arq = new RandomAccessFile(hd, "rw");
        pointer = ((blocoFilho / 8));
        bit = ((blocoFilho % 8));
        arq.seek(pointer);
        leitura = arq.readByte();
        novoBit = mudabit((char) leitura, (char) bit, (char) 0);
        arq.seek(pointer);
        arq.writeByte(novoBit);
        arq.close();

    }

    public void mostraPasta(String caminho) throws IOException {
        short endereco = exist(caminho);
        Pasta pasta = new Pasta(hd, endereco);
        pasta.mostraPasta();
    }

    public void copiaArquivo(String vetorCMD, String vetorCMD0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void moveArquivo(String vetorCMD, String vetorCMD0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
