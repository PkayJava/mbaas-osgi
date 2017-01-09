package org.angkorteam.mbaas.servlet.internal;

import org.angkorteam.mbaas.servlet.View;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.resource.ResourceManager;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class BundleTemplate extends Template {

    public BundleTemplate(View view) {
        this.type = ResourceManager.RESOURCE_TEMPLATE;
        this.name = view.getTemplate();
        this.rsvc = RuntimeSingleton.getRuntimeServices();
        this.resourceLoader = new BundleResourceLoader(view.getBundle());
        if (!process()) {
            throw new IllegalArgumentException(view.getBundle().getSymbolicName() + ">" + view.getTemplate());
        }
    }
}
