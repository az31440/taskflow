# TaskFlow Project

## Overview
TaskFlow is a task management system built using Java and Spring Boot. It enables users to manage tasks effectively by providing APIs for creating, updating, and retrieving tasks. The system emphasizes simplicity and efficiency, allowing developers to interact with it via RESTful APIs.

---

## Features
- **Create Tasks**: Add new tasks with a name, status, and description.
- **Update Task Description**: Modify the description of an existing task.
- **Update Task Status**: Change the status of a task (e.g., TO_DO, IN_PROGRESS, COMPLETED).
- **Retrieve Tasks**: Fetch all tasks or specific tasks by ID.

---

## Technologies Used
- **Backend**: Spring Boot
- **Testing**: JUnit 5, Mockito, MockMvc
- **Build Tool**: Gradle
- **Database**: H2 (in-memory database for development/testing)

---

## Installation

### Prerequisites
1. **Java**: Ensure you have Java 17 or later installed.
2. **Gradle**: Ensure Gradle is installed or use the wrapper included in the project.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/az31440/taskflow.git
   cd taskflow
   ```
2. Build the project:
   ```bash
   ./gradlew build
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```
4. Access the API documentation (e.g., Swagger) at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

## Endpoints

### 1. Update Task Description
**Endpoint**: `PATCH /tasks/{id}/description`

**Description**: Updates the description of a task.

**Request**:
- **Path Parameter**: `id` (Long) - ID of the task to be updated.
- **Body**: `String` - The new description of the task.

**Example**:
```bash
curl -X PATCH "http://localhost:8080/tasks/1/description" \
-H "Content-Type: application/json" \
-d "Updated description"
```

**Response**:
```json
{
    "id": 1,
    "name": "Test Task",
    "status": "TO_DO",
    "description": "Updated description"
}
```

---

## Running Tests

### Prerequisites
Ensure you have JUnit 5 and Mockito dependencies included in your `build.gradle`.

### Running Tests
Execute the following command:
```bash
./gradlew test
```

### Sample Test (TaskControllerTest)
```java
    @Test
    public void testGetTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Task"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("TO_DO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test description"));
    }
```

---

## Contributing
1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Description of changes"
   ```
4. Push your branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## License
This project is licensed under the [MIT License](LICENSE).

---

## Contact
For questions or suggestions, please contact **Arben Zenullai** at [az31440@seeu.edu.mk](mailto:az31440@seeu.edu.mk).

