package org.apache.velocity.runtime.resource.loader;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.util.ExtProperties;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class BundleResourceLoader extends ResourceLoader {

    private Bundle bundle;

    public BundleResourceLoader(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void init(ExtProperties configuration) {
    }

    @Override
    public Reader getResourceReader(String source, String encoding) throws ResourceNotFoundException {
        if (this.bundle == null) {
            throw new ResourceNotFoundException("bundle is null");
        }
        URL url = this.bundle.getResource(source);
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch (IOException e) {
            throw new ResourceNotFoundException(bundle.getSymbolicName() + ">" + source);
        }
        return new InputStreamReader(inputStream);
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        return false;
    }

    @Override
    public long getLastModified(Resource resource) {
        return this.bundle.getLastModified();
    }
}
