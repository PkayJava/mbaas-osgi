package org.apache.commons.validator;

import org.osgi.framework.Bundle;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by socheatkhauv on 1/14/17.
 */
public interface ValidatorService {

    ValidatorResources createValidatorResources(URL url) throws IOException, SAXException;

    Validator createValidator(Bundle bundle, ValidatorResources resources, String form);
}
