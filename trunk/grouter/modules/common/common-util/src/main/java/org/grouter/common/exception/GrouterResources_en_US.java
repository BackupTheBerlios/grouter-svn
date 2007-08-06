/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.common.exception;

import java.util.ListResourceBundle;

/**
 * Localization for US and english.
 * 
 * @author Georges
 */
public class GrouterResources_en_US extends ListResourceBundle implements MessageKeys
{
    public Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents =
            {
                    // Localize from here
                    {MSG_FILE_NOT_FOUND, "Cannot find file {0}"},
                    {MSG_CANT_OPEN_FILE, "Cannot open file {0}"},
                    {REMOTE_EXCEPTION_MESSAGE, "Got a remote exception {0}"},

                    // Localize to here
            };
}

