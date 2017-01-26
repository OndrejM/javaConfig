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

import javax.enterprise.inject.spi.CDI;

import io.microprofile.config.internal.ConfigExtension;
import io.microprofile.config.spi.ConfigSource;


/**
 * <p>This is the central class to access a {@link Config}.</p>
 *
 * <p>A {@link Config} contains the configuration for a certain
 * situation. That might be the configuration found in a certain ClassLoader
 * or even a manually created Configuration</p>
 *
 * <p>The default usage is to use {@link #getConfig()} to automatically
 * pick up the 'Configuration' for the Thread Context ClassLoader
 * (See {@link  Thread#getContextClassLoader()}). </p>
 *
 * <p>A 'Configuration' consists of the information collected from the registered
 * {@link ConfigSource}s. These {@link ConfigSource}s
 * get sorted according to their <em>ordinal</em> defined via {@link ConfigSource#getOrdinal()}.
 * That way it is possible to overwrite configuration with lower importance from outside.</p>
 *
 * <p>It is also possible to register custom {@link ConfigSource}s to
 * flexibly extend the configuration mechanism. An example would be to pick up configuration values
 * from a database table./p>
 *
 * <p>Example usage:
 *
 * <pre>
 *     String restUrl = ConfigProvider.getConfig().getValue("myproject.some.remote.service.url");
 *     Integer port = ConfigProvider.getConfig().getValue("myproject.some.remote.service.port", Integer.class);
 * </pre>
 *
 * </p>
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 * @author <a href="mailto:rmannibucau@apache.org">Romain Manni-Bucau</a>
 */
public class ConfigProvider {

    /**
     * Provide a {@link Config} based on all {@link ConfigSource}s
     * of the current Thread Context ClassLoader (TCCL)
     *
     * <p>There is exactly a single Config instance per Application</p>
     */
    public static Config getConfig() {
        return CDI.current().getBeanManager().getExtension(ConfigExtension.class).getConfig();
    }

}
