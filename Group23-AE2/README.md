# Group 23 — SEIT Assessed Exercise 2

**Course:** Software Engineering (IT) — COMPSCI5059
**Deadline:** 18 March 2026, 16:30

## Team Members & Responsibilities

| Member | Role | Classes |
|--------|------|---------|
| **Minghao Yue** (Lead) | File storage + Training + Report integration | `FileHandler`, `DataStore`, `Training` |
| **Tianxin Han** | Console UI | `App`, `MenuController` |
| **Lu Liu** | Teacher module | `Teacher`, `TeacherManager` |
| **Yanyu Ge** | Requirement module | `TeachingRequirement`, `RequirementManager` |
| **Jie Ding** | Matching & allocation | `Allocation`, `StaffAllocator` |
| **Wenxi Zhang** | UML + Design Commentary + Retrospective | Class Diagram, Sequence Diagrams |

---

## Project Structure

```
Group23-AE2/
├── src/                    # All Java source files (no packages, flat structure)
│   ├── App.java            # Main entry point (Tianxin)
│   ├── DataStore.java      # Persistence interface (Minghao)
│   ├── FileHandler.java    # CSV implementation of DataStore (Minghao)
│   ├── Teacher.java        # Teacher model (Lu)
│   ├── TeacherManager.java # Teacher CRUD operations (Lu)
│   ├── TeachingRequirement.java  # Requirement model (Yanyu)
│   ├── RequirementManager.java   # Requirement CRUD (Yanyu)
│   ├── Training.java       # Training model (Minghao)
│   ├── Allocation.java     # Allocation model (Jie)
│   └── StaffAllocator.java # Matching logic (Jie)
├── data/                   # CSV data files (read on startup, written on exit)
│   ├── teachers.csv
│   ├── requirements.csv
│   ├── trainings.csv
│   └── allocations.csv
├── out/                    # Compiled .class files (git-ignored)
├── compile.sh              # One-command compile script
├── run.sh                  # One-command run script
├── .gitignore
└── README.md
```

---

## Quick Start

### Compile
```bash
./compile.sh
```
or manually:
```bash
javac -d out src/*.java
```

### Run
```bash
./run.sh
```
or manually:
```bash
java -cp out App
```

> **Note:** The program reads from `data/*.csv` on startup. If the `data/` folder is empty or missing, the program starts with empty data (first-run scenario).

---

## How to Contribute (Git Workflow)

### 1. Clone the repo
```bash
git clone https://github.com/<your-org>/Group23-AE2.git
cd Group23-AE2
```

### 2. Create your own branch
```bash
git checkout -b feature/<your-name>
# e.g. git checkout -b feature/lu-teacher
```

### 3. Work on YOUR classes only
Edit only the files you are responsible for (see table above).
If you need to change another member's file, discuss in the group chat first.

### 4. Compile & test locally
```bash
./compile.sh && ./run.sh
```

### 5. Commit & push
```bash
git add src/YourFile.java
git commit -m "Add: brief description of what you did"
git push origin feature/<your-name>
```

### 6. Create a Pull Request
Go to GitHub → Pull Requests → New PR → select your branch → create PR.
At least one other member should review before merging.

---

## Key Design Decisions

1. **DataStore interface** — All managers use `DataStore` (not `FileHandler` directly).
   This means a future `DatabaseHandler` can be swapped in with zero changes to business logic.

2. **Multiple CSV files** — Each entity gets its own file (`teachers.csv`, `requirements.csv`, etc.).
   Easier to debug, and maps naturally to future database tables.

3. **Semicolon as nested delimiter** — Lists within CSV fields (skills, availability, participant IDs) use `;` to avoid conflicts with the `,` field separator.

4. **Standard Java only** — No external libraries. Uses `BufferedReader` / `BufferedWriter` + `String.split`.

---

## CSV Format Reference

### teachers.csv
```
id,name,skills,availability
1,John Smith,Java;Python;SQL,Mon;Tue;Wed
```

### requirements.csv
```
id,courseName,semester,requiredSkill,dayOfWeek,status
1,Intro to Java,2026-S1,Java,Mon,OPEN
```

### trainings.csv
```
id,name,date,skill,participantIds
1,Java Refresher,2026-03-10,Java,1;3
```

### allocations.csv
```
id,teacherId,requirementId
1,1,1
```

---

## Checklist Before Submission

- [ ] All Java classes compile with `javac`
- [ ] Program runs end-to-end (load → interact → save)
- [ ] Each member's classes are clearly attributed (comments + report)
- [ ] Class Structure Diagram is up-to-date
- [ ] Sequence Diagrams match the final implementation
- [ ] Design Commentary ~600 words
- [ ] Running screenshots captured
- [ ] Retrospective written
- [ ] Report filename: `Group23_SEIT_AE2_GroupReport.pdf`
- [ ] Each member submits Individual Deltas on Moodle
