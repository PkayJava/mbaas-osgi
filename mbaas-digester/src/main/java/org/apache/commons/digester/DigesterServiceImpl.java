package org.apache.commons.digester;

import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.io.IOUtils;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.net.URL;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class DigesterServiceImpl implements DigesterService {

    @Override
    public Digester createDigester(Bundle bundle, URL url) {
        Digester digester = null;
        try {
            digester = DigesterLoader.createDigester(bundle, url);
        } catch (Throwable e) {
            String temp = null;
            try {
                temp = IOUtils.toString(url.openStream(), "UTF-8");
            } catch (IOException e1) {
            }
            throw new NullPointerException(temp);
        }
        return digester;
    }
}
