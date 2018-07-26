package ind.rocky.d1;

public class SimpleHandler implements Handler {

    @Override
    public Result[] handleRequests(Request[] requests) {
        Result[] results = new Result[requests.length];
        for (int f = 0; f<requests.length; f++) {
            Result result = new Result((long)f, requests[f].num * 2 % 10);
            results[f] = result;
        }
        return results;
    }

}
