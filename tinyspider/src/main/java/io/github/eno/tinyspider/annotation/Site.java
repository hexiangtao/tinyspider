package io.github.eno.tinyspider.annotation;

import java.lang.annotation.*;

/**
 * 定义要爬取的网站
 *
 * @author hexiangtao
 * @date 2022-06-12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Site {


    /**
     * 网站地址
     *
     * @return
     */
    String host();

    /**
     * url过滤内容
     *
     * @return
     */
    String[] hostContain() default {};

    /**
     * 爬虫线程池线程数
     *
     * @return
     */
    int threads() default 1;
}
