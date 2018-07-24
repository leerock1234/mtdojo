package ind.rocky.d1;

public class TryArray {

    public static final int RNUM = 1000000;

    public static void main(String[] args) {
        Request[] requests = buildRequests(RNUM);
        Result[] results = buildResults(RNUM);
        Handler handler = new SimpleHandler();
        long duration = handleRequest(requests, results, handler);
        System.out.println("Total Time cost: "+ duration);
        assertResult(results);
    }

    private static long handleRequest(Request[] requests, Result[] results, Handler handler) {
        Long startTimestamp = getTimestamp();
        handler.handleRequests(requests, results);
        Long endTimestamp = getTimestamp();
        return endTimestamp - startTimestamp;
    }

    private static void assertResult(Result[] results) {
        for (int f = 0; f<results.length; f++) {
            assertEquals(f, results[f].resultId);
            assertEquals(f*2%10, results[f].num);
        }
    }

    private static void assertEquals(long n1, long n2) {
        if (n1!=n2) {
            throw new RuntimeException("It is not equals.");
        }
    }

    private static Result[] buildResults(int i) {
        Result[] results = new Result[i];
        return results;
    }

    private static Request[] buildRequests(int i) {
        Request[] requests = new Request[i];
        for (int j = 0; j < i; j++) {
            long l = j;
            requests[j] = new Request(l, l);
        }
        return requests;
    }

    private static Long getTimestamp() {
        return System.currentTimeMillis();
    }

}
