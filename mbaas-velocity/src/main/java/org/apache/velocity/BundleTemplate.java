package org.apache.velocity;

import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.resource.ResourceManager;
import org.apache.velocity.runtime.resource.loader.BundleResourceLoader;
import org.osgi.framework.Bundle;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class BundleTemplate extends Template {

    public BundleTemplate(Bundle bundle, String template) {
        this.type = ResourceManager.RESOURCE_TEMPLATE;
        this.name = template;
        this.rsvc = RuntimeSingleton.getRuntimeServices();
        this.resourceLoader = new BundleResourceLoader(bundle);
        if (!process()) {
            throw new IllegalArgumentException(bundle.getSymbolicName() + ">" + template);
        }
    }
}
