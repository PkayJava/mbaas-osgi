package org.angkorteam.mbaas.openshop.validator;

import com.google.common.base.Strings;
import org.angkorteam.mbaas.servlet.FormItem;
import org.apache.commons.validator.*;

/**
 * Created by socheatkhauv on 1/13/17.
 */
public class RequiredValidator {

    public static boolean validate(Object bean,
                                   Form form,
                                   Field field,
                                   Validator validator,
                                   ValidatorAction action,
                                   ValidatorResults results,
                                   java.util.Locale locale) {
        FormItem formItem = (FormItem) bean;
        String[] values = formItem.getParameterValues(field.getProperty());
        if (values == null || values.length == 0) {
            formItem.put(field.getProperty() + ".error", ValidatorUtils.getMessage(action, field.getArg(0).getKey()));
            formItem.setError(true);
            return false;
        } else {
            for (String value : values) {
                if (Strings.isNullOrEmpty(value)) {
                    formItem.put(field.getProperty() + ".error", ValidatorUtils.getMessage(action, field.getArg(0).getKey()));
                    formItem.setError(true);
                    return false;
                }
            }
        }
        return true;
    }

}
