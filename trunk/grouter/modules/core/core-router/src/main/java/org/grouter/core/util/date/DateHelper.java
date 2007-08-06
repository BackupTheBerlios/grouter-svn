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

package org.grouter.core.util.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper
{
    public static SimpleDateFormat validDateFormat = new SimpleDateFormat("yyyyMMdd");

    public DateHelper()
    {
    }

    public static java.sql.Date getDateFromStringDate(String fromDatestr) throws ParseException, Exception
    {
        String toDateStr = "";
        java.util.Date fromDate = null;
        java.sql.Date fromDate2 = null;
        fromDate = validDateFormat.parse(fromDatestr);
        fromDate2 = new java.sql.Date(fromDate.getTime());
        return fromDate2;
    }


    public static java.sql.Date[] getFromToNextDayDates(String fromDatestr) throws ParseException, Exception
    {
        Calendar c1 = Calendar.getInstance();
        java.sql.Date[] fromToDateMRCustomers = new java.sql.Date[2];
        String toDateStr = "";
        java.sql.Date toDate = null;
        java.util.Date fromDate = null;
        java.sql.Date fromDate2 = null;
        fromDate = validDateFormat.parse(fromDatestr);
        fromDate2 = new java.sql.Date(fromDate.getTime());
        c1.setTime(fromDate);
        c1.add(Calendar.DATE, 1);
        toDate = new java.sql.Date(c1.getTime().getTime());
        fromToDateMRCustomers[0] = fromDate2;
        fromToDateMRCustomers[1] = toDate;

        return fromToDateMRCustomers;
    }

    /**
     * Calculate two swl dates for
     *
     * @param numberOfMonthsBack
     * @return java.sql.Date[] array with two elements [0] holds from and [1] holds to date
     * @throws ParseException
     * @throws Exception
     */
    public static java.sql.Date[] getFromToDateForXMonthsBack(int numberOfMonthsBack) throws ParseException, Exception
    {

        Calendar c1 = Calendar.getInstance();
        java.sql.Date[] dates = new java.sql.Date[2];
        java.sql.Date toDate = null;
        java.util.Date today = new java.util.Date(System.currentTimeMillis());
        java.sql.Date fromDate = null;
        c1.setTime(today);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        toDate = new java.sql.Date(c1.getTime().getTime());
        dates[1] = toDate;
        c1.add(Calendar.MONTH, -6);
        fromDate = new java.sql.Date(c1.getTime().getTime());
        dates[0] = fromDate;

        return dates;
    }

    public static java.sql.Timestamp[] getFromToTimestampsForCurrentMonth() throws ParseException, Exception
    {
        Calendar c1 = Calendar.getInstance();
        java.sql.Timestamp[] dates = new java.sql.Timestamp[2];
        java.sql.Timestamp toDate = null;
        java.util.Date today = new java.util.Date(System.currentTimeMillis());
        java.sql.Timestamp fromDate = null;
        c1.setTime(today);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);

        fromDate = new java.sql.Timestamp(c1.getTime().getTime());
        dates[0] = fromDate;
        c1.add(Calendar.MONTH, 1);
        toDate = new java.sql.Timestamp(c1.getTime().getTime());
        dates[1] = toDate;
        return dates;
    }

    public static java.sql.Date[] getFromToDateForOneMonth(String fromDatestr) throws ParseException, Exception
    {

        Calendar c1 = Calendar.getInstance();
        java.sql.Date[] fromToDateMRCustomers = new java.sql.Date[2];

        String toDateStr = "";
        java.sql.Date toDate = null;
        java.util.Date fromDate = null;
        java.sql.Date fromDate2 = null;
        fromDate = validDateFormat.parse(fromDatestr);
        fromDate2 = new java.sql.Date(fromDate.getTime());
        c1.setTime(fromDate);
        c1.add(Calendar.MONTH, 1);
        toDate = new java.sql.Date(c1.getTime().getTime());
        fromToDateMRCustomers[0] = fromDate2;
        fromToDateMRCustomers[1] = toDate;

        return fromToDateMRCustomers;
    }

    public static java.sql.Timestamp getXHourBackinTime(int x) throws ParseException, Exception
    {
        Calendar c1 = Calendar.getInstance();
        java.sql.Timestamp newDate = null;
        java.util.Date today = new java.util.Date(System.currentTimeMillis());
        java.sql.Date fromDate = null;
        c1.setTime(today);
        c1.set(Calendar.HOUR, -x);
        newDate = new java.sql.Timestamp(c1.getTime().getTime());

        return newDate;
    }

}