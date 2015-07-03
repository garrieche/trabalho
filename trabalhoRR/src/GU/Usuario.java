package GU;

import java.util.ArrayList;

public class Usuario {

    private int nome;
    ArrayList<Integer> grupos = new ArrayList<>();

    public Usuario(int nome, int grupos) {
        this.nome = nome;
        this.grupos.add(grupos);
    }

    public int getNome() {
        return nome;
    }

    public void setNome(int nome) {
        this.nome = nome;
    }

    public ArrayList<Integer> getGrupos() {
        return grupos;
    }

}
