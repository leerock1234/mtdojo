package ind.rocky.timequeue;

import java.util.LinkedList;
import java.util.Queue;

public class TimeQueue<T> {
    Queue<Pack<T>> queue = new LinkedList<>();
    long expectTimestamp=-1;
    public void put(T to, long i) {
        queue.add(new Pack<T>(to, i));
    }

    public T get() {
        if (isNotStarted()){
            throw new NotStartedException();
        }
        Pack<T> pack = synGetPack();
        long timeToSleep=expectTimestamp-System.currentTimeMillis();
        if (timeToSleep>0) {
            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return pack.t;
    }

    private synchronized Pack<T> synGetPack() {
        Pack<T> pack = queue.poll();
        expectTimestamp+=pack.duration;
        return pack;
    }

    private boolean isNotStarted() {
        return expectTimestamp==-1;
    }

    public long startTime() {
        expectTimestamp = System.currentTimeMillis();
        return expectTimestamp;
    }

    class Pack<T> {
        T t;
        long duration;
        public Pack(T t, long duration){
            this.t = t;
            this.duration = duration;
        }
    }
}


