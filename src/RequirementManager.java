import java.util.List;

/**
 * RequirementManager — handles CRUD operations for TeachingRequirement objects.
 *
 * TODO (Yanyu Ge): implement the following methods
 *   - addRequirement(...)
 *   - findRequirementById(int id)
 *   - listOpenRequirements()
 *   - listAllRequirements()
 *   - updateStatus(int id, String newStatus)
 *   - generateNextId()
 *
 * User Story:
 *   "As a course director, I want to submit teaching requirements for the
 *    new semester so that the administrator can find suitable staff."
 *
 * @author Yanyu Ge (Group 23)
 */
public class RequirementManager {

    private List<TeachingRequirement> requirements;

    public RequirementManager(List<TeachingRequirement> requirements) {
        this.requirements = requirements;
    }

    // TODO: implement CRUD methods

    public List<TeachingRequirement> getRequirements() {
        return requirements;
    }
}
