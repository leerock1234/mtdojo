package ind.rocky.d1;

import sun.java2d.pipe.SpanShapeRenderer;

public class TryArray {

    public static final int RNUM = 50000000;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Request[] requests = buildRequests(RNUM);
        //handle(StreamHandler.class, requests);
        handle(SimpleHandler.class, requests);
    }

    private static void handle(Class handlerClass, Request[] requests) throws IllegalAccessException, InstantiationException {
        Handler handler = (Handler) handlerClass.newInstance();
        TestResult testResult = handleRequest(requests, handler);
        System.out.println("Time cost of "+handlerClass.getSimpleName()+": "+ testResult.duration);
        assertResult(testResult.results);
    }

    private static TestResult handleRequest(Request[] requests, Handler handler) {
        Long startTimestamp = getTimestamp();
        TestResult testResult = new TestResult();
        testResult.results = handler.handleRequests(requests);
        Long endTimestamp = getTimestamp();
        testResult.duration = endTimestamp - startTimestamp;
        return testResult;
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
