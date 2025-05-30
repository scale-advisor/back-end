/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records;


import org.jooq.Record2;
import org.jooq.generated.tables.QrtzCalendars;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QrtzCalendarsRecord extends UpdatableRecordImpl<QrtzCalendarsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>scale_advisor.QRTZ_CALENDARS.SCHED_NAME</code>.
     */
    public QrtzCalendarsRecord setSchedName(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_CALENDARS.SCHED_NAME</code>.
     */
    public String getSchedName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_CALENDARS.CALENDAR_NAME</code>.
     */
    public QrtzCalendarsRecord setCalendarName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_CALENDARS.CALENDAR_NAME</code>.
     */
    public String getCalendarName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_CALENDARS.CALENDAR</code>.
     */
    public QrtzCalendarsRecord setCalendar(byte[] value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_CALENDARS.CALENDAR</code>.
     */
    public byte[] getCalendar() {
        return (byte[]) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached QrtzCalendarsRecord
     */
    public QrtzCalendarsRecord() {
        super(QrtzCalendars.QRTZ_CALENDARS);
    }

    /**
     * Create a detached, initialised QrtzCalendarsRecord
     */
    public QrtzCalendarsRecord(String schedName, String calendarName, byte[] calendar) {
        super(QrtzCalendars.QRTZ_CALENDARS);

        setSchedName(schedName);
        setCalendarName(calendarName);
        setCalendar(calendar);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised QrtzCalendarsRecord
     */
    public QrtzCalendarsRecord(org.jooq.generated.tables.pojos.QrtzCalendars value) {
        super(QrtzCalendars.QRTZ_CALENDARS);

        if (value != null) {
            setSchedName(value.getSchedName());
            setCalendarName(value.getCalendarName());
            setCalendar(value.getCalendar());
            resetChangedOnNotNull();
        }
    }
}
