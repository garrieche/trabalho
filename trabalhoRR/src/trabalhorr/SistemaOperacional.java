package roundrobin;

import java.util.ArrayList;
import java.util.Random;

public class SistemaOperacional {

    private final ArrayList<Processo> processos = new ArrayList<>();
    private int ciclo;
    private final float probResposta;
    Pagina[] paginas;
    private final int tamPag;

    public SistemaOperacional(float pr, int tamPag, int nroPaginas) {
        this.probResposta = pr;
        this.paginas = new Pagina[nroPaginas];
        this.tamPag = tamPag;
        for (int i = 0; i < nroPaginas; i++) {
            // paginas[i] = new Pagina(tamPag);
        }

        // onde fica o tamanho da pagina ?
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public void addProcesso(Processo process) {
        this.processos.add(process);

    }

    public void geraAcoes(int ciclo) {
        /*
         Primeiro vamos ver se tem algum processo pra ser Startado...
         */
        System.out.print(String.format("%05d", ciclo) + " E");
        for (Processo p : processos) {
            if (p.getStatus() == 'X' && ciclo >= p.getTempodecriacao()) {
                p.setStatus('P');
            }
            if (p.getTempodevida() == 0) {
                p.setStatus('F');
            } else {
                if (p.getStatus() == 'B') {
                    int npb = (int) (this.probResposta * 10);
                    Random random = new Random();
                    int sorteado = random.nextInt(10);
                    if (sorteado >= npb) {
                        p.setStatus('P');
                    }
                }
            }
            System.out.print("   " + p.toString());
        }
        System.out.println("");
    }

    public boolean getProcesosAtivos() {
        boolean terminou = false;
        for (Processo p : processos) {
            if (p.getStatus() != 'F') {
                terminou = true;
                break;
            }
        }
        return terminou;
    }

    public Pagina getPagina() {

        for (int i = 0; i < paginas.length; i++) {
            if (paginas[i] == null) {
                paginas[i] = new Pagina(this.tamPag, i + 1);
                return paginas[i];
            }
        }
        return getProximaPagina();
    }

    private Pagina getProximaPagina() {
        Pagina maisVelha = null;
        for (int i = 0; i < paginas.length; i++) {
            if (maisVelha == null) {
                maisVelha = paginas[i];
            }
            if (paginas[i].getCiclo() < maisVelha.getCiclo()) {
                maisVelha = paginas[i];
            }
        }
        return maisVelha;
    }

    public void aloca(Processo p, int posicao, int ciclo) {
        int segmentoCodigo = 0;
        int segmentoMemoria = 1;
        int meuSegmento = ((int) (p.getPc() / this.tamPag)) + 1;
        this.ciclo = ciclo;
        boolean processou = false;

        for (int i = 0; i < paginas.length; i++) {
            if (paginas[i] != null) {
                if (paginas[i].getProcesso() == posicao) {
                    if (paginas[i].getTipo() == segmentoCodigo) {
                        if (paginas[i].getSegmento() == meuSegmento) {
                            paginas[i].setCiclo(this.ciclo);
                            processou = true;
                        }
                    }
                }
            }
        }
        if (!processou) {

            this.updatePagina(segmentoCodigo, meuSegmento, posicao);

        }
    }

    public void aloca2(Processo p, int posicao, int ciclo) {

        int segmentoCodigo = 0;
        int segmentoMemoria = 1;
        int meuSegmentoMem = ((int) posicao / this.tamPag) + 1;
        this.ciclo = ciclo;
        boolean processou = false;
        int nroProcesso = processos.indexOf(p);

        for (int i = 0; i < paginas.length; i++) {
            if (paginas[i] != null) {
                if (paginas[i].getProcesso() == nroProcesso) {  //se eh meu processo
                    if (paginas[i].getTipo() == segmentoMemoria) { //se eh memoria
                        if (paginas[i].getSegmento() == meuSegmentoMem) { // se o segmento eh meu
                            paginas[i].setCiclo(this.ciclo);
                            processou = true;
                        }
                    }
                }
            }
        }
        if (!processou) {
            this.updatePagina(segmentoMemoria, meuSegmentoMem, nroProcesso);
        }

    }

    public void updatePagina(int tipo, int Segmento, int nroP) {
        Pagina tmp = getPagina();
        tmp.setCiclo(this.ciclo);
        tmp.setSegmento(Segmento);
        tmp.setTipo(tipo);
        tmp.setProcesso(nroP);
    }

    @Override
    public String toString() {
        String tmp = "";
        for (Pagina pagina : paginas) {
            if (pagina != null) {
                tmp = tmp + pagina.toString() + " ";
            }
        }

        return tmp;
    }

}
