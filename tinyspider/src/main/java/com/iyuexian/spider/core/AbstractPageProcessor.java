package com.iyuexian.spider.core;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iyuexian.spider.annotation.Selector;
import com.iyuexian.spider.annotation.Site;
import com.iyuexian.spider.util.CmdArg;
import com.iyuexian.spider.util.Logger;
import com.iyuexian.spider.util.SelectorParser;
import com.iyuexian.spider.util.SelectorParser.SelectorItem;
import com.iyuexian.spider.util.StringUtil;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.ning.http.client.listener.TransferCompletionHandler;

public abstract class AbstractPageProcessor implements PageProcessor {

	private static AsyncHttpClient client = new AsyncHttpClient();

	private CmdArg cmdArg;

	public AbstractPageProcessor(CmdArg cmdArg) {
		this.cmdArg = cmdArg;
	}

	public AbstractPageProcessor() {
	}

	@Override
	public void process(String currentUrl, Document doc, List<String> pageLinks) {
		for (String url : pageLinks) {
			if (!filter(url)) {
				continue;
			}
			LinkStorage.instance().offer(url);
		}
		process(doc);

	}

	private boolean filter(String url) {

		List<String> filters = new ArrayList<String>();

		Site site = this.getClass().getAnnotation(Site.class);
		if (site != null && site.filter() != null) {
			filters.addAll(Arrays.asList(site.filter()));
		}
		if (this.cmdArg != null && cmdArg.getFilterUrl() != null && cmdArg.getFilterUrl().trim().length() > 0) {
			filters.add(cmdArg.getFilterUrl());
		}

		if (filters != null && filters.size() == 0) {
			return true;
		}

		for (String item : filters) {
			if (url.contains(item)) {
				return true;
			}
		}
		return false;
	}

	public List<String> select(Document doc) {

		List<SelectorItem> selectors = getSelectors();

		List<String> contents = new ArrayList<String>();
		for (SelectorItem selectorItem : selectors) {
			Elements eles = doc.select(selectorItem.getSelector());
			for (Element element : eles) {
				String val = getElementVal(element, selectorItem);
				if (!StringUtil.isBlank(val)) {
					Logger.info("extract:{}", val);
					contents.add(val);
				}
			}

		}
		return contents;
	}

	/**
	 * 提取元素值
	 * 
	 * @param ele
	 * @param selectorItem
	 * @return
	 */
	private String getElementVal(Element ele, SelectorItem selectorItem) {
		if (StringUtil.isBlank(selectorItem.getAttr())) {
			return ele.text();
		}
		String attr = selectorItem.getAttr();

		String dir = cmdArg == null || cmdArg.getDir().trim().length() == 0 ? "" : cmdArg.getDir();
		if (StringUtil.isBlank(dir) && this.getClass().getAnnotation(Selector.class) != null) {
			Selector selector = this.getClass().getAnnotation(Selector.class);
			dir = selector == null || StringUtil.isBlank(selector.dir()) ? dir : selector.dir();
		}

		String attrVal = ele.attr(attr);
		try {
			if (!StringUtil.isBlank(dir)) {

				downloadMedia(ele.absUrl(attr), dir);
			}
		} catch (Exception e) {
			Logger.warn("download file error,{}", e.getMessage());
		}
		return attrVal;

	}

	/**
	 * 获取选择器
	 * 
	 * @return
	 */
	private List<SelectorItem> getSelectors() {
		List<SelectorItem> list = new ArrayList<SelectorItem>();

		if (cmdArg != null && !StringUtil.isBlank(cmdArg.getSelector())) {
			list.add(SelectorParser.parse(cmdArg.getSelector()));
		}

		Selector selector = this.getClass().getAnnotation(Selector.class);
		if (selector == null || selector.value() == null || selector.value().length == 0) {
			return list;
		}
		for (String source : selector.value()) {
			list.add(SelectorParser.parse(source));
		}
		return list;

	}

	private File checkDir(String path, String dir) {
		if (StringUtil.isBlank(path) || path.startsWith("data:")) {
			return null;
		}

		File directory = new File(dir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory;
	}

	public void downloadMedia(String path, String dir) throws Exception {

		File directory = checkDir(path, dir);

		if (directory == null) {
			return;
		}

		Request req = new RequestBuilder().setUrl(path).build();
		client.executeRequest(req, new TransferCompletionHandler(true) {
			@Override
			public Response onCompleted(Response response) throws Exception {
				File f = new File(directory, genFileName(path));
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(response.getResponseBodyAsBytes());
				fos.flush();
				fos.close();
				return super.onCompleted(response);
			}

		});

	}

	public static String genFileName(String path) {
		if (StringUtil.isBlank(path)) {
			return null;
		}
		String fileName = String.valueOf(
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" + System.currentTimeMillis());
		int dotIndex = path.lastIndexOf('.');
		if (dotIndex <= 0) {
			return fileName;
		}
		return fileName + path.substring(dotIndex);

	}

	public abstract void process(Document doc);

}
