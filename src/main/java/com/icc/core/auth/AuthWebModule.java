package com.icc.core.auth;

import com.axelor.auth.AuthFilter;
import com.axelor.auth.AuthRealm;
import com.google.inject.Key;
import org.apache.shiro.guice.web.ShiroWebModule;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

public class AuthWebModule extends ShiroWebModule {

    private String prefix = "/ws";

    public AuthWebModule(ServletContext servletContext) {
        super(servletContext);
        prefix = servletContext.getInitParameter("resteasy.servlet.mapping.prefix");
    }

    @Override
    protected final void configureShiroWeb() {
        this.configureAnon();
        this.configureAuth();
    }

    @SuppressWarnings("unchecked")
    protected void addFilterChain(String pattern, Key<? extends Filter> key) {
        super.addFilterChain(pattern, key);
    }

    protected void configureAnon() {
        this.addFilterChain("/public/**", ANON);
        this.addFilterChain("/dist/**", ANON);
        this.addFilterChain("/lib/**", ANON);
        this.addFilterChain("/img/**", ANON);
        this.addFilterChain("/ico/**", ANON);
        this.addFilterChain("/css/**", ANON);
        this.addFilterChain("/js/**", ANON);
        this.addFilterChain("/error.jsp", ANON);
        this.addFilterChain(prefix + "/rs/**", ANON);
    }

    protected void configureAuth() {
        this.bindRealm().to(AuthRealm.class);
        this.addFilterChain("/logout", LOGOUT);
        this.addFilterChain("/**", Key.get(AuthFilter.class));
    }
}

