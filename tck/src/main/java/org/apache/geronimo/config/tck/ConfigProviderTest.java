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
package org.apache.geronimo.config.tck;

import javx.config.Config;
import javx.config.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
public class ConfigProviderTest {

    @Test
    public void testConfigProviderWithDefaultTCCL() {
        ClassLoader oldTccl = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader tempCl = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
            Thread.currentThread().setContextClassLoader(tempCl);
            Config config = ConfigFactory.getConfig();
            Assert.assertNotNull(config);

            Config config2 = ConfigFactory.getConfig(tempCl);
            Assert.assertNotNull(config2);
            Assert.assertEquals(config, config2);
        } finally {
            Thread.currentThread().setContextClassLoader(oldTccl);
        }
    }

    @Test
    public void testEnvironmentConfigSource() {
        Map<String, String> env = System.getenv();
        Config config = ConfigFactory.getConfig();
        for (Map.Entry<String, String> envEntry : env.entrySet()) {
            Assert.assertEquals(envEntry.getValue(), config.getValue(envEntry.getKey()));
        }
    }

    @Test
    public void testPropertyConfigSource() {
        Properties properties = System.getProperties();
        Config config = ConfigFactory.getConfig();

        for (Map.Entry<Object, Object> propEntry : properties.entrySet()) {
            Assert.assertEquals(propEntry.getValue(), config.getValue((String) propEntry.getKey()));
        }
    }

    @Test
    public void testDynamicValueInPropertyConfigSource() {
        Config config = ConfigFactory.getConfig();
        String configKey = "tck.config.test.systemproperty.dynamic.value";
        String configValue = "myDynamicValue;";

        System.setProperty(configKey, configValue);
        Assert.assertEquals(config.getValue(configKey), configValue);
    }

    @Test
    public void testJavaConfigPropertyFilesConfigSource() {
        Config config = ConfigFactory.getConfig();
        Assert.assertEquals(config.getValue("tck.config.test.javaconfig.properties.key1"), "VALue1");
    }

    @Test
    public void testNonExistingConfigKey() {
        Config config = ConfigFactory.getConfig();
        Assert.assertNull(config.getValue("tck.config.test.keydoesnotexist"));
    }
}
