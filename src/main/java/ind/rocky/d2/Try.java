package ind.rocky.d2;

public class Try {
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.start();
        T1.changed=true;
        T1.outNano=System.nanoTime();
        T1.i=1;
        t1.join();
        System.out.println(T1.inNano-T1.outNano);
    }

    static class T1 extends Thread {
        volatile static boolean changed = false;
        static long outNano;
        static long inNano;
        volatile static long i=0;
        public void run(){
            while(!changed){
            }
            inNano = System.nanoTime();
        }
    }
}
