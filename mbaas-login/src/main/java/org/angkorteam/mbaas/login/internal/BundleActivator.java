package org.angkorteam.mbaas.login.internal;

import org.angkorteam.mbaas.login.block.MenuView;
import org.angkorteam.mbaas.login.controller.LoginController;
import org.angkorteam.mbaas.login.view.LoginView;
import org.angkorteam.mbaas.servlet.Controller;
import org.angkorteam.mbaas.servlet.View;
import org.osgi.framework.*;

import java.util.Hashtable;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class BundleActivator implements org.osgi.framework.BundleActivator, ServiceListener, BundleListener {

    private BundleContext context;

    public void start(BundleContext context) throws Exception {
        this.context = context;
        context.addServiceListener(this);
        context.addBundleListener(this);
        context.registerService(View.class, new LoginView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new MenuView(context.getBundle()), new Hashtable<>());
        context.registerService(Controller.class, new LoginController(context.getBundle()), new Hashtable<>());
    }

    public void stop(BundleContext context) throws Exception {
    }

    @Override
    public void bundleChanged(BundleEvent event) {
    }

    @Override
    public void serviceChanged(ServiceEvent event) {
    }
}
