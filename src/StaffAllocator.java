import java.util.List;
import java.util.ArrayList;

/**
 * StaffAllocator — matches teachers to teaching requirements.
 *
 * Matching rules:
 *   1. Teacher must have the required skill
 *   2. Teacher must be available on the required day
 *   3. Teacher must not already be assigned to another requirement on the same day
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

    public List<Allocation> getAllocations() { return allocations; }

    // ---------- Manual allocation ----------

    /**
     * Manually allocate a specific teacher to a specific requirement.
     * Returns true if successful, false if already assigned or invalid.
     */
    public boolean allocate(int teacherId, int requirementId) {
        Teacher teacher = findTeacherById(teacherId);
        TeachingRequirement req = findRequirementById(requirementId);

        if (teacher == null || req == null) {
            System.out.println("Invalid teacher ID or requirement ID.");
            return false;
        }
        if (!req.getStatus().equals("OPEN")) {
            System.out.println("Requirement is already ASSIGNED.");
            return false;
        }
        if (!hasRequiredSkill(teacher, req.getRequiredSkill())) {
            System.out.println("Teacher does not have the required skill.");
            return false;
        }
        if (!isAvailableOnDay(teacher, req.getDayOfWeek())) {
            System.out.println("Teacher is not available on the required day.");
            return false;
        }
        if (isAlreadyAssignedOnDay(teacher, req.getDayOfWeek())) {
            System.out.println("Teacher already has another assignment on that day.");
            return false;
        }

        int newId = generateNextId();
        allocations.add(new Allocation(newId, teacherId, requirementId));
        req.setStatus("ASSIGNED");
        System.out.println("Successfully allocated: " + teacher.getName()
                + " -> " + req.getCourseName());
        return true;
    }

    // ---------- Auto match ----------

    /**
     * Automatically find and assign the first suitable teacher
     * for a given requirement.
     */
    public Teacher autoMatch(TeachingRequirement req) {
        if (!req.getStatus().equals("OPEN")) {
            System.out.println("Requirement is already ASSIGNED.");
            return null;
        }
        for (Teacher teacher : teachers) {
            if (hasRequiredSkill(teacher, req.getRequiredSkill())
                    && isAvailableOnDay(teacher, req.getDayOfWeek())
                    && !isAlreadyAssignedOnDay(teacher, req.getDayOfWeek())) {

                int newId = generateNextId();
                allocations.add(new Allocation(newId, teacher.getId(), req.getId()));
                req.setStatus("ASSIGNED");
                System.out.println("Auto-matched: " + teacher.getName()
                        + " -> " + req.getCourseName());
                return teacher;
            }
        }
        System.out.println("No suitable teacher found for: " + req.getCourseName());
        return null;
    }

    // ---------- List allocations ----------

    public void listAllocations() {
        if (allocations.isEmpty()) {
            System.out.println("No allocations found.");
            return;
        }
        System.out.println("ID | TeacherID | RequirementID");
        System.out.println("--------------------------------");
        for (Allocation a : allocations) {
            System.out.println(a.getId() + "  | "
                    + a.getTeacherId() + "         | "
                    + a.getRequirementId());
        }
    }

    // ---------- Remove allocation ----------

    public boolean removeAllocation(int allocationId) {
        Allocation target = null;
        for (Allocation a : allocations) {
            if (a.getId() == allocationId) {
                target = a;
                break;
            }
        }
        if (target == null) {
            System.out.println("Allocation not found.");
            return false;
        }
        // Reset requirement status back to OPEN
        TeachingRequirement req = findRequirementById(target.getRequirementId());
        if (req != null) {
            req.setStatus("OPEN");
        }
        allocations.remove(target);
        System.out.println("Allocation " + allocationId + " removed.");
        return true;
    }

    // ---------- Helper methods ----------

    private int generateNextId() {
        int max = 0;
        for (Allocation a : allocations) {
            if (a.getId() > max) max = a.getId();
        }
        return max + 1;
    }

    private Teacher findTeacherById(int id) {
        for (Teacher t : teachers) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    private TeachingRequirement findRequirementById(int id) {
        for (TeachingRequirement r : requirements) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    private boolean hasRequiredSkill(Teacher teacher, String requiredSkill) {
        for (String skill : teacher.getSkills()) {
            if (skill.trim().equalsIgnoreCase(requiredSkill.trim())) return true;
        }
        return false;
    }

    private boolean isAvailableOnDay(Teacher teacher, String dayOfWeek) {
        for (String day : teacher.getAvailability()) {
            if (day.trim().equalsIgnoreCase(dayOfWeek.trim())) return true;
        }
        return false;
    }

    private boolean isAlreadyAssignedOnDay(Teacher teacher, String dayOfWeek) {
        for (Allocation a : allocations) {
            if (a.getTeacherId() == teacher.getId()) {
                TeachingRequirement req = findRequirementById(a.getRequirementId());
                if (req != null && req.getDayOfWeek().equalsIgnoreCase(dayOfWeek)) {
                    return true;
                }
            }
        }
        return false;
    }
}