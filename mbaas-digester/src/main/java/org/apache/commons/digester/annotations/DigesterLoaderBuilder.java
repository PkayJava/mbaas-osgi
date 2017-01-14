/* $id: digesterloaderbuilder.java 992099 2010-09-02 20:09:12z simonetripodi $
 *
 * licensed to the apache software foundation (asf) under one or more
 * contributor license agreements.  see the notice file distributed with
 * this work for additional information regarding copyright ownership.
 * the asf licenses this file to you under the apache license, version 2.0
 * (the "license"); you may not use this file except in compliance with
 * the license.  you may obtain a copy of the license at
 *
 *      http://www.apache.org/licenses/license-2.0
 *
 * unless required by applicable law or agreed to in writing, software
 * distributed under the license is distributed on an "as is" basis,
 * without warranties or conditions of any kind, either express or implied.
 * see the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.commons.digester.annotations;

import org.apache.commons.digester.annotations.internal.DefaultAnnotationRuleProviderFactory;
import org.apache.commons.digester.annotations.spi.AnnotationRuleProviderFactory;

/**
 * {@link DigesterLoader} builder implementation.
 *
 * @since 2.1
 */
public final class DigesterLoaderBuilder {

    /**
     * Builds a new {@link DigesterLoader} using the default SPI
     * implementations.
     *
     * @return a new {@link DigesterLoader} using the default SPI
     *         implementations.
     */
    public static DigesterLoader byDefaultFactories() {
        return new DigesterLoaderBuilder()
                    .useDefaultAnnotationRuleProviderFactory()
                    .useDefaultDigesterLoaderHandlerFactory();
    }

    /**
     * Builds a new {@link DigesterLoader} using the default
     * {@link AnnotationRuleProviderFactory} implementation.
     *
     * @return the next chained builder.
     * @see DefaultAnnotationRuleProviderFactory
     */
    public FromAnnotationRuleProviderFactory useDefaultAnnotationRuleProviderFactory() {
        return this.useAnnotationRuleProviderFactory(new DefaultAnnotationRuleProviderFactory());
    }

    /**
     * Builds a new {@link DigesterLoader} using the user defined
     * {@link AnnotationRuleProviderFactory} implementation.
     *
     * @param annotationRuleProviderFactory the user defined
     *        {@link AnnotationRuleProviderFactory} implementation.
     * @return the next chained builder.
     */
    public FromAnnotationRuleProviderFactory
            useAnnotationRuleProviderFactory(AnnotationRuleProviderFactory annotationRuleProviderFactory) {
        if (annotationRuleProviderFactory == null) {
            throw new IllegalArgumentException("Parameter 'annotationRuleProviderFactory' must be not null");
        }
        return new FromAnnotationRuleProviderFactory(annotationRuleProviderFactory);
    }

}
