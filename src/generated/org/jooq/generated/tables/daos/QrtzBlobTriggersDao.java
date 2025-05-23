/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.daos;


import java.util.List;

import org.jooq.Configuration;
import org.jooq.Record3;
import org.jooq.generated.tables.QrtzBlobTriggers;
import org.jooq.generated.tables.records.QrtzBlobTriggersRecord;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QrtzBlobTriggersDao extends DAOImpl<QrtzBlobTriggersRecord, org.jooq.generated.tables.pojos.QrtzBlobTriggers, Record3<String, String, String>> {

    /**
     * Create a new QrtzBlobTriggersDao without any configuration
     */
    public QrtzBlobTriggersDao() {
        super(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS, org.jooq.generated.tables.pojos.QrtzBlobTriggers.class);
    }

    /**
     * Create a new QrtzBlobTriggersDao with an attached configuration
     */
    public QrtzBlobTriggersDao(Configuration configuration) {
        super(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS, org.jooq.generated.tables.pojos.QrtzBlobTriggers.class, configuration);
    }

    @Override
    public Record3<String, String, String> getId(org.jooq.generated.tables.pojos.QrtzBlobTriggers object) {
        return compositeKeyRecord(object.getSchedName(), object.getTriggerName(), object.getTriggerGroup());
    }

    /**
     * Fetch records that have <code>SCHED_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchRangeOfSchedName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.SCHED_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>SCHED_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchBySchedName(String... values) {
        return fetch(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.SCHED_NAME, values);
    }

    /**
     * Fetch records that have <code>TRIGGER_NAME BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchRangeOfTriggerName(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.TRIGGER_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>TRIGGER_NAME IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchByTriggerName(String... values) {
        return fetch(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.TRIGGER_NAME, values);
    }

    /**
     * Fetch records that have <code>TRIGGER_GROUP BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchRangeOfTriggerGroup(String lowerInclusive, String upperInclusive) {
        return fetchRange(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.TRIGGER_GROUP, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>TRIGGER_GROUP IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchByTriggerGroup(String... values) {
        return fetch(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.TRIGGER_GROUP, values);
    }

    /**
     * Fetch records that have <code>BLOB_DATA BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchRangeOfBlobData(byte[] lowerInclusive, byte[] upperInclusive) {
        return fetchRange(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.BLOB_DATA, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>BLOB_DATA IN (values)</code>
     */
    public List<org.jooq.generated.tables.pojos.QrtzBlobTriggers> fetchByBlobData(byte[]... values) {
        return fetch(QrtzBlobTriggers.QRTZ_BLOB_TRIGGERS.BLOB_DATA, values);
    }
}
