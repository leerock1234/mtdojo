package ind.rocky.d1;

import java.util.Random;

public class SimpleHandler implements Handler {

    @Override
    public Result[] handleRequests(Request[] requests) {
        long resultNum=0;
        for(Request request : requests) {
            Random random = new Random(request.seed);
            for (long f = 0; f < request.loopnum; f++) {
                resultNum = (resultNum + random.nextInt(10)) % 10;
            }
        }

        Result[] results = new Result[1];
        Result resultObj = new Result(0l, resultNum);
        results[0] = resultObj;
        return results;
    }

}
