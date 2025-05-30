/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.generated.tables.BatchJobExecution;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BatchJobExecutionRecord extends UpdatableRecordImpl<BatchJobExecutionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for
     * <code>scale_advisor.BATCH_JOB_EXECUTION.JOB_EXECUTION_ID</code>.
     */
    public BatchJobExecutionRecord setJobExecutionId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_JOB_EXECUTION.JOB_EXECUTION_ID</code>.
     */
    public Long getJobExecutionId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.VERSION</code>.
     */
    public BatchJobExecutionRecord setVersion(Long value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.VERSION</code>.
     */
    public Long getVersion() {
        return (Long) get(1);
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_JOB_EXECUTION.JOB_INSTANCE_ID</code>.
     */
    public BatchJobExecutionRecord setJobInstanceId(Long value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_JOB_EXECUTION.JOB_INSTANCE_ID</code>.
     */
    public Long getJobInstanceId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.CREATE_TIME</code>.
     */
    public BatchJobExecutionRecord setCreateTime(LocalDateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.CREATE_TIME</code>.
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.START_TIME</code>.
     */
    public BatchJobExecutionRecord setStartTime(LocalDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.START_TIME</code>.
     */
    public LocalDateTime getStartTime() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.END_TIME</code>.
     */
    public BatchJobExecutionRecord setEndTime(LocalDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.END_TIME</code>.
     */
    public LocalDateTime getEndTime() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.STATUS</code>.
     */
    public BatchJobExecutionRecord setStatus(String value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.STATUS</code>.
     */
    public String getStatus() {
        return (String) get(6);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.EXIT_CODE</code>.
     */
    public BatchJobExecutionRecord setExitCode(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.EXIT_CODE</code>.
     */
    public String getExitCode() {
        return (String) get(7);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.EXIT_MESSAGE</code>.
     */
    public BatchJobExecutionRecord setExitMessage(String value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.EXIT_MESSAGE</code>.
     */
    public String getExitMessage() {
        return (String) get(8);
    }

    /**
     * Setter for <code>scale_advisor.BATCH_JOB_EXECUTION.LAST_UPDATED</code>.
     */
    public BatchJobExecutionRecord setLastUpdated(LocalDateTime value) {
        set(9, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_JOB_EXECUTION.LAST_UPDATED</code>.
     */
    public LocalDateTime getLastUpdated() {
        return (LocalDateTime) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached BatchJobExecutionRecord
     */
    public BatchJobExecutionRecord() {
        super(BatchJobExecution.BATCH_JOB_EXECUTION);
    }

    /**
     * Create a detached, initialised BatchJobExecutionRecord
     */
    public BatchJobExecutionRecord(Long jobExecutionId, Long version, Long jobInstanceId, LocalDateTime createTime, LocalDateTime startTime, LocalDateTime endTime, String status, String exitCode, String exitMessage, LocalDateTime lastUpdated) {
        super(BatchJobExecution.BATCH_JOB_EXECUTION);

        setJobExecutionId(jobExecutionId);
        setVersion(version);
        setJobInstanceId(jobInstanceId);
        setCreateTime(createTime);
        setStartTime(startTime);
        setEndTime(endTime);
        setStatus(status);
        setExitCode(exitCode);
        setExitMessage(exitMessage);
        setLastUpdated(lastUpdated);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised BatchJobExecutionRecord
     */
    public BatchJobExecutionRecord(org.jooq.generated.tables.pojos.BatchJobExecution value) {
        super(BatchJobExecution.BATCH_JOB_EXECUTION);

        if (value != null) {
            setJobExecutionId(value.getJobExecutionId());
            setVersion(value.getVersion());
            setJobInstanceId(value.getJobInstanceId());
            setCreateTime(value.getCreateTime());
            setStartTime(value.getStartTime());
            setEndTime(value.getEndTime());
            setStatus(value.getStatus());
            setExitCode(value.getExitCode());
            setExitMessage(value.getExitMessage());
            setLastUpdated(value.getLastUpdated());
            resetChangedOnNotNull();
        }
    }
}
