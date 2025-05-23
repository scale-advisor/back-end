/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.records;


import java.time.LocalDateTime;

import org.jooq.Record2;
import org.jooq.generated.tables.ProjectMember;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProjectMemberRecord extends UpdatableRecordImpl<ProjectMemberRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>scale_advisor.PROJECT_MEMBER.USER_ID</code>.
     */
    public ProjectMemberRecord setUserId(Long value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.PROJECT_MEMBER.USER_ID</code>.
     */
    public Long getUserId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>scale_advisor.PROJECT_MEMBER.PROJECT_ID</code>.
     */
    public ProjectMemberRecord setProjectId(Long value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.PROJECT_MEMBER.PROJECT_ID</code>.
     */
    public Long getProjectId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>scale_advisor.PROJECT_MEMBER.STATE</code>.
     */
    public ProjectMemberRecord setState(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.PROJECT_MEMBER.STATE</code>.
     */
    public String getState() {
        return (String) get(2);
    }

    /**
     * Setter for <code>scale_advisor.PROJECT_MEMBER.ROLE</code>.
     */
    public ProjectMemberRecord setRole(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.PROJECT_MEMBER.ROLE</code>.
     */
    public String getRole() {
        return (String) get(3);
    }

    /**
     * Setter for <code>scale_advisor.PROJECT_MEMBER.CREATED_AT</code>.
     */
    public ProjectMemberRecord setCreatedAt(LocalDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.PROJECT_MEMBER.CREATED_AT</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>scale_advisor.PROJECT_MEMBER.UPDATED_AT</code>.
     */
    public ProjectMemberRecord setUpdatedAt(LocalDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>scale_advisor.PROJECT_MEMBER.UPDATED_AT</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProjectMemberRecord
     */
    public ProjectMemberRecord() {
        super(ProjectMember.PROJECT_MEMBER);
    }

    /**
     * Create a detached, initialised ProjectMemberRecord
     */
    public ProjectMemberRecord(Long userId, Long projectId, String state, String role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(ProjectMember.PROJECT_MEMBER);

        setUserId(userId);
        setProjectId(projectId);
        setState(state);
        setRole(role);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised ProjectMemberRecord
     */
    public ProjectMemberRecord(org.jooq.generated.tables.pojos.ProjectMember value) {
        super(ProjectMember.PROJECT_MEMBER);

        if (value != null) {
            setUserId(value.getUserId());
            setProjectId(value.getProjectId());
            setState(value.getState());
            setRole(value.getRole());
            setCreatedAt(value.getCreatedAt());
            setUpdatedAt(value.getUpdatedAt());
            resetChangedOnNotNull();
        }
    }
}
