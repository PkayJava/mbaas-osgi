package org.angkorteam.mbaas.servlet;

import org.osgi.framework.Bundle;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public final class AssetMapping {

    private final Bundle bundle;

    private final String path;

    public AssetMapping(Bundle bundle, String path) {
        this.bundle = bundle;
        this.path = path;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public String getPath() {
        return path;
    }

}
