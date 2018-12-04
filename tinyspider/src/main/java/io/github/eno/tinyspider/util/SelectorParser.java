package io.github.eno.tinyspider.util;

public class SelectorParser {

	public static SelectorItem parse(String source) {

		if (source == null || source.trim().length() == 0) {
			return null;
		}
		SelectorItem item = new SelectorItem(source);
		if (source.indexOf('[') <= 0 || source.indexOf(']') <= 0) {
			item.setSelector(source);
			return item;
		}

		int begin = source.indexOf('['), end = source.indexOf(']');
		String selector = source.substring(0, begin);
		String attr = source.substring(begin + 1, end);

		item.setSelector(selector);
		item.setAttr(attr);
		boolean downlaod = source.endsWith(".download") ? true : false;
		item.setDownlaod(downlaod);
		return item;

	}

	public static class SelectorItem {
		private String source;
		private String selector;
		private String attr;
		private boolean downlaod;

		private SelectorItem(String source) {
			super();
			this.source = source;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getSelector() {
			return selector;
		}

		public void setSelector(String selector) {
			this.selector = selector;
		}

		public String getAttr() {
			return attr;
		}

		public void setAttr(String attr) {
			this.attr = attr;
		}

		public boolean isDownlaod() {
			return downlaod;
		}

		public void setDownlaod(boolean downlaod) {
			this.downlaod = downlaod;
		}

	}
}
