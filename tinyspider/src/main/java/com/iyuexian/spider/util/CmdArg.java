package com.iyuexian.spider.util;

import com.iyuexian.spider.util.Logger;

public class CmdArg {

	public static CmdArg getCmdArgs(String host, int threadNum, String selector, String filterUrl,String dir) {
		return new CmdArg(host, threadNum, selector, filterUrl,dir);

	}

	/**
	 * 
	 * @param args
	 *            args[0] host 域名 默认 www.baidu.com args[1] threadNum 线程数 默认 1
	 *            args[2] selecotr 选择器 默认 空
	 * @return
	 */
	public static CmdArg getCmdArgs(String[] args) {

		String host = "www.baidu.com";
		if (args == null || args.length == 0) {
			Logger.warn("未输入网站路径,默认使用 www.baidu.com");
		} else {
			host = args[0];
		}

		int threadNum = setThreadNum(args, 1);
		String selector = "", filterUrl = "", dir = "";

		if (args.length > 2) {
			selector = args[2] != null && args[2].trim().length() > 0 ? args[2] : "";
		} else {
			selector = "title";
			Logger.warn("未设置提取标签,默认使用 {}", selector);
		}

		if (args.length > 3) {
			filterUrl = args[3] != null && args[3].trim().length() > 0 ? args[3] : "";
		} else {
			filterUrl = host;
			Logger.warn("未设置匹配url,默认使用 {}", filterUrl);
		}

		setLoggerLevel(args, 4);

		if (args.length > 5) {
			dir = args[5] != null && args[5].trim().length() > 0 ? args[5] : "";
		}

		return new CmdArg(host, threadNum, selector, filterUrl, dir);

	}

	private static int setThreadNum(String[] args, int index) {
		final int defaultNum = 1;
		final int maxNum = 3000;
		if (args == null || args.length <= index) {
			Logger.info("未设置线程数，使用默认值:{}", defaultNum);
			return defaultNum;
		}

		String num = args[index];
		if (num == null || num.trim().length() == 0) {
			Logger.warn("未设置线程数，使用默认值:{}", defaultNum);
			return defaultNum;
		}
		try {
			int lNum = Integer.parseInt(num);
			if (lNum <= 0 || lNum > maxNum) {
				Logger.warn("线程数设置区间只能在1-{}之间，使用默认值:{}", maxNum, defaultNum);
				return defaultNum;
			}
			return lNum;

		} catch (Exception ex) {
			Logger.warn("线程数值不合法,{},使用默认,使用默认值:{}", num, defaultNum);
			return defaultNum;
		}

	}

	private static void setLoggerLevel(String[] args, int index) {
		try {
			if (args.length <= index) {
				return;
			}
			int lNum = Integer.parseInt(args[index]);
			Logger.setLevel(lNum);
		} catch (Exception ex) {
			Logger.error("invalid level val of {}", args[index]);
		}

	}

	private String host = "";
	private int threadNum;
	private String selector;
	private String filterUrl;
	private String dir;

	private CmdArg() {
	}

	private CmdArg(String host, int threadNum, String selector, String filterUrl, String dir) {
		this.host = host;
		this.threadNum = threadNum;
		this.selector = selector;
		this.filterUrl = filterUrl;
		this.dir = dir;
	}

	public String getHost() {
		return host;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public String getSelector() {
		return selector;
	}

	public String getFilterUrl() {
		return filterUrl;
	}

	public String getDir() {
		return dir;
	}
	

}
