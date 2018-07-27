package ind.rocky.d1;

public class TryMultiThread {

    public static final int RNUM = 100000000;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Request[] requests = buildRequests(9, 3, 4);
        //handle(StreamHandler.class, requests);
        //handle(SimpleHandler.class, requests);
        handle(MulTdSimpleHandler.class, requests);
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
        assertEquals(6, results[0].num);
    }

    private static void assertEquals(long n1, long n2) {
        if (n1!=n2) {
            throw new RuntimeException("It is not equals, the left is "+n1+", but the right is "+n2);
        }
    }

    private static Result[] buildResults(int i) {
        Result[] results = new Result[i];
        return results;
    }

    private static Request[] buildRequests(int ... seeds) {
        Request[] requests = new Request[seeds.length];
        for (int i = 0; i < seeds.length; i++) {
            requests[i] = new Request((long)seeds[i], (long)RNUM);
        }
        return requests;
    }

    private static Long getTimestamp() {
        return System.currentTimeMillis();
    }

}
