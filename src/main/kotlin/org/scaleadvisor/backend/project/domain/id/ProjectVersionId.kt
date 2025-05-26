import org.scaleadvisor.backend.project.domain.id.ProjectId
import java.io.Serializable

@JvmInline
value class ProjectVersionId(val raw: String) : Serializable {

    val projectId: ProjectId
        get() = ProjectId.from(raw.substringBefore(':'))

    val major: Int
        get() = raw.substringAfter(':')
            .substringBefore(':')
            .toInt()

    val minor: Int
        get() = raw.substringAfterLast(':').toInt()

    companion object {
        fun of(projectId: ProjectId, major: Int, minor: Int) =
            ProjectVersionId("${projectId.value}:$major:$minor")
    }
}