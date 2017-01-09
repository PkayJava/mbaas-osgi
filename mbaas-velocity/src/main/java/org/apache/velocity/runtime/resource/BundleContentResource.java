package org.apache.velocity.runtime.resource;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.util.StringBuilderWriter;
import org.osgi.framework.Bundle;

import java.io.BufferedReader;
import java.io.Writer;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class BundleContentResource extends ContentResource {

    private Bundle bundle;

    public BundleContentResource(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public boolean process() throws ResourceNotFoundException {
        BufferedReader reader = null;

        try {
            Writer sw = new StringBuilderWriter();

            reader = new BufferedReader(resourceLoader.getResourceReader(bundle, name, encoding));

            char buf[] = new char[1024];
            int len = 0;

            while ((len = reader.read(buf, 0, 1024)) != -1)
                sw.write(buf, 0, len);

            setData(sw.toString());

            return true;
        } catch (ResourceNotFoundException e) {
            // Tell the ContentManager to continue to look through any
            // remaining configured ResourceLoaders.
            throw e;
        } catch (Exception e) {
            String msg = "Cannot process content resource";
            log.error(msg, e);
            throw new VelocityException(msg, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
