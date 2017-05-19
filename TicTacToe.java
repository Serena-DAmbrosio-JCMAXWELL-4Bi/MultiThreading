/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        Monitor schermo = new Monitor();
        
        // Ho creato i 3 thread
        Thread tic = new Thread(new TTT ("TIC",schermo));
        Thread tac = new Thread(new TTT ("TAC",schermo));
        Thread toe = new Thread(new TTT ("TOE",schermo));
        //faccio partire tutti e 3 i thread contemporaneamente
        tic.start();
        tac.start();
        toe.start();
        
        try{
            tic.join();
            tac.join();
            toe.join();
        }catch(InterruptedException e){System.out.println(e);}
        
        
        long end = System.currentTimeMillis(); 
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
        System.out.println("Toe è capitato dopo di Tac: " + schermo.conta + " volte;");
    }
    
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato


class Monitor{
    int conta = 0;
    String Thread_p = " ";

public synchronized void StampaConta(String name, String messaggino){
    int casuale=100+(int)(Math.random()*300); //numero casuale tra 100 e 400
    if(Thread_p.equals("TAC") && name.equals("TOE")){ /*confronta il thread precedente con il thread attuale
            per verificare che prima di TOE ci sia TAC*/
                conta++; //nel caso si verificasse la condizione si aggiorna il contatore del punteggio
                messaggino +=" <--------";
    }
    try {
        TimeUnit.MILLISECONDS.sleep(casuale); //casuale ora diventa un numero rappresentante il tempo il MILLISECONDI
    }catch (InterruptedException e) {} //Richiamo eccezione    this.ultimoTHREAD = thread;
    Thread_p = name;
    
    System.out.println(messaggino);
}
}
class TTT implements Runnable{
    Monitor monitor;
    private String t;
    private String msg;
    public TTT(String s, Monitor monitor){
        this.monitor = monitor;
        this.t = s;
    }


public void run() {
    for (int i = 10; i > 0; i--) {
        msg = "<" + t + "> " + t + ": " + i;
        monitor.StampaConta(t, msg);
    }
}
}

