package GU;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GU {

    private Usuario logado;
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private File csv = new File("c:\\usuarios.csv");

    public GU() {
        Scanner file = null;
        int user;
        String leitura;
        String[] splitado;
        int umGrupo;
        Usuario umUsuario;

        try {
            file = new Scanner(csv);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GU.class.getName()).log(Level.SEVERE, null, ex);
        }
        leitura = file.nextLine();
        leitura.trim();
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

    private void salvaCSV(){
               
        try
	{
	    FileWriter writer = new FileWriter(csv);

            for (Usuario usuario : usuarios) {
            ArrayList<Integer> grupo = usuario.getGrupos();
            for (Integer grupo1 : grupo) {
                writer.append(String.valueOf(usuario.getNome()));
                writer.append(",");
                writer.append(String.valueOf(grupo1));
                writer.append("\n");
            }
        }
            writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }

    public void logout() {
        this.logado = null;
    }
}
