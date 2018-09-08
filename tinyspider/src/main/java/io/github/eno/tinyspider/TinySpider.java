package io.github.eno.tinyspider;

import io.github.eno.tinyspider.core.impl.Scheduler;

public class TinySpider {

	private Scheduler scheduler;

	private TinySpider(String host) {
		scheduler = new Scheduler(host);
	}

	public static TinySpider create(String siteUrl) {
		return new TinySpider(siteUrl);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void start() {
		scheduler.start();
	}

}
