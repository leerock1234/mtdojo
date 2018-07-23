package ind.rocky.d1;

public class TryArray {

    public static final int RNUM = 1000000;

    public static void main(String[] args) {
        Request[] requests = buildRequests(RNUM);
        Result[] results = buildResults(RNUM);
        Long startTimestamp = getTimestamp();
        handleRequests(requests, results);
        Long endTimestamp = getTimestamp();
        System.out.println("Total Time cost: "+(endTimestamp-startTimestamp));
        assertResult(results);
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

    private static void handleRequests(Request[] requests, Result[] results) {
        for (int f = 0; f<requests.length; f++) {
            Result result = new Result((long)f, requests[f].num * 2 % 10);
            results[f] = result;
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
