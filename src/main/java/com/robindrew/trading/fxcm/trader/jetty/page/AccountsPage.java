package com.robindrew.trading.fxcm.trader.jetty.page;

import static com.robindrew.common.dependency.DependencyFactory.getDependency;

import java.util.Map;

import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.service.component.jetty.handler.page.AbstractServicePage;
import com.robindrew.trading.fxcm.platform.IFxcmSession;
import com.robindrew.trading.fxcm.platform.IFxcmTradingPlatform;
import com.robindrew.trading.fxcm.trader.fxcm.IFxcmSessionManager;
import com.robindrew.trading.platform.account.IAccountService;

public class AccountsPage extends AbstractServicePage {

	public AccountsPage(IVelocityHttpContext context, String templateName) {
		super(context, templateName);
	}

	@Override
	protected void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap) {
		super.execute(request, response, dataMap);

		IFxcmSessionManager manager = getDependency(IFxcmSessionManager.class);
		IFxcmTradingPlatform platform = manager.getPlatform();
		IFxcmSession session = platform.getSession();
		IAccountService account = platform.getAccountService();
		account.getAccountId();
		account.getBalance();

		dataMap.put("user", session.getCredentials().getUsername());
		dataMap.put("environment", session.getEnvironment());
	}
}
