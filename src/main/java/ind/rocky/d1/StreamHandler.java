package ind.rocky.d1;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StreamHandler implements Handler {

    @Override
    public Result[] handleRequests(Request[] requests) {
        return Arrays.stream(requests).parallel().map(StreamHandler::handle).collect(Collectors.toList()).toArray(new Result[]{});
    }

    static Result handle(Request request){
        return new Result(request.requestId, request.num * 2 % 10);
    }

}
