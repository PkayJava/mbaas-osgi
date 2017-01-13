/* $Id: SimpleRegexMatcher.java 992060 2010-09-02 19:09:47Z simonetripodi $
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.digester;

/**
 * <p>Simple regex pattern matching algorithm.</p>
 * 
 * <p>This uses just two wildcards:
 * <ul>
 *  <li><code>*</code> matches any sequence of none, one or more characters
 *  <li><code>?</code> matches any one character 
 * </ul>
 * Escaping these wildcards is not supported .</p>
 *
 * @since 1.5
 */

public class SimpleRegexMatcher extends RegexMatcher {
    
    // --------------------------------------------------------- Fields
    
    // --------------------------------------------------------- Public Methods
    
    /** 
     * Matches using simple regex algorithm.
     * 
     *
     * @param basePattern the standard digester path representing the element
     * @param regexPattern the regex pattern the path will be tested against
     * @return true if the given pattern matches the given path
     */
    @Override
    public boolean match(String basePattern, String regexPattern) {
        // check for nulls
        if (basePattern == null || regexPattern == null) {
            return false;
        }
        return match(basePattern, regexPattern, 0, 0);
    }
    
    // --------------------------------------------------------- Implementations Methods
    
    /**
     * Implementation of regex matching algorithm.
     * This calls itself recursively.
     */
    private boolean match(String basePattern, String regexPattern, int baseAt, int regexAt) {
        
        // check bounds
        if (regexAt >= regexPattern.length()) {
            // maybe we've got a match
            if (baseAt >= basePattern.length()) {
                // ok!
                return true;
            }
            // run out early
            return false;
            
        } else {
            if (baseAt >= basePattern.length()) {
                // run out early
                return false;
            }
        }
        
        // ok both within bounds
        char regexCurrent = regexPattern.charAt(regexAt);
        switch (regexCurrent) {
            case '*':
                // this is the tricky case
                // check for terminal 
                if (++regexAt >= regexPattern.length()) {
                    // this matches anything let - so return true
                    return true;
                }
                // go through every subsequent apperance of the next character
                // and so if the rest of the regex matches
                char nextRegex = regexPattern.charAt(regexAt);
                int nextMatch = basePattern.indexOf(nextRegex, baseAt);
                while (nextMatch != -1) {
                    if (match(basePattern, regexPattern, nextMatch, regexAt)) {
                        return true;
                    }
                    nextMatch = basePattern.indexOf(nextRegex, nextMatch + 1);
                }
                return false;
                
            case '?':
                // this matches anything
                return match(basePattern, regexPattern, ++baseAt, ++regexAt);
            
            default:
                if (regexCurrent == basePattern.charAt(baseAt)) {
                    // still got more to go
                    return match(basePattern, regexPattern, ++baseAt, ++regexAt);
                }
                return false;
        }
    }
}
