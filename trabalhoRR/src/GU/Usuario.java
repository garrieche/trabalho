package GU;

public class Usuario {

    private int nome;
    private int[] grupos;

    public Usuario(int nome, int[] grupos) {

        this.nome = nome;
        this.grupos = grupos;
    }

    public int getNome() {
        return nome;
    }

    public void setNome(int nome) {
        this.nome = nome;
    }

    public int[] getGrupos() {
        return grupos;
    }

    public void setGrupos(int[] grupos) {
        this.grupos = grupos;
    }
}