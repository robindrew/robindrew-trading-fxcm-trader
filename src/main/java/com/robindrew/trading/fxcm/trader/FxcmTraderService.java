package com.robindrew.trading.fxcm.trader;

import com.robindrew.common.service.AbstractService;
import com.robindrew.common.service.component.heartbeat.HeartbeatComponent;
import com.robindrew.common.service.component.logging.LoggingComponent;
import com.robindrew.common.service.component.properties.PropertiesComponent;
import com.robindrew.common.service.component.stats.StatsComponent;
import com.robindrew.trading.fxcm.trader.fxcm.FxcmComponent;
import com.robindrew.trading.fxcm.trader.jetty.JettyComponent;

public class FxcmTraderService extends AbstractService {

	/**
	 * Entry point for the IG Index Trader Service.
	 */
	public static void main(String[] args) {
		FxcmTraderService service = new FxcmTraderService(args);
		service.startAsync();
	}

	private final JettyComponent jetty = new JettyComponent();
	private final HeartbeatComponent heartbeat = new HeartbeatComponent();
	private final PropertiesComponent properties = new PropertiesComponent();
	private final LoggingComponent logging = new LoggingComponent();
	private final StatsComponent stats = new StatsComponent();
	private final FxcmComponent fxcm = new FxcmComponent();

	public FxcmTraderService(String[] args) {
		super(args);
	}

	@Override
	protected void startupService() throws Exception {
		start(properties);
		start(logging);
		start(heartbeat);
		start(stats);
		start(jetty);
		start(fxcm);
	}

	@Override
	protected void shutdownService() throws Exception {
		stop(jetty);
		stop(heartbeat);
	}
}
