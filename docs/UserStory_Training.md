# User Story: Arrange Training for Part-Time Teachers

## User Story

**As** an administrator,
**I want to** create a training session and assign part-time teachers to it,
**so that** teachers can gain the skills needed to fulfil teaching requirements.

## Acceptance Criteria

1. The administrator can create a new training session by providing a name, date, and the skill to be taught.
2. The system assigns a unique ID to the new training.
3. The administrator can view all available training sessions.
4. The administrator can assign one or more teachers to a training session by teacher ID.
5. The system prevents duplicate enrolment (the same teacher cannot be added twice).
6. After a training is completed, the system updates each participating teacher's skill list — teachers who did not previously have the skill will gain it.
7. All training data is persisted to file when the program exits, and reloaded when it starts.

## Priority

High — training is a core part of the specification: "organise training for them."

## Estimate

Medium complexity (involves Training model, TrainingManager logic, integration with Teacher skill list, and file persistence via DataStore).
