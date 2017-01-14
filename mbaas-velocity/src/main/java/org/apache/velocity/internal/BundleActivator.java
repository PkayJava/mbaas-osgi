package org.apache.velocity.internal;

import org.apache.velocity.VelocityService;
import org.apache.velocity.VelocityServiceImpl;
import org.apache.velocity.app.Velocity;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class BundleActivator implements org.osgi.framework.BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        Velocity.init();
        context.registerService(VelocityService.class, new VelocityServiceImpl(), new Hashtable<>());
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }
}
