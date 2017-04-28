/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */
package multithread;

import java.util.concurrent.TimeUnit;
/**
 *
 * @author Matteo Palitto
 */
public class Multithread {
    /**
     * @param args the command line arguments
     */
    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis();
        
        // Ho creato i 3 thread
        Thread tic = new Thread (new TicTacToe("TIC"));
        Thread tac = new Thread(new TicTacToe("TAC"));
        Thread toe = new Thread(new TicTacToe("TOE"));
        //faccio partire tutti e 3 i thread contemporaneamente
        tic.start();
        tac.start();
        toe.start();
        
        try{
            tic.join();
        }catch(InterruptedException e){System.out.println(e);}
        
        try{
            tac.join();
        }catch(InterruptedException e){System.out.println(e);}
        
        try{
            toe.join();
        }catch(InterruptedException e){System.out.println(e);}
        
        
        long end = System.currentTimeMillis();
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
        System.out.println("Toe è capitato dopo di Tac: " + TicTacToe.conta + " volte;");
    }
    
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
    public static int conta = 0;
    public static String T_Prec = "   ";
    // non essesndo "static" c'e' una copia delle seguenti variabili per ogni THREAD 
    private String t;
    private String msg;

    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s) {
        this.t = s;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {
        int casuale=100+(int)(Math.random()*300); /*permette di generare numeri casuali tra un range di 100 e 300. ciò serve per 
        far partire i thread in maniera casuale. */
        
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> ";
            //System.out.print(msg);
            
            try {
                TimeUnit.MILLISECONDS.sleep(casuale); 
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }
            msg += t + ": " + i;
            System.out.println(msg);
            if(T_Prec.equals("TAC") && t.equals("TOE")){
                conta++; 
            }
            T_Prec = t;
        }
    }
    
}
