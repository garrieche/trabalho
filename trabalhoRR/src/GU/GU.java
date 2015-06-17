package GU;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GU {

    private Usuario logado;
    private Usuario[] usuarios;

    public GU() throws FileNotFoundException {
        Scanner file = null;
        String tmp;

        file = new Scanner(new File("c:\\grupos.csv"));
    }

    public Usuario getLogado() {
        return logado;
    }

    private void setLogado(Usuario logado) {
        this.logado = logado;
    }
    
    public boolean podeLogar (int nome){
        //this.logado(exist(nome));
        return true;
    }

}
