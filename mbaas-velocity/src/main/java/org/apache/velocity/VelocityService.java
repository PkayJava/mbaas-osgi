package org.apache.velocity;

import org.osgi.framework.Bundle;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public interface VelocityService {

    VelocityContext createVelocityContext();

    Template createBundleTemplate(Bundle bundle, String template);
}
