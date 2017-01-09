package org.angkorteam.mbaas.servlet.internal;

import org.angkorteam.mbaas.servlet.MBaaS;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public class MBaaSImpl implements MBaaS {

    @Override
    public String getVersion() {
        return "1.0.0-SNAPSHOT";
    }

}
