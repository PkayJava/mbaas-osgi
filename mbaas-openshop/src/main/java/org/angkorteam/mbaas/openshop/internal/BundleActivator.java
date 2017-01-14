package org.angkorteam.mbaas.openshop.internal;

import org.angkorteam.mbaas.openshop.block.MenuBlockView;
import org.angkorteam.mbaas.openshop.controller.IndexController;
import org.angkorteam.mbaas.openshop.controller.RegisterGetController;
import org.angkorteam.mbaas.openshop.controller.RegisterPostController;
import org.angkorteam.mbaas.openshop.layout.*;
import org.angkorteam.mbaas.openshop.page.IndexPageView;
import org.angkorteam.mbaas.openshop.page.LoginPageView;
import org.angkorteam.mbaas.openshop.page.RegisterPageView;
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
        context.registerService(Controller.class, new IndexController(), new Hashtable<>());
        context.registerService(Controller.class, new RegisterGetController(), new Hashtable<>());
        context.registerService(Controller.class, new RegisterPostController(context), new Hashtable<>());
    }

    @Override
    public void registerView(BundleContext context) {
        context.registerService(View.class, new MenuBlockView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new AdminLayoutView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new LoginLayoutView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new VendorLayoutView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new WebsiteLayoutView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new RegisterLayoutView(context.getBundle()), new Hashtable<>());

        context.registerService(View.class, new IndexPageView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new LoginPageView(context.getBundle()), new Hashtable<>());
        context.registerService(View.class, new RegisterPageView(context.getBundle()), new Hashtable<>());
    }

    @Override
    public void bundleChanged(BundleEvent event) {

    }

    @Override
    public void serviceChanged(ServiceEvent event) {

    }
}
