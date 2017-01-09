package org.angkorteam.mbaas.servlet;

import org.osgi.framework.Bundle;

/**
 * Created by socheatkhauv on 1/9/17.
 */
public final class Asset {

    private final Bundle bundle;

    private final String id;

    private final String path;

    public Asset(Bundle bundle, String id, String path) {
        this.bundle = bundle;
        this.id = id;
        this.path = path;
    }

    public final Bundle getBundle() {
        return bundle;
    }

    public final String getId() {
        return id;
    }

    public final String getPath() {
        return path;
    }

}
