/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.daos;


import java.util.List;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.generated.tables.QrtzFiredTriggers;
import org.jooq.generated.tables.records.QrtzFiredTriggersRecord;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QrtzFiredTriggersDao extends DAOImpl<QrtzFiredTriggersRecord, org.jooq.generated.tables.pojos.QrtzFiredTriggers, Record2<String, String>> {

    /**
     * Create a new QrtzFiredTriggersDao without any configuration
     */
    public QrtzFiredTriggersDao() {
        super(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS, org.jooq.generated.tables.pojos.QrtzFiredTriggers.class);
    }

    /**
     * Create a new QrtzFiredTriggersDao with an attached configuration
     */
    public QrtzFiredTriggersDao(Configuration configuration) {
        super(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS, org.jooq.generated.tables.pojos.QrtzFiredTriggers.class, configuration);
    }

    @Override
    public Record2<String, String> getId(org.jooq.generated.tables.pojos.QrtzFiredTriggers object) {
        return compositeKeyRecord(object.getSchedName(), object.getEntryId());
    }

    /**
     * Fetch records that have <code>SCHED_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfSchedName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.SCHED_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>SCHED_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchBySchedName(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.SCHED_NAME, values);
    }

    /**
     * Fetch records that have <code>ENTRY_ID BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfEntryId(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.ENTRY_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>ENTRY_ID IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByEntryId(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.ENTRY_ID, values);
    }

    /**
     * Fetch records that have <code>TRIGGER_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfTriggerName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.TRIGGER_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>TRIGGER_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByTriggerName(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.TRIGGER_NAME, values);
    }

    /**
     * Fetch records that have <code>TRIGGER_GROUP BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfTriggerGroup(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.TRIGGER_GROUP, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>TRIGGER_GROUP IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByTriggerGroup(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.TRIGGER_GROUP, values);
    }

    /**
     * Fetch records that have <code>INSTANCE_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfInstanceName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.INSTANCE_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>INSTANCE_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByInstanceName(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.INSTANCE_NAME, values);
    }

    /**
     * Fetch records that have <code>FIRED_TIME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfFiredTime(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.FIRED_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>FIRED_TIME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByFiredTime(Long... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.FIRED_TIME, values);
    }

    /**
     * Fetch records that have <code>SCHED_TIME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfSchedTime(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.SCHED_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>SCHED_TIME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchBySchedTime(Long... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.SCHED_TIME, values);
    }

    /**
     * Fetch records that have <code>PRIORITY BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfPriority(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.PRIORITY, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>PRIORITY IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByPriority(Integer... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>STATE BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfState(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.STATE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>STATE IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByState(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.STATE, values);
    }

    /**
     * Fetch records that have <code>JOB_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfJobName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.JOB_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>JOB_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByJobName(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.JOB_NAME, values);
    }

    /**
     * Fetch records that have <code>JOB_GROUP BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfJobGroup(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.JOB_GROUP, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>JOB_GROUP IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByJobGroup(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.JOB_GROUP, values);
    }

    /**
     * Fetch records that have <code>IS_NONCONCURRENT BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfIsNonconcurrent(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.IS_NONCONCURRENT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>IS_NONCONCURRENT IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByIsNonconcurrent(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.IS_NONCONCURRENT, values);
    }

    /**
     * Fetch records that have <code>REQUESTS_RECOVERY BETWEEN lowerInclusive
     * AND upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchRangeOfRequestsRecovery(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.REQUESTS_RECOVERY, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>REQUESTS_RECOVERY IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzFiredTriggers> fetchByRequestsRecovery(String... values) {
        return fetch(QrtzFiredTriggers.QRTZ_FIRED_TRIGGERS.REQUESTS_RECOVERY, values);
    }
}
