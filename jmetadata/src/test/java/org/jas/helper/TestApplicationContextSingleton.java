package org.jas.helper;

import static org.junit.Assert.assertNotNull;

import org.asmatron.messengine.engines.DefaultEngine;
import org.jas.gui.LoginWindow;
import org.jas.gui.MainWindow;
import org.jas.helper.ApplicationContextSingleton;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextSingleton {

	@Test
	public void shouldCreateAnApplicationContext() throws Exception {
		ConfigurableApplicationContext applicationContext = ApplicationContextSingleton.getApplicationContext();
		DefaultEngine defaultEngine = applicationContext.getBean(DefaultEngine.class);
		MainWindow mainWindow = applicationContext.getBean(MainWindow.class);
		LoginWindow loginWindow = applicationContext.getBean(LoginWindow.class);
		assertNotNull(defaultEngine);
		assertNotNull(mainWindow);
		assertNotNull(loginWindow);
	}

}
