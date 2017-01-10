package org.angkorteam.mbaas.servlet;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * Created by socheatkhauv on 1/10/17.
 */
public class QueryString implements Serializable {

    private Map<String, String[]> query;

    public QueryString(HttpServletRequest request) {
        this.query = Maps.newHashMap();
        String queryString = request.getQueryString();
        if (!Strings.isNullOrEmpty(queryString)) {
            String[] params = StringUtils.split(queryString, '&');
            if (params != null && params.length > 0) {
                for (String temp : params) {
                    if (!Strings.isNullOrEmpty(temp)) {
                        String param[] = StringUtils.split(temp, '=');
                        String name = null;
                        String value = null;
                        if (param != null) {
                            if (param.length > 0) {
                                name = param[0];
                            }
                            if (param.length > 1) {
                                value = param[1];
                            }
                        }
                        if (!Strings.isNullOrEmpty(name)) {
                            if (!query.containsKey(name)) {
                                query.put(name, new String[]{});
                            }
                            if (!Strings.isNullOrEmpty(value)) {
                                String[] values = query.get(name);
                                String[] newValues = Arrays.copyOf(values, values.length + 1);
                                newValues[newValues.length - 1] = value;
                                query.put(name, newValues);
                            }
                        }
                    }
                }
            }
        }
    }

    public String getParameter(String name) {
        String[] values = this.query.get(name);
        return values == null || values.length == 0 ? null : values[0];
    }

    public String[] getParameterValues(String name) {
        String[] values = this.query.get(name);
        return values;
    }

    public List<String> getParameterNames() {
        return Collections.unmodifiableList(new ArrayList<>(this.query.keySet()));
    }

    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.query);
    }

}
