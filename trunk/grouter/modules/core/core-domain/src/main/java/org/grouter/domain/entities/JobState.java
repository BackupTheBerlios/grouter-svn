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

package org.grouter.domain.entities;

/**
 * @author Georges Polyzois
 */
public class JobState
{
    public static final JobState UNKNOWN = new JobState(1L, "UNKNOWN");
    public static final JobState PENDING = new JobState(2L, "PENDING");
    public static final JobState PROCESSING = new JobState(3L, "PROCESSING");
    public static final JobState CANCELED = new JobState(4L, "CANCELED");
    public static final JobState ABORTED = new JobState(5L, "ABORTED");
    public static final JobState COMPLETED = new JobState(6L, "COMPLETED");
    public static final JobState FAILED = new JobState(7L, "FAILED");

    public JobState(long id, String name)
    {

    }
}
