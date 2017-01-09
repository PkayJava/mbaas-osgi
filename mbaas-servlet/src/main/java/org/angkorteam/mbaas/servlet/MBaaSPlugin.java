package org.angkorteam.mbaas.servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceListener;

public abstract class MBaaSPlugin implements BundleActivator, BundleListener, ServiceListener {

    @Override
    public final void start(BundleContext context) throws Exception {
        context.addBundleListener(this);
        context.addServiceListener(this);
        registerAsset(context);
        registerView(context);
        registerController(context);
    }

    @Override
    public final void stop(BundleContext context) throws Exception {

    }

    public abstract void registerController(BundleContext context);

    public abstract void registerAsset(BundleContext context);

    public abstract void registerView(BundleContext context);

}
