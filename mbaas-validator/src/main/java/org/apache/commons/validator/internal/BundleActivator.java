package org.apache.commons.validator.internal;

import org.apache.commons.validator.ValidatorService;
import org.apache.commons.validator.ValidatorServiceImpl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Hashtable;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class BundleActivator implements org.osgi.framework.BundleActivator {

    private ServiceRegistration<ValidatorService> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        this.registration = context.registerService(ValidatorService.class, new ValidatorServiceImpl(context), new Hashtable<>());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        this.registration.unregister();
    }
}
