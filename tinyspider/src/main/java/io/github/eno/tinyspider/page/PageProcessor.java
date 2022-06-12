package io.github.eno.tinyspider.page;

/**
 * @author hexiangtao
 * @date 2022/6/11 16:05
 */
public interface PageProcessor {

    static PageProcessor create() {
        return new PageProcessorImpl();
    }

    void process(Page page) throws Exception;


}
