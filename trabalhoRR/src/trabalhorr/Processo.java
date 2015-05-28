package roundrobin;

public class Processo {

    private char status;
    private int tempodevida;
    private int tempodecriacao;
    private int[] instrucoes = new int[256];
    private int[] memoria = new int[256];
    
    private int ac;
    private int pc;
    private int z;
    private int n;
    
    
    public Processo(int tempodecriacao, int[] file) {
        this.status = 'X';
        this.tempodecriacao = tempodecriacao;
        this.instrucoes = file;
        this.tempodevida = 1000;
        System.out.println("Process Adding OK.");
    }

    public char getStatus() {
        return status;
        
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getTempodevida() {
        return tempodevida;
    }

    public int getTempodecriacao() {
        return tempodecriacao;
    }
    
    public void salvaInterrupcao( int ac, int pc, int n, int z, int[] memoria) {
        this.ac = ac ; 
        this.pc = pc ;
        this.n = n ;
        this.z = z;
        for ( int i=0 ; i < this.memoria.length ; i++) this.memoria[i] = memoria[i];
    
    }

    public int[] getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(int[] instrucoes) {
        this.instrucoes = instrucoes;
    }

    public int[] getMemoria() {
        return memoria;
    }

    public void setMemoria(int[] memoria) {
        this.memoria = memoria;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    
    

    public void processa(int PC) {
        if (this.status == 'E') {
 //           this.tempodevida--;
        }

    }

    public void finaliza() {
        this.status = 'F';
        this.tempodevida = 0;
        this.tempodecriacao = 0;
    }

    @Override
    public String toString(){
        if (this.status == 'F' || this.status == 'X')
            return " ";
        else
            return String.valueOf(this.getStatus());
    }
}
