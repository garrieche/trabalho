package GU;

import java.io.FileNotFoundException;

public class teste {
    public static void main (String args[]) throws FileNotFoundException {
        GU gerUsuario = new GU();
        boolean teste = gerUsuario.podeLogar(1);
        System.out.println("Pode Logar? "+teste);
        System.out.println("Esta Logado -> "+gerUsuario.getLogado().getNome());
        System.out.println("Usuario 2 esta no grupo do logado? -> "+gerUsuario.estaNoGrupo(5));
        
    }
}
