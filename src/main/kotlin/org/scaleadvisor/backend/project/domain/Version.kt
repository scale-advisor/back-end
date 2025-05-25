package org.scaleadvisor.backend.project.domain

import org.scaleadvisor.backend.project.domain.id.ProjectId

data class Version(
    val projectId: ProjectId,
    var versionNumber: VersionNumber,
) {

    constructor(
        projectId: ProjectId,
        versionNumber: String
    ) : this(
        projectId,
        VersionNumber.of(versionNumber)
    )

    constructor(
        projectId: ProjectId,
        major: Int,
        minor: Int
    ) : this(
        projectId,
        VersionNumber.of(major, minor)
    )
}
