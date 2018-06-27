package com.robindrew.trading.fxcm.trader.fxcm;

import static com.robindrew.common.dependency.DependencyFactory.setDependency;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.mbean.IMBeanRegistry;
import com.robindrew.common.mbean.annotated.AnnotatedMBeanRegistry;
import com.robindrew.common.properties.map.type.EnumProperty;
import com.robindrew.common.properties.map.type.FileProperty;
import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.StringProperty;
import com.robindrew.common.service.component.AbstractIdleComponent;
import com.robindrew.trading.fxcm.platform.FxcmCredentials;
import com.robindrew.trading.fxcm.platform.FxcmEnvironment;
import com.robindrew.trading.fxcm.platform.FxcmSession;
import com.robindrew.trading.fxcm.platform.FxcmTradingPlatform;
import com.robindrew.trading.fxcm.platform.IFxcmSession;
import com.robindrew.trading.fxcm.platform.IFxcmTradingPlatform;
import com.robindrew.trading.fxcm.platform.api.java.FxcmJavaService;
import com.robindrew.trading.fxcm.platform.api.java.gateway.FxcmGateway;
import com.robindrew.trading.log.FileBackedTransactionLog;

public class FxcmComponent extends AbstractIdleComponent {

	private static final Logger log = LoggerFactory.getLogger(FxcmComponent.class);

	private static final IProperty<String> propertyUsername = new StringProperty("fxcm.username");
	private static final IProperty<String> propertyPassword = new StringProperty("fxcm.password");
	private static final IProperty<FxcmEnvironment> propertyEnvironment = new EnumProperty<>(FxcmEnvironment.class, "fxcm.environment");
	private static final IProperty<File> propertyTransactionLogDir = new FileProperty("transaction.log.dir");

	@Override
	protected void startupComponent() throws Exception {
		IMBeanRegistry registry = new AnnotatedMBeanRegistry();

		String username = propertyUsername.get();
		String password = propertyPassword.get();
		FxcmEnvironment environment = propertyEnvironment.get();
		File transactionLogDir = propertyTransactionLogDir.get();

		FxcmCredentials credentials = new FxcmCredentials(username, password);

		log.info("Creating Session");
		log.info("Environment: {}", environment);
		log.info("User: {}", credentials.getUsername());
		IFxcmSession session = new FxcmSession(credentials, environment);
		setDependency(IFxcmSession.class, session);

		log.info("Creating Transaction Log");
		FileBackedTransactionLog transactionLog = new FileBackedTransactionLog(transactionLogDir);
		transactionLog.start("FxcmTransactionLog");

		log.info("Creating Trading Platform");
		FxcmGateway gateway = new FxcmGateway(transactionLog);
		FxcmJavaService service = new FxcmJavaService(session, gateway, transactionLog);
		FxcmTradingPlatform platform = new FxcmTradingPlatform(service);
		setDependency(IFxcmTradingPlatform.class, platform);

		log.info("Creating Session Manager");
		IFxcmSessionManager sessionManager = new FxcmSessionManager(platform);
		registry.register(sessionManager);
		setDependency(IFxcmSessionManager.class, sessionManager);

		log.info("Logging In...");
		service.login();
	}

	@Override
	protected void shutdownComponent() throws Exception {
		// TODO: Cancel all subscriptions here
	}

}
