package com.robindrew.trading.fxcm.trader.fxcm;

import com.robindrew.trading.fxcm.platform.api.java.IFxcmJavaService;

public class FxcmSessionManager implements IFxcmSessionManager {

	private final IFxcmJavaService service;

	public FxcmSessionManager(IFxcmJavaService service) {
		this.service = service;
	}

	@Override
	public IFxcmJavaService getService() {
		return service;
	}

}
