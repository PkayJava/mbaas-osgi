package org.apache.velocity;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.parser.ParseException;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * Created by socheatkhauv on 1/8/17.
 */
public class BundleTemplate extends Template {

    private static final Logger LOGGER = LoggerFactory.getLogger(BundleTemplate.class);

    private final Bundle bundle;

    public BundleTemplate(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public boolean process() throws ResourceNotFoundException, ParseErrorException {
        LOGGER.info("BundleTemplate.process()");
        data = null;
        InputStream is = null;
        errorCondition = null;

        /*
         *  first, try to get the stream from the loader
         */
        try {
            LOGGER.info("bundle id {} resource {}", this.bundle.getSymbolicName(), name);
            URL url = this.bundle.getResource(name);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (InputStream inputStream = url.openStream()) {
                IOUtils.copy(inputStream, outputStream);
                is = new ByteArrayInputStream(outputStream.toByteArray());
            } catch (IOException e) {
                is = new ByteArrayInputStream("NULL".getBytes());
            }
        } catch (ResourceNotFoundException rnfe) {
            /*
             *  remember and re-throw
             */

            errorCondition = rnfe;
            throw rnfe;
        }

        /*
         *  if that worked, lets protect in case a loader impl
         *  forgets to throw a proper exception
         */

        if (is != null) {
            /*
             *  now parse the template
             */

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
                data = rsvc.parse(br, name);
                initDocument();
                return true;
            } catch (UnsupportedEncodingException uce) {
                String msg = "Template.process : Unsupported input encoding : " + encoding
                        + " for template " + name;

                errorCondition = new ParseErrorException(msg);
                throw errorCondition;
            } catch (ParseException pex) {
                /*
                 *  remember the error and convert
                 */
                errorCondition = new ParseErrorException(pex, name);
                throw errorCondition;
            } catch (TemplateInitException pex) {
                errorCondition = new ParseErrorException(pex, name);
                throw errorCondition;
            }
            /**
             * pass through runtime exceptions
             */ catch (RuntimeException e) {
                errorCondition = new VelocityException("Exception thrown processing Template "
                        + getName(), e);
                throw errorCondition;
            } finally {
                /*
                 *  Make sure to close the inputstream when we are done.
                 */
                try {
                    is.close();
                } catch (IOException e) {
                    // If we are already throwing an exception then we want the original
                    // exception to be continued to be thrown, otherwise, throw a new Exception.
                    if (errorCondition == null) {
                        throw new VelocityException(e);
                    }
                }
            }
        } else {
            /*
             *  is == null, therefore we have some kind of file issue
             */
            errorCondition = new ResourceNotFoundException("Unknown resource error for resource " + name);
            throw errorCondition;
        }
    }
}
