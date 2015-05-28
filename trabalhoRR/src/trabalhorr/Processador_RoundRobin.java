package roundrobin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Processador_RoundRobin {

    /**
     * @param args the command line arguments
     *
     * pb = Probabilidade de Bloqueio pr = Probabilidade de Resposta tc = Tempo
     * de criação tv = Tempo de Vida
     *
     * [E] = Execucao [P] = Prontos [B] = Bloqueados
     * @return
     */
    public static void main(String[] args) {
        int AC;
        int PC;
        int N, Z;
        int memoria[] = new int[256];
        int instrucao[] = new int[256];
        int quantum;
        float pr;
        int tamanhoPagina = 5;
        int nroPaginas = 4;

        Scanner file = null;
        int comando;
        int[] tmpVet = new int[256];
        int vlr1;
        int ciclo = 0;
        int x = 0;
        int i = 0;
        String erro = "";
        String erro2 = "";
        String tmp;
        String vlr2 = "";
        String origem;
        File fileOrigin;
        ArrayList<Processo> processos = new ArrayList<>();

        System.out.println("BETITANDER");
        System.out.println("--------------------------------------");
        System.out.println("Wait, Loading Files...");
        try {
            file = new Scanner(new File("c:\\entrada.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tmp = file.nextLine();
        quantum = Integer.valueOf(tmp);
        if (quantum > 0) {
            tmp = file.nextLine();
        } else {
            erro = erro.concat("Prob de bloqueio deve ser entre 0.1 e 0.9\n");
        }

        pr = Float.valueOf(tmp);
        if (pr < 0.1 || pr > 0.9) {
            erro = erro.concat("Prob de resposta deve ser entre 0.1 e 0.9\n");
        }
        vlr1 = -1;
        do {
            try {
                vlr1 = file.nextInt();
                vlr2 = file.nextLine().substring(1);
                if (vlr1 > 0) {
                    origem = vlr2;
                    fileOrigin = new File(origem);
                    try {
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileOrigin));
                        while ((x = in.read()) != -1) {
                            tmpVet[i] = x;
                            i++;
                        }
                        in.close();
                    } catch (Exception e) {
                        System.out.println("Erro = " + e);
                    }
                    processos.add(new Processo(vlr1, tmpVet));
                } else {
                    erro2 = erro2.concat("existe um processo invalido\n");
                }
            } catch (Exception e) {
                System.out.println("Erro = " + e);
            }
            vlr2 = "";
        } while (vlr1 != 0);
        System.out.println("Processing Start...");
        if (erro.equals("")) {
            SistemaOperacional windows = new SistemaOperacional(pr, tamanhoPagina, nroPaginas);
            for (Processo p : processos) {
                windows.addProcesso(p);
            }
            System.out.println("--------------------------------------");
            System.out.print("Ciclo SO ");
            for (Processo z : processos) {
                System.out.print(" P" + processos.indexOf(z) + " ");
            }
            System.out.println("");
            System.out.println("--------------------------------------");
            /*
             ==> Daqui pra cima Gabriel se vira
             Gabriel tem que me entregar os processos 
             devidamente instanciados
            
             */
            AC = 0;   //    Start Acumulador
            PC = 0;   //    Start Process Counter
            Z = 0;    //    Start Z
            N = 0;    //    Start Acumulador
            while (windows.getProcesosAtivos()) {
                windows.geraAcoes(ciclo);
                ciclo++;
                for (Processo p : processos) {
                    if (p.getTempodevida() > 0) {
                        if (p.getStatus() == 'P') {
                            windows.aloca(p, processos.indexOf(p), ciclo);
                            p.setStatus('E');
                        }
                        if (p.getStatus() == 'E') {

                            AC = p.getAc();
                            PC = p.getPc();
                            memoria = p.getMemoria();
                            instrucao = p.getInstrucoes();
                            for (int y = 0; y < quantum; y++) {
                                comando = instrucao[PC];

                                switch (comando) {
                                    case 0:
                                        // NOP
                                        PC++;
                                        break;
                                    case 1:
                                        // STO
                                        PC++;
                                        AC = instrucao[PC];
                                        PC++;
                                        break;
                                    case 2:
                                        // STA Men[end]
                                        PC++;
                                        memoria[instrucao[PC]] = AC;
                                        windows.aloca2(p, instrucao[PC], ciclo);
                                        PC++;
                                        break;
                                    case 3:
                                        // LDA
                                        PC++;
                                        AC = memoria[instrucao[PC]];
                                        windows.aloca2(p, instrucao[PC], ciclo);
                                        if (AC == 0) {
                                            Z = 1;
                                        } else {
                                            Z = 0;
                                        }
                                        if (AC >= 0) {
                                            N = 0;
                                        } else {
                                            N = 1;
                                        }
                                        PC++;
                                       
                                        break;
                                    case 4:
                                        // ADD
                                        PC++;
                                        AC += memoria[instrucao[PC]];
                                        windows.aloca2(p, instrucao[PC], ciclo);
                                        if (AC == 0) {
                                            Z = 1;
                                        } else {
                                            Z = 0;
                                        }

                                        if (AC >= 0) {
                                            N = 0;
                                        } else {
                                            N = 1;
                                        }
                                        PC++;
                                        //       }
                                        break;
                                    case 5:
                                        // OR
                                        PC++;
                                        AC = memoria[instrucao[PC]] | AC;
                                        windows.aloca2(p, instrucao[PC], ciclo);

                                        if (AC == 0) {
                                            Z = 1;
                                        } else {
                                            Z = 0;
                                        }

                                        if (AC >= 0) {
                                            N = 0;
                                        } else {
                                            N = 1;
                                        }
                                        PC++;
                                        //       }
                                        break;
                                    case 6:
                                        // AND

                                        PC++;
                                        //    if (PC < 256) {
                                        AC = memoria[instrucao[PC]] & AC;
                                        windows.aloca2(p, instrucao[PC], ciclo);
                                        if (AC == 0) {
                                            Z = 1;
                                        } else {
                                            Z = 0;
                                        }

                                        if (AC >= 0) {
                                            N = 0;
                                        } else {
                                            N = 1;
                                        }
                                        PC++;
                                        //       }
                                        break;
                                    case 7:
                                        AC = ~AC;
                                        PC++;
                                        break;
                                    case 8:

                                        PC++;
                                        //  if (PC < 256) {
                                        PC = instrucao[PC];
                                        //   }
                                        break;
                                    case 9:
                                        PC++;
                                        if (N == 1) {
                                            PC = instrucao[PC];
                                        } else {
                                            PC++;
                                        }
                                        break;
                                    case 10:
                                        PC++;
                                        //   if (PC < 256) {
                                        if (N == 0) {
                                            PC = instrucao[PC];
                                        } else {
                                            PC++;
                                        }
                                        //  }

                                        break;
                                    case 11:
                                        PC++;
                                        // if (PC < 256) {
                                        if (Z == 1) {
                                            PC = instrucao[PC];
                                        } else {
                                            PC++;
                                        }
                                        // }
                                        break;
                                    case 12:
                                        PC++;
                                        //if (PC < 256) {
                                        if (Z == 0) {
                                            PC = instrucao[PC];
                                        } else {
                                            PC++;
                                        }
                                        // }
                                        break;
                                    case 15:
                                        System.out.println("--------------------------------------");
                                        System.out.println("Betitaner Concluido");
                                        System.out.println("Progress Count:" + PC);
                                        System.out.println("Acumulador....:" + AC);
                                        System.out.println("Flag Z / N....:" + Z + "/" + N);
                                        System.out.println("Conteudo da Memória:");
                                        System.out.println("Memoria  -  Registrador");
                                        for (int j = 0; j < 10; j++) {
                                            System.out.println("[" + String.format("%02d", j) + "]" + String.format("%02d", memoria[j]) + " ------ " + String.format("%02d", instrucao[j]));
                                        }
                                        p.finaliza();
                                        break;

                                }
                                if (p.getTempodevida() < 1) {
                                    p.finaliza();
                                    break;
                                }
                                if (comando >= 2 && comando <= 6) {
                                    p.setStatus('B');
                                    p.salvaInterrupcao(AC, PC, N, Z, memoria);
                                } else {
                                    p.processa(PC);
                                }
                                System.out.printf(String.format("%05d", ciclo) + "  ");
                                for (Processo z : processos) {
                                    System.out.print("   " + z.toString());
                                }
                                System.out.println(windows.toString());
                                //System.out.println("");
                                ciclo++;
                                if (p.getStatus() == 'B') {
                                    break;
                                }

                            }
                            if (p.getStatus() == 'E') {
                                p.setStatus('P');
                            }
                        }
                    }
                }
            }
            System.out.println("--------------------------------------");
            System.out.println("terminado");
        } else {
            System.out.println(erro);
        }
        System.out.println(erro2);
    }
}
