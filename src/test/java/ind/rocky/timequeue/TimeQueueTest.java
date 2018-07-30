package ind.rocky.timequeue;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TimeQueueTest {

    @Test(expected = NotStartedException.class)
    public void should_throw_error_when_used_without_start(){
        TimeQueue<TO> timeQueue = new TimeQueue<>();
        timeQueue.put(new TO(1),0);
        timeQueue.get();
    }

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
        for (int i = 1; i <=100 ; i++) {
            timeQueue.put(new TO(i),5);
        }
        List<TRetrive> ts = new ArrayList<>();
        for (int i = 1; i <=100 ; i++) {
            ts.add(new TRetrive(timeQueue, String.valueOf(i)));
        }
        long startTime = timeQueue.startTime();
        for(TRetrive t : ts){
            t.start();
        }
        try {
            for(TRetrive t : ts){
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Long> tTimes = new ArrayList<>();
        for(TRetrive t : ts){
            tTimes.add(t.retriveTimestamp-startTime);
        }
        tTimes.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });

        for (int i = 5; i < 100; i++) {
            assertThat("Verifying No. "+i+" Thread.", tTimes.get(i), greaterThanOrEqualTo(5l*(i+1)));
            assertThat("Verifying No. "+i+" Thread.", tTimes.get(i), lessThan(5l*(i+1)+20));
        }
    }

    @Test
    public void should_support_two_threads(){
        TimeQueue<TO> timeQueue = new TimeQueue<>();
        timeQueue.put(new TO(1),100);
        timeQueue.put(new TO(2),100);
        TRetrive t1 = new TRetrive(timeQueue, "1");
        TRetrive t2 = new TRetrive(timeQueue, "2");
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
        public TRetrive(TimeQueue timeQueue, String threadId){
            this.timeQueue = timeQueue;
            this.setName("MTreadId-"+threadId);
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