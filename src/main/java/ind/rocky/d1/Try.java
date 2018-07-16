package ind.rocky.d1;

public class Try {
    public static void main(String[] args) {
        RequestSource requestSource = buildRequestSource();
        Long startTimestamp = getTimestamp();
        handleRequests(requestSource);
        Long endTimestamp = getTimestamp();
        System.out.println("Total Time cost: "+(endTimestamp-startTimestamp));
    }

    private static void handleRequests(RequestSource requestSource) {
        while(requestSource.hasNext()){
            Request request = requestSource.next();
        }
    }

    private static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    private static RequestSource buildRequestSource() {
        RequestSource requestSource = new RequestSource(10000);
        for(long i=0;i<10000;i++){
            Request request = new Request(i,i);
            requestSource.put(Math.toIntExact(i), request);
        }
        return requestSource;
    }
}
