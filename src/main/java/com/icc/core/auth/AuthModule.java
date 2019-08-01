package com.icc.core.auth;


import com.axelor.auth.AuthObserver;
import com.axelor.auth.AuthRealm;
import com.axelor.auth.cas.AuthCasModule;
import com.axelor.auth.ldap.AuthLdapModule;
import com.axelor.db.JpaSecurity;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.guice.ShiroModule;
import org.apache.shiro.mgt.SecurityManager;

import javax.inject.Inject;
import javax.servlet.ServletContext;

public class AuthModule extends AbstractModule {

    private ServletContext context;

    public AuthModule() {}

    public AuthModule(ServletContext context) {
        this.context = context;
    }

    @Override
    protected final void configure() {

        // bind security service
        bind(JpaSecurity.class).toProvider(AuthSecurity.class);

        // non-web environment (cli or unit tests)
        if (context == null) {
            install(new MyShiroModule());
            return;
        }

        // CAS
        if (AuthCasModule.isEnabled()) {
            install(new AuthCasModule(context));
            return;
        }

        // LDAP
        if (AuthLdapModule.isEnabled()) {
            install(new AuthLdapModule(context));
            return;
        }

        // default
        install(new AuthWebModule(context));

        // observe authentication-related events
        bind(AuthObserver.class);
    }

    static final class MyShiroModule extends ShiroModule {

        @Override
        protected void configureShiro() {
            this.bindRealm().to(AuthRealm.class);
            this.bind(com.axelor.auth.AuthModule.Initializer.class).asEagerSingleton();
        }
    }

    @Singleton
    public static class Initializer {

        @Inject
        public Initializer(Injector injector) {
            SecurityManager sm = injector.getInstance(SecurityManager.class);
            SecurityUtils.setSecurityManager(sm);
        }
    }
}
