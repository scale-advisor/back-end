/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.generated.tables.RequirementUnitProcess;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RequirementUnitProcessRecord extends UpdatableRecordImpl<RequirementUnitProcessRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.REQUIREMENT_UNIT_PROCESS_ID</code>.
     */
    public RequirementUnitProcessRecord setRequirementUnitProcessId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.REQUIREMENT_UNIT_PROCESS_ID</code>.
     */
    public Long getRequirementUnitProcessId() {
        return (Long) get(0);
    }

    /**
     * Setter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID</code>.
     */
    public RequirementUnitProcessRecord setRequirementId(Long value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.REQUIREMENT_ID</code>.
     */
    public Long getRequirementId() {
        return (Long) get(1);
    }

    /**
     * Setter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID</code>.
     */
    public RequirementUnitProcessRecord setUnitProcessId(Long value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.UNIT_PROCESS_ID</code>.
     */
    public Long getUnitProcessId() {
        return (Long) get(2);
    }

    /**
     * Setter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.CREATED_AT</code>.
     */
    public RequirementUnitProcessRecord setCreatedAt(LocalDateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.CREATED_AT</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.UPDATED_AT</code>.
     */
    public RequirementUnitProcessRecord setUpdatedAt(LocalDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.REQUIREMENT_UNIT_PROCESS.UPDATED_AT</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(4);
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
     * Create a detached RequirementUnitProcessRecord
     */
    public RequirementUnitProcessRecord() {
        super(RequirementUnitProcess.REQUIREMENT_UNIT_PROCESS);
    }

    /**
     * Create a detached, initialised RequirementUnitProcessRecord
     */
    public RequirementUnitProcessRecord(Long requirementUnitProcessId, Long requirementId, Long unitProcessId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(RequirementUnitProcess.REQUIREMENT_UNIT_PROCESS);

        setRequirementUnitProcessId(requirementUnitProcessId);
        setRequirementId(requirementId);
        setUnitProcessId(unitProcessId);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised RequirementUnitProcessRecord
     */
    public RequirementUnitProcessRecord(org.jooq.generated.tables.pojos.RequirementUnitProcess value) {
        super(RequirementUnitProcess.REQUIREMENT_UNIT_PROCESS);

        if (value != null) {
            setRequirementUnitProcessId(value.getRequirementUnitProcessId());
            setRequirementId(value.getRequirementId());
            setUnitProcessId(value.getUnitProcessId());
            setCreatedAt(value.getCreatedAt());
            setUpdatedAt(value.getUpdatedAt());
            resetChangedOnNotNull();
        }
    }
}
