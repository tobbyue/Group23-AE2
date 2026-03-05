import java.util.ArrayList;
import java.util.List;

/**
 * Represents a training session that can be organised for part-time teachers.
 *
 * Fields based on trainings.csv:
 *   id, name, date, skill, participantIds (semicolon-separated)
 *
 * Assumption: after a training is completed, each participant gains the
 * corresponding skill (updated via TeacherManager or App logic).
 *
 * @author Minghao Yue (Group 23)
 */
public class Training {

    private int id;
    private String name;           // e.g. "Java Refresher"
    private String date;           // format: "YYYY-MM-DD"
    private String skill;          // skill taught in this training
    private List<Integer> participantIds;  // teacher IDs

    // ---------- Constructors ----------

    public Training(int id, String name, String date, String skill,
                    List<Integer> participantIds) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.skill = skill;
        this.participantIds = new ArrayList<>(participantIds);
    }

    // ---------- Getters & Setters ----------

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSkill() { return skill; }
    public void setSkill(String skill) { this.skill = skill; }

    public List<Integer> getParticipantIds() { return participantIds; }
    public void setParticipantIds(List<Integer> participantIds) {
        this.participantIds = new ArrayList<>(participantIds);
    }

    // ---------- Convenience ----------

    public void addParticipant(int teacherId) {
        if (!participantIds.contains(teacherId)) {
            participantIds.add(teacherId);
        }
    }

    /** Semicolon-joined participant IDs for CSV output. */
    public String participantIdsToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < participantIds.size(); i++) {
            if (i > 0) sb.append(";");
            sb.append(participantIds.get(i));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Training{id=" + id + ", name='" + name + "', date='" + date
                + "', skill='" + skill + "', participants=" + participantIds + "}";
    }
}
