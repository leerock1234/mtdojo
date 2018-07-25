package ind.rocky.d1;

public class MulTdSimpleHandler implements Handler {

    @Override
    public void handleRequests(Request[] requests, Result[] results) {

        int length = requests.length / 4;

        TheRun run1 = new TheRun(requests, results, length*0, length);
        Thread thread1 = new Thread(run1);
        thread1.start();

        TheRun run2 = new TheRun(requests, results, length*1, length);
        Thread thread2 = new Thread(run2);
        thread2.start();

        TheRun run3 = new TheRun(requests, results, length*2,length);
        Thread thread3 = new Thread(run3);
        thread3.start();

        TheRun run4 = new TheRun(requests, results,length*3,length);
        Thread thread4 = new Thread(run4);
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class TheRun implements Runnable {
        int start;
        int num;
        Request[] requests;
        Result[] results;
        public TheRun(Request[] requests, Result[] results, int start, int num){
            this.requests = requests;
            this.results = results;
            this.start = start;
            this.num = num;
        }
        public void run() {
            for (int f = start; f<start+num; f++) {
                Result result = new Result((long)f, requests[f].num * 2 % 10);
                results[f] = result;
            }

        }
    }
}
