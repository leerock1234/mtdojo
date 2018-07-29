package ind.rocky.d1;

public class TryMultiThread {

    public static final int RNUM = 100000000;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        TryCore.handle(MulTdSimpleHandler.class);
    }


}
