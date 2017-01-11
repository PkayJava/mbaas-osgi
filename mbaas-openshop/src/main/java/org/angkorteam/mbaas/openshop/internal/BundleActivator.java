package org.angkorteam.mbaas.login.internal;

import org.angkorteam.mbaas.login.controller.IndexController;
import org.angkorteam.mbaas.servlet.Controller;
import org.angkorteam.mbaas.servlet.MBaaSPlugin;
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
        context.registerService(Controller.class, new IndexController(), new Hashtable<>());
    }

    @Override
    public void registerView(BundleContext context) {
        // context.registerService(View.class, new MenuBlockView(context.getBundle()), new Hashtable<>());
    }

    @Override
    public void bundleChanged(BundleEvent event) {

    }

    @Override
    public void serviceChanged(ServiceEvent event) {

    }
}
