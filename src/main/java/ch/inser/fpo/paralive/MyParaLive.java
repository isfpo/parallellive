package ch.inser.fpo.paralive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Une petit classe de tests pour voir ce qu'il se passe avec des traitements
 * parallèles
 * 
 * @author frp
 * 
 */
public class MyParaLive {

    private class myCallable implements Callable<Object> {

        public myCallable() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public Object call() throws Exception {
            Thread.sleep(100);
            return null;
        }

    }

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
        System.out.println("Without parallelism : " + (end - start));
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
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);// Il
                                                                        // semble
                                                                        // que
                                                                        // ce
                                                                        // soit
                                                                        // sans
                                                                        // timeout!
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = new Date().getTime();
        System.out.println("With awaitTermination :" + (end - start));
        return end - start;
    }

    public long withRunnableEncapsulatedCallable() {
        int nbrOfThreads = Runtime.getRuntime().availableProcessors();
        long start = new Date().getTime();
        ExecutorService exec = Executors.newFixedThreadPool(nbrOfThreads);
        List<Callable<Object>> todo = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            todo.add(Executors.callable(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        try {
            @SuppressWarnings("unused")
            List<Future<Object>> future = exec.invokeAll(todo);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long end = new Date().getTime();
        System.out.println("With Runnable encapsulated in Callable : "
                + (end - start));
        return end - start;
    }

    public long withCallable() {
        int nbrOfThreads = Runtime.getRuntime().availableProcessors();
        long start = new Date().getTime();
        ExecutorService exec = Executors.newFixedThreadPool(nbrOfThreads);
        List<Callable<Object>> todo = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            todo.add(new myCallable());
        }

        try {
            @SuppressWarnings("unused")
            List<Future<Object>> future = exec.invokeAll(todo);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long end = new Date().getTime();
        System.out.println("With Callable : " + (end - start));
        return end - start;
    }
}
