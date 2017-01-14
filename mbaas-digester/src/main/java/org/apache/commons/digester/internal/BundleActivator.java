package org.apache.commons.digester.internal;

import org.apache.commons.digester.DigesterService;
import org.apache.commons.digester.DigesterServiceImpl;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class BundleActivator implements org.osgi.framework.BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        context.registerService(DigesterService.class, new DigesterServiceImpl(), new Hashtable<>());
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }
}
