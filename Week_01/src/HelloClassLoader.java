
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 功能简述：
 *
 * @author qcyki
 * @create 2020/10/20
 * @since 1.0.0
 */
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            Class<?> clazz = new HelloClassLoader().findClass("Hello");
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("hello");
            method.invoke(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] b = getByte();
            return defineClass(name,b,0,b.length);
        } catch (IOException e) {
            e.printStackTrace();
            return super.findClass(name);
        }
    }

    private static byte[] getByte() throws IOException {
        String path = new File("").getCanonicalPath().replaceAll("\\\\","/") + "/src/Hello.xlass";
        File file = new File(path);
        byte[] buf = new byte[(int) file.length()];
        int resultLe = new FileInputStream(file).read(buf);
        for (int i = 0; i < resultLe; i++) {
            buf[i] = (byte) (255 - buf[i]);
        }
        return buf;
    }
}
