package org.apache.commons.validator;

import org.apache.commons.digester.DigesterService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public class ValidatorServiceImpl implements ValidatorService {

    private final BundleContext context;

    public ValidatorServiceImpl(BundleContext context) {
        this.context = context;
    }

    @Override
    public ValidatorResources createValidatorResources(URL url) throws IOException {
        ServiceReference<DigesterService> reference = context.getServiceReference(DigesterService.class);
        DigesterService service = context.getService(reference);
        try {
            ValidatorResources validatorResources = new ValidatorResources(context.getBundle(), service, url.openStream());
            return validatorResources;
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Validator createValidator(Bundle bundle, ValidatorResources resources, String form) {
        Validator validator = new Validator(bundle, resources, form);
        return validator;
    }
}
