import java.util.List;

/**
 * StaffAllocator — matches teachers to teaching requirements.
 *
 * Matching rules:
 *   1. Teacher must have the required skill
 *   2. Teacher must be available on the required day
 *   3. Teacher must not already be assigned to another requirement on the same day
 *
 * TODO (Jie Ding): implement the following methods
 *   - allocate(int teacherId, int requirementId) — manual allocation
 *   - autoMatch(TeachingRequirement req) — find suitable teachers
 *   - listAllocations()
 *   - removeAllocation(int allocationId)
 *   - generateNextId()
 *
 * User Story:
 *   "As an administrator, I want to match suitable part-time teachers
 *    to teaching requirements based on skills and availability."
 *
 * @author Jie Ding (Group 23)
 */
public class StaffAllocator {

    private List<Teacher> teachers;
    private List<TeachingRequirement> requirements;
    private List<Allocation> allocations;

    public StaffAllocator(List<Teacher> teachers,
                          List<TeachingRequirement> requirements,
                          List<Allocation> allocations) {
        this.teachers = teachers;
        this.requirements = requirements;
        this.allocations = allocations;
    }

    // TODO: implement matching and allocation methods

    public List<Allocation> getAllocations() {
        return allocations;
    }
}
