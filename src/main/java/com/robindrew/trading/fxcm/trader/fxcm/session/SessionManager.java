package com.robindrew.trading.fxcm.trader.fxcm.session;

import com.robindrew.trading.fxcm.platform.FxcmSession;

public class SessionManager implements SessionManagerMBean {

	private final FxcmSession session;

	public SessionManager(FxcmSession session) {
		this.session = session;
	}

	@Override
	public String getUsername() {
		return session.getCredentials().getUsername();
	}

}
