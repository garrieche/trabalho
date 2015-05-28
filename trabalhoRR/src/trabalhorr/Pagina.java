package roundrobin;

public class Pagina {

    private int tipo;      // 0 - Segmento Codigo  ou 1 - Segmento Memória
    private int segmento; //usar pc/tamanho pagina
    private int ciclo;    //usado para saber quem sai
    private int tamanho;
    private int processo;
    private int idPagina;

    public int getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(int idPagina) {
        this.idPagina = idPagina;
    }

    public Pagina(int tamanho, int id ) {
        this.ciclo = -1;
        this.tamanho = tamanho;
        this.idPagina = id;

    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getSegmento() {
        return segmento;
    }

    public void setSegmento(int segmento) {
        this.segmento = segmento;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public String toString() {
        
        String tmpTipo = "";
        if (tipo == 0) {
            tmpTipo = "SC";
        } else {
            tmpTipo = "SM";
        }
        return " PG-" + idPagina + ", " + tmpTipo + ":"+ segmento + " ProcN:" + processo + " ";
    }

}
