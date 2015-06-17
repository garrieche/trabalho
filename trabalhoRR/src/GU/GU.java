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

}
