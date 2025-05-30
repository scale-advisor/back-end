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
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fileId;
    private Long projectId;
    private Integer versionMajorNumber;
    private Integer versionMinorNumber;
    private String type;
    private String name;
    private Long uploaderId;
    private String path;
    private String extension;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public File() {}

    public File(File value) {
        this.fileId = value.fileId;
        this.projectId = value.projectId;
        this.versionMajorNumber = value.versionMajorNumber;
        this.versionMinorNumber = value.versionMinorNumber;
        this.type = value.type;
        this.name = value.name;
        this.uploaderId = value.uploaderId;
        this.path = value.path;
        this.extension = value.extension;
        this.createdAt = value.createdAt;
        this.updatedAt = value.updatedAt;
    }

    public File(
        Long fileId,
        Long projectId,
        Integer versionMajorNumber,
        Integer versionMinorNumber,
        String type,
        String name,
        Long uploaderId,
        String path,
        String extension,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.fileId = fileId;
        this.projectId = projectId;
        this.versionMajorNumber = versionMajorNumber;
        this.versionMinorNumber = versionMinorNumber;
        this.type = type;
        this.name = name;
        this.uploaderId = uploaderId;
        this.path = path;
        this.extension = extension;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>scale_advisor.FILE.FILE_ID</code>.
     */
    public Long getFileId() {
        return this.fileId;
    }

    /**
     * Setter for <code>scale_advisor.FILE.FILE_ID</code>.
     */
    public File setFileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.PROJECT_ID</code>.
     */
    public Long getProjectId() {
        return this.projectId;
    }

    /**
     * Setter for <code>scale_advisor.FILE.PROJECT_ID</code>.
     */
    public File setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.VERSION_MAJOR_NUMBER</code>.
     */
    public Integer getVersionMajorNumber() {
        return this.versionMajorNumber;
    }

    /**
     * Setter for <code>scale_advisor.FILE.VERSION_MAJOR_NUMBER</code>.
     */
    public File setVersionMajorNumber(Integer versionMajorNumber) {
        this.versionMajorNumber = versionMajorNumber;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.VERSION_MINOR_NUMBER</code>.
     */
    public Integer getVersionMinorNumber() {
        return this.versionMinorNumber;
    }

    /**
     * Setter for <code>scale_advisor.FILE.VERSION_MINOR_NUMBER</code>.
     */
    public File setVersionMinorNumber(Integer versionMinorNumber) {
        this.versionMinorNumber = versionMinorNumber;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.TYPE</code>.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for <code>scale_advisor.FILE.TYPE</code>.
     */
    public File setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.NAME</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>scale_advisor.FILE.NAME</code>.
     */
    public File setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.UPLOADER_ID</code>.
     */
    public Long getUploaderId() {
        return this.uploaderId;
    }

    /**
     * Setter for <code>scale_advisor.FILE.UPLOADER_ID</code>.
     */
    public File setUploaderId(Long uploaderId) {
        this.uploaderId = uploaderId;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.PATH</code>.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Setter for <code>scale_advisor.FILE.PATH</code>.
     */
    public File setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.EXTENSION</code>.
     */
    public String getExtension() {
        return this.extension;
    }

    /**
     * Setter for <code>scale_advisor.FILE.EXTENSION</code>.
     */
    public File setExtension(String extension) {
        this.extension = extension;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.CREATED_AT</code>.
     */
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Setter for <code>scale_advisor.FILE.CREATED_AT</code>.
     */
    public File setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Getter for <code>scale_advisor.FILE.UPDATED_AT</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>scale_advisor.FILE.UPDATED_AT</code>.
     */
    public File setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
        final File other = (File) obj;
        if (this.fileId == null) {
            if (other.fileId != null)
                return false;
        }
        else if (!this.fileId.equals(other.fileId))
            return false;
        if (this.projectId == null) {
            if (other.projectId != null)
                return false;
        }
        else if (!this.projectId.equals(other.projectId))
            return false;
        if (this.versionMajorNumber == null) {
            if (other.versionMajorNumber != null)
                return false;
        }
        else if (!this.versionMajorNumber.equals(other.versionMajorNumber))
            return false;
        if (this.versionMinorNumber == null) {
            if (other.versionMinorNumber != null)
                return false;
        }
        else if (!this.versionMinorNumber.equals(other.versionMinorNumber))
            return false;
        if (this.type == null) {
            if (other.type != null)
                return false;
        }
        else if (!this.type.equals(other.type))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.uploaderId == null) {
            if (other.uploaderId != null)
                return false;
        }
        else if (!this.uploaderId.equals(other.uploaderId))
            return false;
        if (this.path == null) {
            if (other.path != null)
                return false;
        }
        else if (!this.path.equals(other.path))
            return false;
        if (this.extension == null) {
            if (other.extension != null)
                return false;
        }
        else if (!this.extension.equals(other.extension))
            return false;
        if (this.createdAt == null) {
            if (other.createdAt != null)
                return false;
        }
        else if (!this.createdAt.equals(other.createdAt))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.fileId == null) ? 0 : this.fileId.hashCode());
        result = prime * result + ((this.projectId == null) ? 0 : this.projectId.hashCode());
        result = prime * result + ((this.versionMajorNumber == null) ? 0 : this.versionMajorNumber.hashCode());
        result = prime * result + ((this.versionMinorNumber == null) ? 0 : this.versionMinorNumber.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.uploaderId == null) ? 0 : this.uploaderId.hashCode());
        result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
        result = prime * result + ((this.extension == null) ? 0 : this.extension.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("File (");

        sb.append(fileId);
        sb.append(", ").append(projectId);
        sb.append(", ").append(versionMajorNumber);
        sb.append(", ").append(versionMinorNumber);
        sb.append(", ").append(type);
        sb.append(", ").append(name);
        sb.append(", ").append(uploaderId);
        sb.append(", ").append(path);
        sb.append(", ").append(extension);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(updatedAt);

        sb.append(")");
        return sb.toString();
    }
}
