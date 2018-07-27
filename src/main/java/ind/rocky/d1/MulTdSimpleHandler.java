package ind.rocky.d1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MulTdSimpleHandler implements Handler {

    @Override
    public Result[] handleRequests(Request[] requests) {
        List<Thread> threads = new ArrayList<>();
        List<TheRun> runables = new ArrayList<>();
        for(Request request : requests){
            TheRun run1 = new TheRun(request);
            Thread thread1 = new Thread(run1);
            thread1.start();
            threads.add(thread1);
            runables.add(run1);
        }
        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long resultNum=0;
        for(TheRun theRun : runables){
            resultNum = (resultNum + theRun.resultNum) % 10;
        }

        Result[] results = new Result[1];
        results[0] = new Result(1l, resultNum);

        return results;
    }

    class TheRun implements Runnable {
        Request request;
        Long resultNum=0l;
        public TheRun(Request request){
            this.request = request;
        }
        public void run() {
            Random random = new Random(request.seed);
            long start = System.currentTimeMillis();
            for (long f = 0; f < request.loopnum; f++) {
                resultNum = (resultNum + random.nextInt(10)) % 10;
            }
            long end = System.currentTimeMillis();
            System.out.println("Time cost of thread with seed "+request.seed+": "+ (end-start));
        }
    }
}
