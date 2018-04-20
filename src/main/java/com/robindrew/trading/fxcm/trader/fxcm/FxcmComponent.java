package com.robindrew.trading.fxcm.trader.fxcm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.mbean.IMBeanRegistry;
import com.robindrew.common.mbean.annotated.AnnotatedMBeanRegistry;
import com.robindrew.common.properties.map.type.EnumProperty;
import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.StringProperty;
import com.robindrew.common.service.component.AbstractIdleComponent;
import com.robindrew.trading.fxcm.platform.FxcmCredentials;
import com.robindrew.trading.fxcm.platform.FxcmEnvironment;
import com.robindrew.trading.fxcm.platform.FxcmSession;
import com.robindrew.trading.fxcm.trader.fxcm.session.SessionManager;

public class FxcmComponent extends AbstractIdleComponent {

	private static final Logger log = LoggerFactory.getLogger(FxcmComponent.class);

	private static final IProperty<String> propertyUsername = new StringProperty("fxcm.username");
	private static final IProperty<String> propertyPassword = new StringProperty("fxcm.password");
	private static final IProperty<FxcmEnvironment> propertyEnvironment = new EnumProperty<>(FxcmEnvironment.class, "fxcm.environment");

	@Override
	protected void startupComponent() throws Exception {
		IMBeanRegistry registry = new AnnotatedMBeanRegistry();

		String username = propertyUsername.get();
		String password = propertyPassword.get();
		FxcmEnvironment environment = propertyEnvironment.get();

		FxcmCredentials credentials = new FxcmCredentials(username, password);
		FxcmSession session = new FxcmSession(credentials, environment);

		registry.register(new SessionManager(session));
	}

	@Override
	protected void shutdownComponent() throws Exception {
		// TODO: Cancel all subscriptions here
	}

}
