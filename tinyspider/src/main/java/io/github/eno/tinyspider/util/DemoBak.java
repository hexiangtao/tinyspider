package io.github.eno.tinyspider.util;
//package io.github.eno.tinyspider;
//
//import org.jsoup.nodes.Document;
//
//import com.iyuexian.spider.core.Spider;
//
//import io.github.eno.tinyspider.annotation.Selector;
//import io.github.eno.tinyspider.annotation.Site;
//import io.github.eno.tinyspider.util.Logger;
//
//@Selector(value = "img[src]", dir = "C:/doubannv6")
//@Site(host = "http://www.doubannv.net/")
////@Site(host = "http://108.170.9.194/forum.php")
//
//public class DemoBak extends AbstractPageProcessorBak {
//
//	public static void main(String[] args) {
//		Spider.create().thread(300).processor(new DemoBak()).start();
//	}
//
//	@Override
//	public void process(Document doc) {
//		Logger.info("begin process:{}", doc.baseUri());
//		select(doc);
//
//	}
//
//}
