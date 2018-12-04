package io.github.eno.tinyspider;

import io.github.eno.tinyspider.page.PageProcessor;
import io.github.eno.tinyspider.util.CmdArg;

public class App {

	private Scheduler scheduler;

	private App(String host) {
		scheduler = new Scheduler(host);
	}

	public static App create(String[] args) {
		CmdArg cmdArgs = CmdArg.getCmdArgs(args);
		return new App(cmdArgs.getHost());
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
		App.create(args).start(PageProcessor.create());

	}
}
