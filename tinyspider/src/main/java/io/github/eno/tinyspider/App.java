package io.github.eno.tinyspider;

import io.github.eno.tinyspider.page.PageProcessor;

public class App {

	private Scheduler scheduler;

	private App(String host) {
		scheduler = new Scheduler(host);
	}

	public static App create(String siteUrl) {
		return new App(siteUrl);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void start(PageProcessor processor) {
		scheduler.start(processor);
	}

	public static void main(String[] args) {
		App.create("https://www.163.com/").start(PageProcessor.create());

	}
}
