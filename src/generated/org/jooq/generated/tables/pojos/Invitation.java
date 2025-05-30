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
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String invitationToken;
    private Long projectId;
    private LocalDateTime expireDate;

    public Invitation() {}

    public Invitation(Invitation value) {
        this.invitationToken = value.invitationToken;
        this.projectId = value.projectId;
        this.expireDate = value.expireDate;
    }

    public Invitation(
        String invitationToken,
        Long projectId,
        LocalDateTime expireDate
    ) {
        this.invitationToken = invitationToken;
        this.projectId = projectId;
        this.expireDate = expireDate;
    }

    /**
     * Getter for <code>scale_advisor.INVITATION.INVITATION_TOKEN</code>.
     */
    public String getInvitationToken() {
        return this.invitationToken;
    }

    /**
     * Setter for <code>scale_advisor.INVITATION.INVITATION_TOKEN</code>.
     */
    public Invitation setInvitationToken(String invitationToken) {
        this.invitationToken = invitationToken;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.INVITATION.PROJECT_ID</code>.
     */
    public Long getProjectId() {
        return this.projectId;
    }

    /**
     * Setter for <code>scale_advisor.INVITATION.PROJECT_ID</code>.
     */
    public Invitation setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.INVITATION.EXPIRE_DATE</code>.
     */
    public LocalDateTime getExpireDate() {
        return this.expireDate;
    }

    /**
     * Setter for <code>scale_advisor.INVITATION.EXPIRE_DATE</code>.
     */
    public Invitation setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
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
        final Invitation other = (Invitation) obj;
        if (this.invitationToken == null) {
            if (other.invitationToken != null)
                return false;
        }
        else if (!this.invitationToken.equals(other.invitationToken))
            return false;
        if (this.projectId == null) {
            if (other.projectId != null)
                return false;
        }
        else if (!this.projectId.equals(other.projectId))
            return false;
        if (this.expireDate == null) {
            if (other.expireDate != null)
                return false;
        }
        else if (!this.expireDate.equals(other.expireDate))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.invitationToken == null) ? 0 : this.invitationToken.hashCode());
        result = prime * result + ((this.projectId == null) ? 0 : this.projectId.hashCode());
        result = prime * result + ((this.expireDate == null) ? 0 : this.expireDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Invitation (");

        sb.append(invitationToken);
        sb.append(", ").append(projectId);
        sb.append(", ").append(expireDate);

        sb.append(")");
        return sb.toString();
    }
}
