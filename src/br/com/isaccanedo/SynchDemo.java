package br.com.isaccanedo;

/**
 * @author Isac Canedo
 */

public class SynchDemo {

    public static void main(String[] args) {

        CallMe target = new CallMe();
        Caller c1 = new Caller(target, "HI", "um");
        Caller c2 = new Caller(target, "Synch", "dois");
        Caller c3 = new Caller(target, "World", "três");

        c1.t.start();
        c2.t.start();
        c3.t.start();

        try {
            c1.t.join();
            c2.t.join();
            c3.t.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Fim");
    }

}

class CallMe {
    void call(String msg) {
        System.out.print("[" + msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("]");
    }
}

class Caller implements Runnable {
    CallMe target;
    Thread t;
    String msg;
    String name;

    public Caller(CallMe targ, String s, String name) {
        target = targ;
        this.name = name;
        msg = s;
        t = new Thread(this);
    }

    public void run() {
        System.out.println("Thread: "+ name);
        synchronized (target) {
            System.out.println("Synch Thread: "+ name);
            target.call(msg);
        }

    }
}