# User Stories — Teacher Module (Member 2: Lu Liu)

## US-T1 — Register a New Part-Time Teacher

| Field | Content |
|---|---|
| **As a** | School administrator |
| **I want to** | Register a new part-time teacher with their name, subject skills, and weekly availability |
| **So that** | The system holds an up-to-date pool of teachers that can be matched to teaching requirements |
| **Priority** | High |

**Acceptance Criteria:**
- Given valid details are entered (name, at least one skill, at least one availability day),
- When I submit the registration,
- Then a new `Teacher` record is created with a unique auto-incremented ID,
- And the record is persisted to `data/teachers.csv` on program exit.
- If required fields are empty, the system displays a validation error and does not create the record.

---

## US-T2 — Search Teachers by Subject Skill

| Field | Content |
|---|---|
| **As a** | School administrator |
| **I want to** | Search the teacher pool by a specific subject skill (e.g. Java, Mathematics) |
| **So that** | I can quickly identify which teachers are qualified to cover a particular course |
| **Priority** | High |

**Acceptance Criteria:**
- Given I enter a skill keyword,
- When I submit the search,
- Then all teachers whose skill list contains that keyword (case-insensitive) are displayed in a formatted list.
- If no teachers are found, a clear "No teachers found" message is shown.
- The search does not modify any teacher records.

---

## US-T3 — Update Teacher Name

| Field | Content |
|---|---|
| **As a** | School administrator |
| **I want to** | Update the name of an existing teacher record by providing their ID and the corrected name |
| **So that** | Teacher records remain accurate when a name is entered incorrectly or legally changed |
| **Priority** | Medium |

**Acceptance Criteria:**
- Given a valid teacher ID and a non-empty new name,
- When I submit the update,
- Then the teacher's name is changed in memory and the updated record is persisted on the next save.
- If the ID does not exist, the system returns an error message and no record is changed.

---

## Notes

- All user stories are implemented in `Teacher.java` and `TeacherManager.java`.
- US-T1 and US-T2 are covered by the sequence diagram in `docs/sequence_diagram.png`.
- Persistence is handled by `FileHandler.java` (Member 5: Minghao Yue).
- Skills and availability use semicolon `;` as delimiter within CSV fields (see group data format spec).
