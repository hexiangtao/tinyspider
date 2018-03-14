package com.iyuexian.wechat4j.crawler;

public class CmdArgInfo {

	public static CmdArgInfo getCMDArgs(String host, String savePath, int threadNum) {

		return new CmdArgInfo(host, savePath, threadNum);

	}

	public static CmdArgInfo getCMDArgs(String[] args) {

		String host = "www.baidu.com";
		if (args == null || args.length == 0) {
			Logger.warn("未输入网站路径,默认使用 www.baidu.com");
		} else {
			host = args[0];
		}

		int threadNum = setThreadNum(args);
		String savePath = "";

		if (args.length > 2) {
			savePath = args[2] != null && args[2].trim().length() > 0 ? args[2] : "";
		}
		return new CmdArgInfo(host, savePath, threadNum);

	}

	private static int setThreadNum(String[] args) {
		final int defaultNum = 200;
		final int maxNum = 3000;
		if (args == null || args.length < 2) {
			Logger.info("未设置线程数，使用默认值:{}", defaultNum);
			return defaultNum;
		}

		String num = args[1];
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

	private String host = "";
	private String savePath = "";
	private int threadNum;

	private CmdArgInfo() {
	}

	private CmdArgInfo(String host, String savePath, int threadNum) {
		this.host = host;
		this.savePath = savePath;
		this.threadNum = threadNum;
	}

	public String getHost() {
		return host;
	}

	public String getSavePath() {
		return savePath;
	}

	public int getThreadNum() {
		return threadNum;
	}

}
