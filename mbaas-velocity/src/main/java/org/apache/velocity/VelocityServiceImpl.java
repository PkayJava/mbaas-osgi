package org.apache.velocity;

import org.osgi.framework.Bundle;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class VelocityServiceImpl implements VelocityService {

    @Override
    public VelocityContext createVelocityContext() {
        return new VelocityContext();
    }

    @Override
    public Template createBundleTemplate(Bundle bundle, String template) {
        return new BundleTemplate(bundle, template);
    }
}
