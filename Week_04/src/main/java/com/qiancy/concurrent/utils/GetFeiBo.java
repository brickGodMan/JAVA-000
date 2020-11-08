package com.qiancy.concurrent.utils;

/**
 * 功能简述：获取斐波那契数
 *
 * @author qiancy
 * @create 2020/11/8
 * @since 1.0.0
 */
public class GetFeiBo {

    /**
     *  根据n获取对应的斐波那契数
     * @param n
     * @return
     */
    public static int fiBo(int n) {
        if (n < 0) {
            return -1;
        } else if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        } else {
            int c = 0, a = 1, b = 1;
            for (int i = 3; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return c;
        }
    }
}
