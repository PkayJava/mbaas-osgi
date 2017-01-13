package org.angkorteam.mbaas.openshop.validator;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.angkorteam.mbaas.servlet.FormItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.*;

import java.util.List;

/**
 * Created by socheatkhauv on 1/13/17.
 */
public class EmailValidator {

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
            return true;
        } else {
            List<String> errors = Lists.newArrayList();
            for (String value : values) {
                if (!Strings.isNullOrEmpty(value) && !org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(value)) {
                    errors.add(value);
                }
            }
            if (!errors.isEmpty()) {
                formItem.put(field.getProperty() + ".error", ValidatorUtils.getMessage(action, StringUtils.join(errors, ",")));
                formItem.setError(true);
                return false;
            }
        }
        return true;
    }
}
