package ch.inser.fpo.paralive;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Une petit classe de tests pour voir ce qu'il se passe avec des traitements
 * parallèles
 * 
 * @author frp
 * 
 */
public class MyParaLive {

    public long withoutPara() {
        long start = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = new Date().getTime();
        System.out.println(end - start);
        return end - start;
    }

    public long withPara() {
        int nbrOfThreads = Runtime.getRuntime().availableProcessors();
        long start = new Date().getTime();
        ExecutorService exec = Executors.newFixedThreadPool(nbrOfThreads);
        for (int i = 0; i < 100; i++) {
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime();
        System.out.println(end - start);
        return end - start;
    }

}
