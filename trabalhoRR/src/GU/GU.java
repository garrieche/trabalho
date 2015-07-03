package GU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class GU {

    private Usuario logado;
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private File hd = new File("c:\\usuarios.csv");

    public GU() throws FileNotFoundException {
        Scanner file = null;
        int user;
        String leitura;
        String[] splitado;
        int umGrupo;
        Usuario umUsuario;

        file = new Scanner(hd);
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
        ArrayList<Integer> donoGrupo;
        Usuario donoUsuario = this.getPorNome(nomeDono);
        donoGrupo = donoUsuario.getGrupos();
        for (Integer donoGrupo1 : donoGrupo) {
            if (this.logado.grupos.contains(donoGrupo1)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(String comando) throws IOException {
        int nome = Integer.valueOf(comando);
        Usuario user = this.getPorNome(nome);
        if (user == null) {
            user = new Usuario(nome, 0);
            this.usuarios.add(user);
        } else {
            System.out.println("Usuario já existe");
        }
        this.salvaCSV();
    }

    public void delUser(String comando) throws IOException {
        int nome = Integer.valueOf(comando);
        Usuario user = this.getPorNome(nome);
        if (user == null) {
            System.out.println("Usuario já existe");
        } else {
            this.usuarios.remove(user);
        }
        this.salvaCSV();
    }

    public void alteraGrupo(String comando, String comando0) {
        /*
         gu A +B (apenas root)
         associa o usuário A ao grupo B

         gu A -B (apenas root)
         desassocia o usuário A ao grupo B
         */
        int nome = Integer.valueOf(comando);
        Usuario user = this.getPorNome(nome);
        int grupo = Integer.valueOf(comando0);
        if (grupo > 0 ){
            //associa
           if( user.getGrupos().contains(nome) ){
               System.out.println("Usuario já esta no grupo "+nome);
           }else {
               user.getGrupos().add(nome);
           }
        }else {
            //desassocia
           if( user.getGrupos().contains(nome) ){
               user.getGrupos().remove(nome);
           }else {
               System.out.println("Usuario não esta no grupo "+nome);
           } 
        }
        
    }

    private void salvaCSV() throws FileNotFoundException, IOException {
        RandomAccessFile csv = new RandomAccessFile(hd, "rw");
        for (Usuario usuario : usuarios) {
            ArrayList<Integer> grupo = usuario.getGrupos();
            for (Integer grupo1 : grupo) {
                csv.writeChars(usuario.getNome()+ "," + grupo1 + "\n");
            }
        }
        csv.write(-1);
        csv.close();
    }
}
