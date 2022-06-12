package io.github.eno.tinyspider.annotation;

import io.github.eno.tinyspider.downloader.DefaultMediaDownloader;
import io.github.eno.tinyspider.downloader.MediaDownloader;
import io.github.eno.tinyspider.page.DefaultElementParser;
import io.github.eno.tinyspider.page.ElementParser;

import java.lang.annotation.*;

/**
 * document 元素选择器
 *
 * @author hexiangtao
 * @date 2022-06-12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Selector {

    /**
     * 要提取的元素,比如div,script
     *
     * @return
     */
    String[] value();

    /***
     * 下载器
     * @return
     */
    Class<? extends MediaDownloader> downloader() default DefaultMediaDownloader.class;

    boolean download() default false;


    /**
     * 元素内容解析器，复杂情况需要自己定义解析器
     *
     * @return
     */
    Class<? extends ElementParser> parser() default DefaultElementParser.class;

    /**
     * 下载资源本地保存定目录
     *
     * @return
     */
    String localDir() default "";
}
