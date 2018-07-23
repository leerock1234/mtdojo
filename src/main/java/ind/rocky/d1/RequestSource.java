package ind.rocky.d1;

public class RequestSource {
    Request[] requests;
    int index=0;

    public RequestSource(int len){
        requests = new Request[len];
    }

    public void put(int index, Request request){
        requests[index] = request;
    }

    public boolean hasNext() {
        return index<requests.length;
    }

    public Request next() {
        return requests[index++];
    }
}
