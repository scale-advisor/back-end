/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.daos;


import java.util.List;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.generated.tables.QrtzCalendars;
import org.jooq.generated.tables.records.QrtzCalendarsRecord;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QrtzCalendarsDao extends DAOImpl<QrtzCalendarsRecord, org.jooq.generated.tables.pojos.QrtzCalendars, Record2<String, String>> {

    /**
     * Create a new QrtzCalendarsDao without any configuration
     */
    public QrtzCalendarsDao() {
        super(QrtzCalendars.QRTZ_CALENDARS, org.jooq.generated.tables.pojos.QrtzCalendars.class);
    }

    /**
     * Create a new QrtzCalendarsDao with an attached configuration
     */
    public QrtzCalendarsDao(Configuration configuration) {
        super(QrtzCalendars.QRTZ_CALENDARS, org.jooq.generated.tables.pojos.QrtzCalendars.class, configuration);
    }

    @Override
    public Record2<String, String> getId(org.jooq.generated.tables.pojos.QrtzCalendars object) {
        return compositeKeyRecord(object.getSchedName(), object.getCalendarName());
    }

    /**
     * Fetch records that have <code>SCHED_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzCalendars> fetchRangeOfSchedName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzCalendars.QRTZ_CALENDARS.SCHED_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>SCHED_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzCalendars> fetchBySchedName(String... values) {
        return fetch(QrtzCalendars.QRTZ_CALENDARS.SCHED_NAME, values);
    }

    /**
     * Fetch records that have <code>CALENDAR_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzCalendars> fetchRangeOfCalendarName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzCalendars.QRTZ_CALENDARS.CALENDAR_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>CALENDAR_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzCalendars> fetchByCalendarName(String... values) {
        return fetch(QrtzCalendars.QRTZ_CALENDARS.CALENDAR_NAME, values);
    }

    /**
     * Fetch records that have <code>CALENDAR BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzCalendars> fetchRangeOfCalendar(byte[] lowerInclusive, byte[] upperInclusive) {
        return fetchRange(QrtzCalendars.QRTZ_CALENDARS.CALENDAR, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>CALENDAR IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzCalendars> fetchByCalendar(byte[]... values) {
        return fetch(QrtzCalendars.QRTZ_CALENDARS.CALENDAR, values);
    }
}
