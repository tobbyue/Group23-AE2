import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FileHandler — concrete CSV-based implementation of DataStore.
 *
 * Design principles applied:
 *   - High cohesion: this class is solely responsible for CSV file I/O.
 *   - Low coupling: other modules depend on the DataStore interface,
 *     not on FileHandler directly. Swapping to a DatabaseHandler in
 *     the future requires zero changes to business logic classes.
 *   - Single Responsibility: each private helper method handles exactly
 *     one concern (parsing a line, writing a line, ensuring directories).
 *
 * File format assumptions (documented in 数据文件格式设计方案):
 *   - Each entity type has its own CSV file under data/
 *   - First line is always a header row
 *   - Fields separated by comma; nested lists use semicolon as delimiter
 *   - Encoding: UTF-8
 *
 * @author Minghao Yue (Group 23)
 */
public class FileHandler implements DataStore {

    private static final String DATA_DIR          = "data" + File.separator;
    private static final String TEACHERS_FILE     = DATA_DIR + "teachers.csv";
    private static final String REQUIREMENTS_FILE = DATA_DIR + "requirements.csv";
    private static final String TRAININGS_FILE    = DATA_DIR + "trainings.csv";
    private static final String ALLOCATIONS_FILE  = DATA_DIR + "allocations.csv";

    // =====================================================================
    //  LOAD methods — read CSV files into List of domain objects
    // =====================================================================

    @Override
    public List<Teacher> loadTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        List<String> lines = readLines(TEACHERS_FILE);

