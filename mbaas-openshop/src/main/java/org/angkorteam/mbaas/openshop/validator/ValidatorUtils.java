package org.angkorteam.mbaas.openshop.validator;

import org.apache.commons.validator.ValidatorAction;

import java.text.MessageFormat;

/**
 * Created by socheatkhauv on 1/13/17.
 */
public class ValidatorUtils {

    public static String getMessage(ValidatorAction action, Object... args) {
        String actionMessage = action.getMsg();
        if (args != null && args.length > 0) {
            return MessageFormat.format(actionMessage, args);
        } else {
            return actionMessage;
        }
    }

}
