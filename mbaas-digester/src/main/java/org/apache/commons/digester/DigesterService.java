package org.apache.commons.digester;

import org.osgi.framework.Bundle;

import java.net.URL;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public interface DigesterService {

    Digester createDigester(Bundle bundle, URL url);

}
