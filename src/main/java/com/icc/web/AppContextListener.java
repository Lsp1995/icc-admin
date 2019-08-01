package com.icc.web;

import com.axelor.app.AppSettings;
import com.axelor.app.internal.AppLogger;
import com.axelor.meta.loader.ViewWatcher;
import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.ResourceMethodRegistry;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.plugins.guice.ModuleProcessor;
import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.SessionCookieConfig;

/** Servlet context listener. */
public class AppContextListener extends GuiceServletContextListener {

  private ResteasyDeployment deployment;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    AppLogger.install();
    super.contextInitialized(servletContextEvent);

    final ServletContext context = servletContextEvent.getServletContext();
    final ListenerBootstrap config = new ListenerBootstrap(context);
    final Injector injector = (Injector) context.getAttribute(Injector.class.getName());

    final SessionCookieConfig cookieConfig = context.getSessionCookieConfig();

    cookieConfig.setHttpOnly(true);
    cookieConfig.setSecure(AppSettings.get().getBoolean("session.cookie.secure", false));

    deployment = config.createDeployment();

    // use custom registry for hotswap-agent support
    final ResteasyProviderFactory providerFactory = ResteasyProviderFactory.getInstance();
    final ResourceMethodRegistry registry =
        new ResourceMethodRegistry(providerFactory) {

          @Override
          @SuppressWarnings("all")
          public void addPerRequestResource(Class clazz) {
            final Binding<?> binding = injector.getBinding(clazz);
            if (binding == null) {
              super.addPerRequestResource(clazz);
            } else {
              super.addResourceFactory(new GuiceResourceFactory(binding.getProvider(), clazz));
            }
          }
        };
    final Dispatcher dispatcher = new SynchronousDispatcher(providerFactory, registry);

    deployment.setProviderFactory(providerFactory);
    deployment.setAsyncJobServiceEnabled(false);
    deployment.setDispatcher(dispatcher);
    deployment.start();

    context.setAttribute(ResteasyProviderFactory.class.getName(), providerFactory);
    context.setAttribute(Dispatcher.class.getName(), dispatcher);
    context.setAttribute(Registry.class.getName(), registry);

    final ModuleProcessor processor = new ModuleProcessor(registry, providerFactory);

    // process all injectors
    Injector current = injector;
    while (current != null) {
      processor.processInjector(current);
      current = injector.getParent();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    ViewWatcher.getInstance().stop();
    deployment.stop();
    super.contextDestroyed(servletContextEvent);
    AppLogger.uninstall();
  }

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new com.icc.web.AppServletModule());
  }
}
