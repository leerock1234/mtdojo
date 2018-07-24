package ind.rocky.d1;

public class MulTdSimpleHandler implements Handler {

    @Override
    public void handleRequests(Request[] requests, Result[] results) {
        for (int f = 0; f<requests.length; f++) {
            Result result = new Result((long)f, requests[f].num * 2 % 10);
            results[f] = result;
        }
    }
}
