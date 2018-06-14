package com.robindrew.trading.fxcm.trader.fxcm;

import static com.robindrew.common.util.Check.notNull;

import com.robindrew.trading.fxcm.platform.IFxcmTradingPlatform;

public class FxcmSessionManager implements IFxcmSessionManager, FxcmSessionManagerMBean {

	private final IFxcmTradingPlatform platform;

	public FxcmSessionManager(IFxcmTradingPlatform platform) {
		this.platform = notNull("platform", platform);
	}

	@Override
	public IFxcmTradingPlatform getPlatform() {
		return platform;
	}

	@Override
	public String getUser() {
		return platform.getSession().getCredentials().getUsername();
	}

	@Override
	public String getStation() {
		return platform.getSession().getEnvironment().getStation();
	}

	@Override
	public String getServer() {
		return platform.getSession().getEnvironment().getServer();
	}

}
