package org.angkorteam.mbaas.login.internal;

import org.angkorteam.mbaas.login.block.MenuView;
import org.angkorteam.mbaas.login.controller.LoginController;
import org.angkorteam.mbaas.login.view.LoginView;
import org.angkorteam.mbaas.servlet.Controller;
import org.angkorteam.mbaas.servlet.MBaaSPlugin;
import org.angkorteam.mbaas.servlet.View;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceEvent;

import java.util.Hashtable;

/**
 * Created by socheatkhauv on 1/7/17.
 */
public class BundleActivator extends MBaaSPlugin {

    @Override
    public void registerController(BundleContext context) {
        context.registerService(Controller.class, new LoginController(), new Hashtable<>());
    }

    @Override
    public void registerView(BundleContext context) {
        context.registerService(View.class, new LoginView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new MenuView(context.getBundle()), new Hashtable<>());
    }

    @Override
    public void bundleChanged(BundleEvent event) {

    }

    @Override
    public void serviceChanged(ServiceEvent event) {

    }
}
