import java.util.List;

/**
 * TeacherManager — handles CRUD operations for Teacher objects.
 *
 * TODO (Lu Liu): implement the following methods
 *   - addTeacher(...)
 *   - removeTeacher(int id)
 *   - findTeacherById(int id)
 *   - findTeachersBySkill(String skill)
 *   - listAllTeachers()
 *   - generateNextId()
 *
 * User Story:
 *   "As an administrator, I want to add new part-time teachers to the system
 *    so that they can be considered for teaching assignments."
 *
 * @author Lu Liu (Group 23)
 */
public class TeacherManager {

    private List<Teacher> teachers;

    public TeacherManager(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    // TODO: implement CRUD methods

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
