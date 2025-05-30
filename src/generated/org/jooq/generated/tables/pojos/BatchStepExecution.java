/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BatchStepExecution implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long stepExecutionId;
    private Long version;
    private String stepName;
    private Long jobExecutionId;
    private LocalDateTime createTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Long commitCount;
    private Long readCount;
    private Long filterCount;
    private Long writeCount;
    private Long readSkipCount;
    private Long writeSkipCount;
    private Long processSkipCount;
    private Long rollbackCount;
    private String exitCode;
    private String exitMessage;
    private LocalDateTime lastUpdated;

    public BatchStepExecution() {}

    public BatchStepExecution(BatchStepExecution value) {
        this.stepExecutionId = value.stepExecutionId;
        this.version = value.version;
        this.stepName = value.stepName;
        this.jobExecutionId = value.jobExecutionId;
        this.createTime = value.createTime;
        this.startTime = value.startTime;
        this.endTime = value.endTime;
        this.status = value.status;
        this.commitCount = value.commitCount;
        this.readCount = value.readCount;
        this.filterCount = value.filterCount;
        this.writeCount = value.writeCount;
        this.readSkipCount = value.readSkipCount;
        this.writeSkipCount = value.writeSkipCount;
        this.processSkipCount = value.processSkipCount;
        this.rollbackCount = value.rollbackCount;
        this.exitCode = value.exitCode;
        this.exitMessage = value.exitMessage;
        this.lastUpdated = value.lastUpdated;
    }

    public BatchStepExecution(
        Long stepExecutionId,
        Long version,
        String stepName,
        Long jobExecutionId,
        LocalDateTime createTime,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status,
        Long commitCount,
        Long readCount,
        Long filterCount,
        Long writeCount,
        Long readSkipCount,
        Long writeSkipCount,
        Long processSkipCount,
        Long rollbackCount,
        String exitCode,
        String exitMessage,
        LocalDateTime lastUpdated
    ) {
        this.stepExecutionId = stepExecutionId;
        this.version = version;
        this.stepName = stepName;
        this.jobExecutionId = jobExecutionId;
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.commitCount = commitCount;
        this.readCount = readCount;
        this.filterCount = filterCount;
        this.writeCount = writeCount;
        this.readSkipCount = readSkipCount;
        this.writeSkipCount = writeSkipCount;
        this.processSkipCount = processSkipCount;
        this.rollbackCount = rollbackCount;
        this.exitCode = exitCode;
        this.exitMessage = exitMessage;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.STEP_EXECUTION_ID</code>.
     */
    public Long getStepExecutionId() {
        return this.stepExecutionId;
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.STEP_EXECUTION_ID</code>.
     */
    public BatchStepExecution setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.VERSION</code>.
     */
    public Long getVersion() {
        return this.version;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.VERSION</code>.
     */
    public BatchStepExecution setVersion(Long version) {
        this.version = version;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.STEP_NAME</code>.
     */
    public String getStepName() {
        return this.stepName;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.STEP_NAME</code>.
     */
    public BatchStepExecution setStepName(String stepName) {
        this.stepName = stepName;
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.JOB_EXECUTION_ID</code>.
     */
    public Long getJobExecutionId() {
        return this.jobExecutionId;
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.JOB_EXECUTION_ID</code>.
     */
    public BatchStepExecution setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.CREATE_TIME</code>.
     */
    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.CREATE_TIME</code>.
     */
    public BatchStepExecution setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.START_TIME</code>.
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.START_TIME</code>.
     */
    public BatchStepExecution setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.END_TIME</code>.
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.END_TIME</code>.
     */
    public BatchStepExecution setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.STATUS</code>.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.STATUS</code>.
     */
    public BatchStepExecution setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.COMMIT_COUNT</code>.
     */
    public Long getCommitCount() {
        return this.commitCount;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.COMMIT_COUNT</code>.
     */
    public BatchStepExecution setCommitCount(Long commitCount) {
        this.commitCount = commitCount;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.READ_COUNT</code>.
     */
    public Long getReadCount() {
        return this.readCount;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.READ_COUNT</code>.
     */
    public BatchStepExecution setReadCount(Long readCount) {
        this.readCount = readCount;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.FILTER_COUNT</code>.
     */
    public Long getFilterCount() {
        return this.filterCount;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.FILTER_COUNT</code>.
     */
    public BatchStepExecution setFilterCount(Long filterCount) {
        this.filterCount = filterCount;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.WRITE_COUNT</code>.
     */
    public Long getWriteCount() {
        return this.writeCount;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.WRITE_COUNT</code>.
     */
    public BatchStepExecution setWriteCount(Long writeCount) {
        this.writeCount = writeCount;
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.READ_SKIP_COUNT</code>.
     */
    public Long getReadSkipCount() {
        return this.readSkipCount;
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.READ_SKIP_COUNT</code>.
     */
    public BatchStepExecution setReadSkipCount(Long readSkipCount) {
        this.readSkipCount = readSkipCount;
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.WRITE_SKIP_COUNT</code>.
     */
    public Long getWriteSkipCount() {
        return this.writeSkipCount;
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.WRITE_SKIP_COUNT</code>.
     */
    public BatchStepExecution setWriteSkipCount(Long writeSkipCount) {
        this.writeSkipCount = writeSkipCount;
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.PROCESS_SKIP_COUNT</code>.
     */
    public Long getProcessSkipCount() {
        return this.processSkipCount;
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.PROCESS_SKIP_COUNT</code>.
     */
    public BatchStepExecution setProcessSkipCount(Long processSkipCount) {
        this.processSkipCount = processSkipCount;
        return this;
    }

    /**
     * Getter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.ROLLBACK_COUNT</code>.
     */
    public Long getRollbackCount() {
        return this.rollbackCount;
    }

    /**
     * Setter for
     * <code>scale_advisor.BATCH_STEP_EXECUTION.ROLLBACK_COUNT</code>.
     */
    public BatchStepExecution setRollbackCount(Long rollbackCount) {
        this.rollbackCount = rollbackCount;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.EXIT_CODE</code>.
     */
    public String getExitCode() {
        return this.exitCode;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.EXIT_CODE</code>.
     */
    public BatchStepExecution setExitCode(String exitCode) {
        this.exitCode = exitCode;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.EXIT_MESSAGE</code>.
     */
    public String getExitMessage() {
        return this.exitMessage;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.EXIT_MESSAGE</code>.
     */
    public BatchStepExecution setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.BATCH_STEP_EXECUTION.LAST_UPDATED</code>.
     */
    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Setter for <code>scale_advisor.BATCH_STEP_EXECUTION.LAST_UPDATED</code>.
     */
    public BatchStepExecution setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BatchStepExecution other = (BatchStepExecution) obj;
        if (this.stepExecutionId == null) {
            if (other.stepExecutionId != null)
                return false;
        }
        else if (!this.stepExecutionId.equals(other.stepExecutionId))
            return false;
        if (this.version == null) {
            if (other.version != null)
                return false;
        }
        else if (!this.version.equals(other.version))
            return false;
        if (this.stepName == null) {
            if (other.stepName != null)
                return false;
        }
        else if (!this.stepName.equals(other.stepName))
            return false;
        if (this.jobExecutionId == null) {
            if (other.jobExecutionId != null)
                return false;
        }
        else if (!this.jobExecutionId.equals(other.jobExecutionId))
            return false;
        if (this.createTime == null) {
            if (other.createTime != null)
                return false;
        }
        else if (!this.createTime.equals(other.createTime))
            return false;
        if (this.startTime == null) {
            if (other.startTime != null)
                return false;
        }
        else if (!this.startTime.equals(other.startTime))
            return false;
        if (this.endTime == null) {
            if (other.endTime != null)
                return false;
        }
        else if (!this.endTime.equals(other.endTime))
            return false;
        if (this.status == null) {
            if (other.status != null)
                return false;
        }
        else if (!this.status.equals(other.status))
            return false;
        if (this.commitCount == null) {
            if (other.commitCount != null)
                return false;
        }
        else if (!this.commitCount.equals(other.commitCount))
            return false;
        if (this.readCount == null) {
            if (other.readCount != null)
                return false;
        }
        else if (!this.readCount.equals(other.readCount))
            return false;
        if (this.filterCount == null) {
            if (other.filterCount != null)
                return false;
        }
        else if (!this.filterCount.equals(other.filterCount))
            return false;
        if (this.writeCount == null) {
            if (other.writeCount != null)
                return false;
        }
        else if (!this.writeCount.equals(other.writeCount))
            return false;
        if (this.readSkipCount == null) {
            if (other.readSkipCount != null)
                return false;
        }
        else if (!this.readSkipCount.equals(other.readSkipCount))
            return false;
        if (this.writeSkipCount == null) {
            if (other.writeSkipCount != null)
                return false;
        }
        else if (!this.writeSkipCount.equals(other.writeSkipCount))
            return false;
        if (this.processSkipCount == null) {
            if (other.processSkipCount != null)
                return false;
        }
        else if (!this.processSkipCount.equals(other.processSkipCount))
            return false;
        if (this.rollbackCount == null) {
            if (other.rollbackCount != null)
                return false;
        }
        else if (!this.rollbackCount.equals(other.rollbackCount))
            return false;
        if (this.exitCode == null) {
            if (other.exitCode != null)
                return false;
        }
        else if (!this.exitCode.equals(other.exitCode))
            return false;
        if (this.exitMessage == null) {
            if (other.exitMessage != null)
                return false;
        }
        else if (!this.exitMessage.equals(other.exitMessage))
            return false;
        if (this.lastUpdated == null) {
            if (other.lastUpdated != null)
                return false;
        }
        else if (!this.lastUpdated.equals(other.lastUpdated))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.stepExecutionId == null) ? 0 : this.stepExecutionId.hashCode());
        result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
        result = prime * result + ((this.stepName == null) ? 0 : this.stepName.hashCode());
        result = prime * result + ((this.jobExecutionId == null) ? 0 : this.jobExecutionId.hashCode());
        result = prime * result + ((this.createTime == null) ? 0 : this.createTime.hashCode());
        result = prime * result + ((this.startTime == null) ? 0 : this.startTime.hashCode());
        result = prime * result + ((this.endTime == null) ? 0 : this.endTime.hashCode());
        result = prime * result + ((this.status == null) ? 0 : this.status.hashCode());
        result = prime * result + ((this.commitCount == null) ? 0 : this.commitCount.hashCode());
        result = prime * result + ((this.readCount == null) ? 0 : this.readCount.hashCode());
        result = prime * result + ((this.filterCount == null) ? 0 : this.filterCount.hashCode());
        result = prime * result + ((this.writeCount == null) ? 0 : this.writeCount.hashCode());
        result = prime * result + ((this.readSkipCount == null) ? 0 : this.readSkipCount.hashCode());
        result = prime * result + ((this.writeSkipCount == null) ? 0 : this.writeSkipCount.hashCode());
        result = prime * result + ((this.processSkipCount == null) ? 0 : this.processSkipCount.hashCode());
        result = prime * result + ((this.rollbackCount == null) ? 0 : this.rollbackCount.hashCode());
        result = prime * result + ((this.exitCode == null) ? 0 : this.exitCode.hashCode());
        result = prime * result + ((this.exitMessage == null) ? 0 : this.exitMessage.hashCode());
        result = prime * result + ((this.lastUpdated == null) ? 0 : this.lastUpdated.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BatchStepExecution (");

        sb.append(stepExecutionId);
        sb.append(", ").append(version);
        sb.append(", ").append(stepName);
        sb.append(", ").append(jobExecutionId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(startTime);
        sb.append(", ").append(endTime);
        sb.append(", ").append(status);
        sb.append(", ").append(commitCount);
        sb.append(", ").append(readCount);
        sb.append(", ").append(filterCount);
        sb.append(", ").append(writeCount);
        sb.append(", ").append(readSkipCount);
        sb.append(", ").append(writeSkipCount);
        sb.append(", ").append(processSkipCount);
        sb.append(", ").append(rollbackCount);
        sb.append(", ").append(exitCode);
        sb.append(", ").append(exitMessage);
        sb.append(", ").append(lastUpdated);

        sb.append(")");
        return sb.toString();
    }
}
