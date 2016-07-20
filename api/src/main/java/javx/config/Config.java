/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
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
package javx.config;

import javx.config.spi.ConfigSource;
import javx.config.spi.ConfigSourceProvider;

/**
 * <p>Resolves configured values of properties by going through the list
 * of configured {@link ConfigSource}s and using the one with the highest ordinal.
 * If multiple {@link ConfigSource}s have the same ordinal, their order is undefined.</p>
 *
 * <p>You can provide your own lookup paths by implementing and registering additional
 * {@link ConfigSource}s and {@link ConfigSourceProvider} implementations.</p>
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
public interface Config extends AutoCloseable {

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
     * Releases the configuration.
     */
    @Override
    void close();
}
