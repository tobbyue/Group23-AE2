/**
 * Represents an assignment of a teacher to a teaching requirement.
 *
 * Fields based on allocations.csv:
 *   id, teacherId, requirementId
 *
 * This is a simple association record — the matching logic lives in
 * StaffAllocator (Jie Ding's module).
 *
 * @author Jie Ding (Group 23)
 */
public class Allocation {

    private int id;
    private int teacherId;
    private int requirementId;

    // ---------- Constructors ----------

    public Allocation(int id, int teacherId, int requirementId) {
        this.id = id;
        this.teacherId = teacherId;
        this.requirementId = requirementId;
    }

    // ---------- Getters & Setters ----------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getRequirementId() { return requirementId; }
    public void setRequirementId(int requirementId) { this.requirementId = requirementId; }

    @Override
    public String toString() {
        return "Allocation{id=" + id + ", teacherId=" + teacherId
                + ", requirementId=" + requirementId + "}";
    }
}
