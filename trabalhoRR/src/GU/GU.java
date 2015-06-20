package GU;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GU {

    private Usuario logado;
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    public GU() throws FileNotFoundException {
        Scanner file = null;
        int user;
        String leitura;
        String[] splitado;
        int umGrupo;
        Usuario umUsuario;

        file = new Scanner(new File("c:\\usuarios.csv"));
        leitura = file.nextLine();
        if (leitura != null) {
            splitado = leitura.split(",");
            user = Integer.valueOf(splitado[0]);
            while (user > -1) {
                umGrupo = Integer.valueOf(splitado[1]);
            //se o usuario nao existe criar um novo com o grupo
                //se ele jah existe adicionar no seu grupo
                umUsuario = getPorNome(user);
                if (umUsuario != null) {
                    umUsuario.grupos.add(umGrupo);
                } else {
                    umUsuario = new Usuario(user, umGrupo);
                    usuarios.add(umUsuario);
                }
                leitura = file.nextLine();
                splitado = leitura.split(",");
                user = Integer.valueOf(splitado[0]);
            }
        }
    }

    public Usuario getLogado() {
        return logado;
    }

    private Usuario getPorNome(int nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome() == nome) {
                return usuario;
            }
        }
        return null;
    }

    private void setLogado(Usuario logado) {
        this.logado = logado;
    }

    public boolean podeLogar(int nome) {
        Usuario user = getPorNome(nome);
        if (user != null) {
            setLogado(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean estaNoGrupo(int nomeDono) {
        ArrayList <Integer> donoGrupo;
        Usuario donoUsuario = this.getPorNome(nomeDono);
        donoGrupo = donoUsuario.getGrupos();
        for (Integer donoGrupo1 : donoGrupo) {
            if (this.logado.grupos.contains(donoGrupo1))
                return true;
        }
        return false;
    }
}