        // Skip header (line 0)
        for (int i = 1; i < lines.size(); i++) {
            String[] fields = parseLine(lines.get(i));
            if (fields.length < 4) continue; // skip malformed lines

            int id = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            List<String> skills = splitSemicolon(fields[2]);
            List<String> availability = splitSemicolon(fields[3]);

            teachers.add(new Teacher(id, name, skills, availability));
        }
        return teachers;
    }

    @Override
    public List<TeachingRequirement> loadRequirements() {
        List<TeachingRequirement> requirements = new ArrayList<>();
        List<String> lines = readLines(REQUIREMENTS_FILE);

        for (int i = 1; i < lines.size(); i++) {
            String[] fields = parseLine(lines.get(i));
            if (fields.length < 6) continue;

            int id = Integer.parseInt(fields[0].trim());
            String courseName = fields[1].trim();
            String semester = fields[2].trim();
            String requiredSkill = fields[3].trim();
            String dayOfWeek = fields[4].trim();
            String status = fields[5].trim();

            requirements.add(new TeachingRequirement(
                    id, courseName, semester, requiredSkill, dayOfWeek, status));
        }
        return requirements;
    }

    @Override
    public List<Training> loadTrainings() {
        List<Training> trainings = new ArrayList<>();
        List<String> lines = readLines(TRAININGS_FILE);

        for (int i = 1; i < lines.size(); i++) {
            String[] fields = parseLine(lines.get(i));
            if (fields.length < 5) continue;

            int id = Integer.parseInt(fields[0].trim());
            String name = fields[1].trim();
            String date = fields[2].trim();
            String skill = fields[3].trim();
            List<Integer> participantIds = parseIntList(fields[4]);

            trainings.add(new Training(id, name, date, skill, participantIds));
        }
        return trainings;
    }

    @Override
    public List<Allocation> loadAllocations() {
        List<Allocation> allocations = new ArrayList<>();
        List<String> lines = readLines(ALLOCATIONS_FILE);

        for (int i = 1; i < lines.size(); i++) {
            String[] fields = parseLine(lines.get(i));
            if (fields.length < 3) continue;

            int id = Integer.parseInt(fields[0].trim());
            int teacherId = Integer.parseInt(fields[1].trim());
            int requirementId = Integer.parseInt(fields[2].trim());

            allocations.add(new Allocation(id, teacherId, requirementId));
        }
        return allocations;
    }

    // =====================================================================
    //  SAVE methods — write List of domain objects back to CSV
    // =====================================================================

    @Override
    public void saveTeachers(List<Teacher> teachers) {
        List<String> lines = new ArrayList<>();
        lines.add("id,name,skills,availability"); // header

        for (Teacher t : teachers) {
            lines.add(toLine(
                    String.valueOf(t.getId()),
                    t.getName(),
                    t.skillsToString(),
                    t.availabilityToString()
            ));
        }
        writeLines(TEACHERS_FILE, lines);
    }

    @Override
    public void saveRequirements(List<TeachingRequirement> requirements) {
        List<String> lines = new ArrayList<>();
        lines.add("id,courseName,semester,requiredSkill,dayOfWeek,status");

        for (TeachingRequirement r : requirements) {
            lines.add(toLine(
                    String.valueOf(r.getId()),
                    r.getCourseName(),
                    r.getSemester(),
                    r.getRequiredSkill(),
                    r.getDayOfWeek(),
                    r.getStatus()
            ));
        }
        writeLines(REQUIREMENTS_FILE, lines);
    }

    @Override
    public void saveTrainings(List<Training> trainings) {
        List<String> lines = new ArrayList<>();
        lines.add("id,name,date,skill,participantIds");

        for (Training t : trainings) {
            lines.add(toLine(
                    String.valueOf(t.getId()),
                    t.getName(),
                    t.getDate(),
                    t.getSkill(),
                    t.participantIdsToString()
            ));
        }
        writeLines(TRAININGS_FILE, lines);
    }

    @Override
    public void saveAllocations(List<Allocation> allocations) {
        List<String> lines = new ArrayList<>();
        lines.add("id,teacherId,requirementId");

        for (Allocation a : allocations) {
            lines.add(toLine(
                    String.valueOf(a.getId()),
                    String.valueOf(a.getTeacherId()),
                    String.valueOf(a.getRequirementId())
            ));
        }
        writeLines(ALLOCATIONS_FILE, lines);
    }

    // =====================================================================
    //  Private utility methods
    // =====================================================================

    /**
     * Read all lines from a file. Returns empty list if the file does not
     * exist (first run scenario — the program starts with no data).
     */
    private List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return lines; // no data yet — perfectly valid on first run
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: could not read " + filePath + " — " + e.getMessage());
        }
        return lines;
    }

    /**
     * Write lines to a file, creating parent directories if needed.
     * Overwrites the file completely (full rewrite on each save).
     */
    private void writeLines(String filePath, List<String> lines) {
        ensureDataDir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error: could not write " + filePath + " — " + e.getMessage());
        }
    }

    /**
     * Parse a single CSV line into fields.
     * Simple comma-split — sufficient because our nested lists use semicolons.
     */
    private String[] parseLine(String line) {
        return line.split(",", -1);  // -1 keeps trailing empty strings
    }

    /**
     * Join fields into a CSV line.
     */
    private String toLine(String... fields) {
        return String.join(",", fields);
    }

    /**
     * Split a semicolon-delimited string into a list of trimmed strings.
     * e.g. "Java;Python;SQL" -> ["Java", "Python", "SQL"]
     */
    private List<String> splitSemicolon(String value) {
        List<String> result = new ArrayList<>();
        if (value == null || value.trim().isEmpty()) {
            return result;
        }
        String[] parts = value.split(";");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    /**
     * Parse a semicolon-delimited string of integers.
     * e.g. "1;3;5" -> [1, 3, 5]
     */
    private List<Integer> parseIntList(String value) {
        List<Integer> result = new ArrayList<>();
        if (value == null || value.trim().isEmpty()) {
            return result;
        }
        String[] parts = value.split(";");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                try {
                    result.add(Integer.parseInt(trimmed));
                } catch (NumberFormatException e) {
                    System.err.println("Warning: skipping invalid integer '" + trimmed + "'");
                }
            }
        }
        return result;
    }

    /**
     * Ensure the data/ directory exists.
     */
    private void ensureDataDir() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
