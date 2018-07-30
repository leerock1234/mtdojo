package ind.rocky.timequeue;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class TimeQueueTest {

    @Test
    public void should_work_like_normal_queue_if_timestamp_are_zero(){
        TimeQueue<TO> timeQueue = new TimeQueue<>();
        timeQueue.put(new TO(1),0);
        timeQueue.put(new TO(2),0);
        timeQueue.startTime();

        TO t1 = timeQueue.get();
        TO t2 = timeQueue.get();

        assertEquals(1, t1.i);
        assertEquals(2, t2.i);
    }

    @Test
    public void should_delay_the_retrieval_as_timestamp(){
        TimeQueue<TO> timeQueue = new TimeQueue<>();
        timeQueue.put(new TO(1),100);
        timeQueue.put(new TO(2),100);
        long startTime = timeQueue.startTime();

        TO t1 = timeQueue.get();
        long t1Time = System.currentTimeMillis()-startTime;
        TO t2 = timeQueue.get();
        long t2Time = System.currentTimeMillis()-startTime;

        assertThat(t1Time, greaterThanOrEqualTo(100l));
        assertThat(t2Time, greaterThanOrEqualTo(200l));
    }

    @Test
    public void should_support_multiple_threads(){
        TimeQueue<TO> timeQueue = new TimeQueue<>();
        timeQueue.put(new TO(1),100);
        timeQueue.put(new TO(2),100);
        TRetrive t1 = new TRetrive(timeQueue);
        TRetrive t2 = new TRetrive(timeQueue);
        long startTime = timeQueue.startTime();
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long t1Time = t1.retriveTimestamp-startTime;
        long t2Time = t2.retriveTimestamp-startTime;

        assertThat(t1Time, greaterThanOrEqualTo(100l));
        assertThat(t2Time, greaterThanOrEqualTo(200l));
    }

    public static class TRetrive extends Thread{
        TimeQueue timeQueue;
        long retriveTimestamp;
        public TRetrive(TimeQueue timeQueue){
            this.timeQueue = timeQueue;
        }
        public void run(){
            timeQueue.get();
            this.retriveTimestamp = System.currentTimeMillis();
        }
    }

    public static class TO {
        int i;
        public TO(int i){
            this.i = i;
        }
    }

    //Should verify a large amount of result retrival.

}