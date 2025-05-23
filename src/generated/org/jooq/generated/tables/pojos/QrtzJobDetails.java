/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables.pojos;


import java.io.Serializable;
import java.util.Arrays;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QrtzJobDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String schedName;
    private String jobName;
    private String jobGroup;
    private String description;
    private String jobClassName;
    private String isDurable;
    private String isNonconcurrent;
    private String isUpdateData;
    private String requestsRecovery;
    private byte[] jobData;

    public QrtzJobDetails() {}

    public QrtzJobDetails(QrtzJobDetails value) {
        this.schedName = value.schedName;
        this.jobName = value.jobName;
        this.jobGroup = value.jobGroup;
        this.description = value.description;
        this.jobClassName = value.jobClassName;
        this.isDurable = value.isDurable;
        this.isNonconcurrent = value.isNonconcurrent;
        this.isUpdateData = value.isUpdateData;
        this.requestsRecovery = value.requestsRecovery;
        this.jobData = value.jobData;
    }

    public QrtzJobDetails(
        String schedName,
        String jobName,
        String jobGroup,
        String description,
        String jobClassName,
        String isDurable,
        String isNonconcurrent,
        String isUpdateData,
        String requestsRecovery,
        byte[] jobData
    ) {
        this.schedName = schedName;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
        this.jobClassName = jobClassName;
        this.isDurable = isDurable;
        this.isNonconcurrent = isNonconcurrent;
        this.isUpdateData = isUpdateData;
        this.requestsRecovery = requestsRecovery;
        this.jobData = jobData;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.SCHED_NAME</code>.
     */
    public String getSchedName() {
        return this.schedName;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.SCHED_NAME</code>.
     */
    public QrtzJobDetails setSchedName(String schedName) {
        this.schedName = schedName;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_NAME</code>.
     */
    public String getJobName() {
        return this.jobName;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_NAME</code>.
     */
    public QrtzJobDetails setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_GROUP</code>.
     */
    public String getJobGroup() {
        return this.jobGroup;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_GROUP</code>.
     */
    public QrtzJobDetails setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.DESCRIPTION</code>.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.DESCRIPTION</code>.
     */
    public QrtzJobDetails setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_CLASS_NAME</code>.
     */
    public String getJobClassName() {
        return this.jobClassName;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_CLASS_NAME</code>.
     */
    public QrtzJobDetails setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.IS_DURABLE</code>.
     */
    public String getIsDurable() {
        return this.isDurable;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.IS_DURABLE</code>.
     */
    public QrtzJobDetails setIsDurable(String isDurable) {
        this.isDurable = isDurable;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.IS_NONCONCURRENT</code>.
     */
    public String getIsNonconcurrent() {
        return this.isNonconcurrent;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.IS_NONCONCURRENT</code>.
     */
    public QrtzJobDetails setIsNonconcurrent(String isNonconcurrent) {
        this.isNonconcurrent = isNonconcurrent;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.IS_UPDATE_DATA</code>.
     */
    public String getIsUpdateData() {
        return this.isUpdateData;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.IS_UPDATE_DATA</code>.
     */
    public QrtzJobDetails setIsUpdateData(String isUpdateData) {
        this.isUpdateData = isUpdateData;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.REQUESTS_RECOVERY</code>.
     */
    public String getRequestsRecovery() {
        return this.requestsRecovery;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.REQUESTS_RECOVERY</code>.
     */
    public QrtzJobDetails setRequestsRecovery(String requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_DATA</code>.
     */
    public byte[] getJobData() {
        return this.jobData;
    }

    /**
     * Setter for <code>scale_advisor.QRTZ_JOB_DETAILS.JOB_DATA</code>.
     */
    public QrtzJobDetails setJobData(byte[] jobData) {
        this.jobData = jobData;
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
        final QrtzJobDetails other = (QrtzJobDetails) obj;
        if (this.schedName == null) {
            if (other.schedName != null)
                return false;
        }
        else if (!this.schedName.equals(other.schedName))
            return false;
        if (this.jobName == null) {
            if (other.jobName != null)
                return false;
        }
        else if (!this.jobName.equals(other.jobName))
            return false;
        if (this.jobGroup == null) {
            if (other.jobGroup != null)
                return false;
        }
        else if (!this.jobGroup.equals(other.jobGroup))
            return false;
        if (this.description == null) {
            if (other.description != null)
                return false;
        }
        else if (!this.description.equals(other.description))
            return false;
        if (this.jobClassName == null) {
            if (other.jobClassName != null)
                return false;
        }
        else if (!this.jobClassName.equals(other.jobClassName))
            return false;
        if (this.isDurable == null) {
            if (other.isDurable != null)
                return false;
        }
        else if (!this.isDurable.equals(other.isDurable))
            return false;
        if (this.isNonconcurrent == null) {
            if (other.isNonconcurrent != null)
                return false;
        }
        else if (!this.isNonconcurrent.equals(other.isNonconcurrent))
            return false;
        if (this.isUpdateData == null) {
            if (other.isUpdateData != null)
                return false;
        }
        else if (!this.isUpdateData.equals(other.isUpdateData))
            return false;
        if (this.requestsRecovery == null) {
            if (other.requestsRecovery != null)
                return false;
        }
        else if (!this.requestsRecovery.equals(other.requestsRecovery))
            return false;
        if (this.jobData == null) {
            if (other.jobData != null)
                return false;
        }
        else if (!Arrays.equals(this.jobData, other.jobData))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.schedName == null) ? 0 : this.schedName.hashCode());
        result = prime * result + ((this.jobName == null) ? 0 : this.jobName.hashCode());
        result = prime * result + ((this.jobGroup == null) ? 0 : this.jobGroup.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.jobClassName == null) ? 0 : this.jobClassName.hashCode());
        result = prime * result + ((this.isDurable == null) ? 0 : this.isDurable.hashCode());
        result = prime * result + ((this.isNonconcurrent == null) ? 0 : this.isNonconcurrent.hashCode());
        result = prime * result + ((this.isUpdateData == null) ? 0 : this.isUpdateData.hashCode());
        result = prime * result + ((this.requestsRecovery == null) ? 0 : this.requestsRecovery.hashCode());
        result = prime * result + ((this.jobData == null) ? 0 : Arrays.hashCode(this.jobData));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("QrtzJobDetails (");

        sb.append(schedName);
        sb.append(", ").append(jobName);
        sb.append(", ").append(jobGroup);
        sb.append(", ").append(description);
        sb.append(", ").append(jobClassName);
        sb.append(", ").append(isDurable);
        sb.append(", ").append(isNonconcurrent);
        sb.append(", ").append(isUpdateData);
        sb.append(", ").append(requestsRecovery);
        sb.append(", ").append("[binary...]");

        sb.append(")");
        return sb.toString();
    }
}
