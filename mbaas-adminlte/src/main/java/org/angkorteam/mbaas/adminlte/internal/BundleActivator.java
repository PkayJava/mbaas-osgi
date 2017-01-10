package org.angkorteam.mbaas.adminlte.internal;

import org.angkorteam.mbaas.servlet.AssetController;
import org.angkorteam.mbaas.servlet.Controller;
import org.angkorteam.mbaas.servlet.MBaaSPlugin;
import org.apache.commons.lang3.RandomStringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceEvent;

import java.util.Hashtable;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class BundleActivator extends MBaaSPlugin {

    @Override
    public void registerController(BundleContext context) {
        context.registerService(Controller.class, new AssetController(context.getBundle(), RandomStringUtils.randomAlphabetic(100), "/asset", "/AdminLTE/{1}"), new Hashtable<>());
        context.registerService(Controller.class, new AssetController(context.getBundle(), RandomStringUtils.randomAlphabetic(100), "/asset", "/AdminLTE/{1}/{2}"), new Hashtable<>());
        context.registerService(Controller.class, new AssetController(context.getBundle(), RandomStringUtils.randomAlphabetic(100), "/asset", "/AdminLTE/{1}/{2}/{3}"), new Hashtable<>());
        context.registerService(Controller.class, new AssetController(context.getBundle(), RandomStringUtils.randomAlphabetic(100), "/asset", "/AdminLTE/{1}/{2}/{3}/{4}"), new Hashtable<>());
        context.registerService(Controller.class, new AssetController(context.getBundle(), RandomStringUtils.randomAlphabetic(100), "/asset", "/AdminLTE/{1}/{2}/{3}/{4}/{5}"), new Hashtable<>());
        context.registerService(Controller.class, new AssetController(context.getBundle(), RandomStringUtils.randomAlphabetic(100), "/asset", "/AdminLTE/{1}/{2}/{3}/{4}/{5}/{6}"), new Hashtable<>());
    }

    @Override
    public void registerView(BundleContext context) {

    }

    @Override
    public void bundleChanged(BundleEvent event) {

    }

    @Override
    public void serviceChanged(ServiceEvent event) {

    }
}
