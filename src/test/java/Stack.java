/**
 * @author hexiangtao
 * @date 2023/3/2 23:06
 */
public class Stack {

    public void test() {
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        for (StackTraceElement s : stackTrace) {
            System.out.println(s.toString());
        }
    }
}
