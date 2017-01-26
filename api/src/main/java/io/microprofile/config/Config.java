/*
 * Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * The author licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microprofile.config;

import java.util.Map;

import io.microprofile.config.spi.Converter;
import io.microprofile.config.spi.ConfigSource;

/**
 * <p>Resolves configured values of properties by going through the list
 * of configured {@link ConfigSource}s and using the one with the highest ordinal.
 * If multiple {@link ConfigSource}s have the same ordinal, their order is undefined.</p>
 *
 * <p>You can provide your own lookup paths by implementing and registering additional
 * {@link ConfigSource}s implementations.</p>
 *
 *
 * @see ConfigProvider to resolve the current configuration.
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
public interface Config {

    /**
     * Resolves the value configured for the given key.
     *
     * @param key the property key
     *
     * @return the configured property value from the {@link ConfigSource} with the highest ordinal
     *         or {@code null} if there is no configured value for it
     */
    String getValue(String key);

    /**
     * Resolves the value configured for the given key and convert it to the required asType.
     *
     * @param key
     * @param asType Also support parameterized Types?
     * @param <T>
     * @return
     * @throws UnsupportedOperationException if there is no {@link Converter} registered for asType
     */
    <T> T getValue(String key, Class<T> asType);

    /**
     * Apply the
     * @return the String converted
     * @throws UnsupportedOperationException if there is no {@link Converter} registered for asType
     */
    <T> T convert(String value, Class<T> asType);

    /**
     * Returns a Map of all properties from all scannable config sources. The values of the properties reflect the
     * values that would be obtained by a call to {@link #getValue(java.lang.String)}, that is, the value of the
     * property from the ConfigSource with the highest ordinal.
     */
    Map<String, String> getAllProperties();

    /**
     * @return all currently registered {@link ConfigSource}s
     */
    ConfigSource[] getConfigSources();

}
