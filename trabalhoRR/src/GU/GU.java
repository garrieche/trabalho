package GU;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GU {

    private Usuario logado;
    private Usuario[] usuarios;

    public GU() throws FileNotFoundException {
        Scanner file = null;
        int user;
        int grupo;

        file = new Scanner(new File("c:\\grupos.csv"));
        user = file.nextInt();
        grupo = file.nextInt();
        if (getPorNome(user) != null){
            
        //Usuario umUsuario = new Usuario (user, grupo);
    }}

    public Usuario getLogado() {
        return logado;
    }
    
    private Usuario getPorNome(int nome){
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].getNome() == nome)
                return usuarios[i];
        }
        return null;
    }

    private void setLogado(Usuario logado) {
        this.logado = logado;
    }
    
    public boolean podeLogar (int nome){
        Usuario user = getPorNome(nome);
        
        if (user != null) {
            setLogado(user);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean estaNoGrupo(int nomeDono){
        return true;
    }

}
