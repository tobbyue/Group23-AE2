import java.util.ArrayList;
import java.util.List;

/**
 * TrainingManager — handles creation, listing, and teacher assignment
 * for training sessions.
 *
 * Design notes:
 *   - High cohesion: all training-related business logic is in this class.
 *   - Low coupling: depends only on the Training and Teacher models.
 *     It does NOT depend on FileHandler — persistence is handled
 *     by the DataStore interface at the App level.
 *   - The manager operates on the shared in-memory list; changes are
 *     automatically persisted when App calls dataStore.saveAll() on exit.
 *
 * Assumption: when a teacher completes a training, they gain the
 * corresponding skill. This is handled by updateTeacherSkills().
 *
 * @author Minghao Yue (Group 23)
 */
public class TrainingManager {

    private List<Training> trainings;
    private List<Teacher> teachers;  // reference for skill updates

    /**
     * Construct a TrainingManager with shared in-memory lists.
     *
     * @param trainings the list of Training objects (loaded from file)
     * @param teachers  the list of Teacher objects (needed for skill updates)
     */
    public TrainingManager(List<Training> trainings, List<Teacher> teachers) {
        this.trainings = trainings;
        this.teachers = teachers;
    }

    // =====================================================================
    //  CREATE
    // =====================================================================

    /**
     * Create a new training session and add it to the list.
     *
     * @param name  training name, e.g. "Java Refresher"
     * @param date  date string in YYYY-MM-DD format
     * @param skill the skill this training teaches
     * @return the newly created Training object
     */
    public Training createTraining(String name, String date, String skill) {
        int newId = generateNextId();
        Training training = new Training(newId, name, date, skill, new ArrayList<>());
        trainings.add(training);
        return training;
    }

    // =====================================================================
    //  READ / QUERY
    // =====================================================================

    /**
     * Return all trainings.
     */
    public List<Training> listAllTrainings() {
        return trainings;
    }

    /**
     * Find a training by its ID.
     *
     * @param id the training ID
     * @return the Training, or null if not found
     */
    public Training findTrainingById(int id) {
        for (Training t : trainings) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Find all trainings that teach a specific skill.
     *
     * @param skill the skill to search for
     * @return list of matching trainings (may be empty)
     */
    public List<Training> findTrainingsBySkill(String skill) {
        List<Training> result = new ArrayList<>();
        for (Training t : trainings) {
            if (t.getSkill().equalsIgnoreCase(skill)) {
                result.add(t);
            }
        }
        return result;
    }

    // =====================================================================
    //  ASSIGN TEACHER
    // =====================================================================

    /**
     * Assign a teacher to a training session.
     *
     * Validates:
     *   1. Training exists
     *   2. Teacher exists
     *   3. Teacher is not already enrolled
     *
     * @param trainingId the training ID
     * @param teacherId  the teacher ID
     * @return true if assignment succeeded, false otherwise
     */
    public boolean assignTeacher(int trainingId, int teacherId) {
        Training training = findTrainingById(trainingId);
        if (training == null) {
            System.out.println("Error: Training ID " + trainingId + " not found.");
            return false;
        }

        Teacher teacher = findTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("Error: Teacher ID " + teacherId + " not found.");
            return false;
        }

        if (training.getParticipantIds().contains(teacherId)) {
            System.out.println("Teacher '" + teacher.getName()
                    + "' is already enrolled in this training.");
            return false;
        }

        training.addParticipant(teacherId);
        System.out.println("Teacher '" + teacher.getName()
                + "' assigned to training '" + training.getName() + "'.");
        return true;
    }

    /**
     * Remove a teacher from a training session.
     *
     * @param trainingId the training ID
     * @param teacherId  the teacher ID
     * @return true if removal succeeded, false otherwise
     */
    public boolean removeTeacher(int trainingId, int teacherId) {
        Training training = findTrainingById(trainingId);
        if (training == null) {
            System.out.println("Error: Training ID " + trainingId + " not found.");
            return false;
        }

        boolean removed = training.getParticipantIds().remove(Integer.valueOf(teacherId));
        if (removed) {
            System.out.println("Teacher ID " + teacherId
                    + " removed from training '" + training.getName() + "'.");
        } else {
            System.out.println("Teacher ID " + teacherId
                    + " was not enrolled in this training.");
        }
        return removed;
    }

    // =====================================================================
    //  SKILL UPDATE (post-training)
    // =====================================================================

    /**
     * After a training is completed, update each participant's skill list.
     * If a teacher does not already have the skill, it is added.
     *
     * @param trainingId the training to mark as completed
     * @return the number of teachers whose skills were updated
     */
    public int updateTeacherSkills(int trainingId) {
        Training training = findTrainingById(trainingId);
        if (training == null) {
            System.out.println("Error: Training ID " + trainingId + " not found.");
            return 0;
        }

        String skill = training.getSkill();
        if (skill == null || skill.trim().isEmpty()) {
            System.out.println("Error: Training '" + training.getName() + "' has no skill defined.");
            return 0;
        }
        int updatedCount = 0;

        for (int tid : training.getParticipantIds()) {
            Teacher teacher = findTeacherById(tid);
            if (teacher != null && !teacher.hasSkill(skill)) {
                teacher.addSkill(skill);
                updatedCount++;
                System.out.println("  Skill '" + skill + "' added to teacher '"
                        + teacher.getName() + "'.");
            }
        }

        if (updatedCount == 0) {
            System.out.println("All participants already had the skill '" + skill + "'.");
        } else {
            System.out.println(updatedCount + " teacher(s) gained the skill '" + skill + "'.");
        }
        return updatedCount;
    }

    // =====================================================================
    //  DISPLAY
    // =====================================================================

    /**
     * Print a formatted summary of a training, including participant names.
     *
     * @param training the training to display
     */
    public void displayTrainingDetails(Training training) {
        System.out.println("-----------------------------------");
        System.out.println("Training ID   : " + training.getId());
        System.out.println("Name          : " + training.getName());
        System.out.println("Date          : " + training.getDate());
        System.out.println("Skill         : " + training.getSkill());
        System.out.print("Participants  : ");

        List<Integer> pids = training.getParticipantIds();
        if (pids.isEmpty()) {
            System.out.println("(none)");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pids.size(); i++) {
                if (i > 0) sb.append(", ");
                Teacher t = findTeacherById(pids.get(i));
                if (t != null) {
                    sb.append(t.getName()).append(" (ID ").append(t.getId()).append(")");
                } else {
                    sb.append("Unknown (ID ").append(pids.get(i)).append(")");
                }
            }
            System.out.println(sb.toString());
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Print a summary table of all trainings.
     */
    public void displayAllTrainings() {
        if (trainings.isEmpty()) {
            System.out.println("No training sessions found.");
            return;
        }

        System.out.println("\n===== All Training Sessions =====");
        System.out.printf("%-4s %-22s %-12s %-12s %-6s%n",
                "ID", "Name", "Date", "Skill", "#Part");
        System.out.println("-".repeat(60));

        for (Training t : trainings) {
            System.out.printf("%-4d %-22s %-12s %-12s %-6d%n",
                    t.getId(),
                    t.getName(),
                    t.getDate(),
                    t.getSkill(),
                    t.getParticipantIds().size());
        }
    }

    // =====================================================================
    //  Private helpers
    // =====================================================================

    /**
     * Generate the next available ID (max existing ID + 1).
     */
    private int generateNextId() {
        int maxId = 0;
        for (Training t : trainings) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Find a teacher by ID from the shared teacher list.
     */
    private Teacher findTeacherById(int id) {
        for (Teacher t : teachers) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
}
