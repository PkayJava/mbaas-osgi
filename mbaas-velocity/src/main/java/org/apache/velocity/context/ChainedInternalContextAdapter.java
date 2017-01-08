package org.apache.velocity.context;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.util.introspection.IntrospectionCacheData;

import java.util.List;

/**
 * This is an abstract internal-use-only context implementation to be
 * used as a subclass for other internal-use-only contexts that wrap
 * other internal-use-only contexts.
 * <p>
 * We use this context to make it easier to chain an existing context
 * as part of a new context implementation.  It just delegates everything
 * to the inner/parent context. Subclasses then only need to override
 * the methods relevant to them.
 *
 * @author Nathan Bubna
 * @version $Id: ChainedInternalContextAdapter.java 685724 2008-08-13 23:12:12Z nbubna $
 * @since 1.6
 */
public abstract class ChainedInternalContextAdapter implements InternalContextAdapter {
    /**
     * the parent context
     */
    protected InternalContextAdapter innerContext = null;

    /**
     * CTOR, wraps an ICA
     *
     * @param inner context
     */
    public ChainedInternalContextAdapter(InternalContextAdapter inner) {
        innerContext = inner;
    }

    /**
     * Return the inner / user context.
     *
     * @return The inner / user context.
     */
    public Context getInternalUserContext() {
        return innerContext.getInternalUserContext();
    }

    /**
     * @see InternalWrapperContext#getBaseContext()
     */
    public InternalContextAdapter getBaseContext() {
        return innerContext.getBaseContext();
    }

    /**
     * Retrieves from parent context.
     *
     * @param key name of item to get
     * @return stored object or null
     */
    public Object get(String key) {
        return innerContext.get(key);
    }

    /**
     * Put method also stores values in parent context
     *
     * @param key   name of item to set
     * @param value object to set to key
     * @return old stored object
     */
    public Object put(String key, Object value) {
        /*
         * just put in the local context
         */
        return innerContext.put(key, value);
    }

    /**
     * @see Context#containsKey(Object)
     */
    public boolean containsKey(Object key) {
        return innerContext.containsKey(key);
    }

    /**
     * @see Context#getKeys()
     */
    public Object[] getKeys() {
        return innerContext.getKeys();
    }

    /**
     * @see Context#remove(Object)
     */
    public Object remove(Object key) {
        return innerContext.remove(key);
    }

    /**
     * @see InternalHousekeepingContext#pushCurrentTemplateName(String)
     */
    public void pushCurrentTemplateName(String s) {
        innerContext.pushCurrentTemplateName(s);
    }

    /**
     * @see InternalHousekeepingContext#popCurrentTemplateName()
     */
    public void popCurrentTemplateName() {
        innerContext.popCurrentTemplateName();
    }

    /**
     * @see InternalHousekeepingContext#getCurrentTemplateName()
     */
    public String getCurrentTemplateName() {
        return innerContext.getCurrentTemplateName();
    }

    /**
     * @see InternalHousekeepingContext#getTemplateNameStack()
     */
    public Object[] getTemplateNameStack() {
        return innerContext.getTemplateNameStack();
    }

    /**
     * @see InternalHousekeepingContext#pushCurrentMacroName(String)
     */
    public void pushCurrentMacroName(String s) {
        innerContext.pushCurrentMacroName(s);
    }

    /**
     * @see InternalHousekeepingContext#popCurrentMacroName()
     */
    public void popCurrentMacroName() {
        innerContext.popCurrentMacroName();
    }

    /**
     * @see InternalHousekeepingContext#getCurrentMacroName()
     */
    public String getCurrentMacroName() {
        return innerContext.getCurrentMacroName();
    }

    /**
     * @see InternalHousekeepingContext#getCurrentMacroCallDepth()
     */
    public int getCurrentMacroCallDepth() {
        return innerContext.getCurrentMacroCallDepth();
    }

    /**
     * @see InternalHousekeepingContext#getMacroNameStack()
     */
    public Object[] getMacroNameStack() {
        return innerContext.getMacroNameStack();
    }

    /**
     * @see InternalHousekeepingContext#icacheGet(Object)
     */
    public IntrospectionCacheData icacheGet(Object key) {
        return innerContext.icacheGet(key);
    }

    /**
     * @see InternalWrapperContext#localPut(String, Object)
     */
    public Object localPut(final String key, final Object value) {
        return innerContext.put(key, value);
    }

    /**
     * @see InternalHousekeepingContext#icachePut(Object, IntrospectionCacheData)
     */
    public void icachePut(Object key, IntrospectionCacheData o) {
        innerContext.icachePut(key, o);
    }

    /**
     * @see InternalHousekeepingContext#setMacroLibraries(List)
     */
    public void setMacroLibraries(List macroLibraries) {
        innerContext.setMacroLibraries(macroLibraries);
    }

    /**
     * @see InternalHousekeepingContext#getMacroLibraries()
     */
    public List getMacroLibraries() {
        return innerContext.getMacroLibraries();
    }

    /**
     * @see InternalEventContext#attachEventCartridge(EventCartridge)
     */
    public EventCartridge attachEventCartridge(EventCartridge ec) {
        return innerContext.attachEventCartridge(ec);
    }

    /**
     * @see InternalEventContext#getEventCartridge()
     */
    public EventCartridge getEventCartridge() {
        return innerContext.getEventCartridge();
    }


    /**
     * @see InternalHousekeepingContext#setCurrentResource(org.apache.velocity.runtime.resource.Resource)
     */
    public void setCurrentResource(Resource r) {
        innerContext.setCurrentResource(r);
    }

    /**
     * @see InternalHousekeepingContext#getCurrentResource()
     */
    public Resource getCurrentResource() {
        return innerContext.getCurrentResource();
    }
}
