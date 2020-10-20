/**
 * 功能简述：
 *
 * @author qcyki
 * @create 2020/10/20
 * @since 1.0.0
 */
public class Hello {

    public static void main(String[] args){
        int a = 10,b = 20;
        int c = a + b;
        int d = b - a;
        int e = a * b;
        int f = a / b;
        System.out.println(String.format("c=%s,d=%s,e=%s,f=%s",c,d,e,f));
        while (a > 0) {
            if(a == 1) {
                System.out.println("while要结束了！");
            }
            a--;
        }
        for (b = 10; b > 0; b--) {
            if(b == 1) {
                System.out.println("for end");
            }
        }
    }
}
