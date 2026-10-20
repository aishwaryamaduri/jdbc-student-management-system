# Student Management System

A beginner-friendly Java console application that stores student records in an embedded H2 database. H2 runs inside the application: you do not need to install or start MySQL or another database server.

## Project files

- `src/main/java/com/example/studentmanagement/Student.java` — represents a student with an ID, name, and email.
- `src/main/java/com/example/studentmanagement/DatabaseConnection.java` — opens a connection to the local H2 database file.
- `src/main/java/com/example/studentmanagement/StudentDAO.java` — creates the table and implements the student database operations using JDBC `PreparedStatement`.
- `src/main/java/com/example/studentmanagement/StudentManagementApp.java` — initializes the database and displays the console menu.
- `sql/create_database.sql` — H2-compatible table definition; the application creates the table automatically, so you do not need to run this file.
- `pom.xml` — specifies Java 17, the H2 JDBC dependency, and Maven build/run plugins.
- `.gitignore` — excludes build output, local IDE settings, and H2 database files.

## Prerequisites

Install a JDK 17 or newer and Apache Maven. If Maven is not installed, download its **Binary zip archive** from the [official Maven installation page](https://maven.apache.org/install.html), extract it to a folder such as `C:\tools\apache-maven`, and add that folder's `bin` directory to your Windows user `Path`. Close and reopen PowerShell or VS Code after updating `Path`.

Verify Java and Maven from PowerShell:

```powershell
java -version
javac -version
mvn -version
```

To work in VS Code, open the project folder and install the recommended **Extension Pack for Java** if prompted. No MySQL installation, server, account, or password is required.

## Build and run on Windows

1. Open PowerShell or the VS Code terminal.
2. Change to the project folder (adjust if you saved it elsewhere):

   ```powershell
   Set-Location 'C:\task2'
   ```

3. Build the project and download the H2 JDBC dependency:

   ```powershell
   mvn package
   ```

4. Start the console application:

   ```powershell
   mvn exec:java
   ```

On the first run, the app creates the `data` folder, opens `data/student_management.mv.db`, and automatically creates the `students` table. Student records persist between runs. Maven handles the H2 driver; no separate JAR setup is needed.

## Use and test each operation

At the menu, enter:

1. **Create:** choose `1`, enter a name and email, and note the new ID.
2. **Read all:** choose `2` to see the saved students.
3. **Read one:** choose `3` and enter the student's ID.
4. **Update:** choose `4`, enter the ID and new values, then choose `2` to confirm.
5. **Delete:** choose `5`, enter the ID, and type `y` to confirm. Choose `2` to verify the student was removed. Any other response cancels deletion.
6. Choose `0` to exit. Start the app again to confirm that saved records remain.

To test not-found handling, find, update, or delete an ID that is not listed. To test duplicate email handling, try to add an email already used by another student; H2 reports the database error and the menu remains available.

## Troubleshooting

- **`mvn` is not recognized:** install Apache Maven, add its `bin` directory to PATH, and open a new terminal.
- **`java` or `javac` is not recognized:** install a JDK 17 or newer and configure PATH.
- **No suitable driver:** run `mvn package` from the project folder while connected to the internet so Maven can download H2.
- **Database file locked:** close any other running copy of the application and try again.
- **Want to start over:** exit the application and delete `data/student_management.mv.db`. This permanently deletes the saved student records.

## Prepare for GitHub

The `.gitignore` file excludes compiled Java classes, Maven build output, local VS Code settings, and H2 database files. Keep the shared `.vscode/extensions.json` file. Do not publish database files, passwords, or other private information.

1. Install Git for Windows and reopen PowerShell. Verify the installation:

   ```powershell
   git --version
   ```

2. On GitHub, sign in and select **New repository**. Enter a repository name such as `student-management-system`, choose **Public**, and leave **Add a README file**, **Add .gitignore**, and **Choose a license** unchecked. Select **Create repository**. Keep the new repository page open for the URL.
3. In PowerShell, go to the project folder, initialize Git, and choose `main` as the branch:

   ```powershell
   Set-Location 'C:\task2'
   git init
   git branch -M main
   ```

4. Stage only the project files (not `target` or `data`), then inspect exactly what would be published:

   ```powershell
   git add README.md pom.xml .gitignore .vscode/extensions.json sql src
   git status --short
   git diff --cached --check
   git diff --cached
   ```

   Confirm that the staged files include the source code and documentation, and do not include database files, compiled `.class` files, build output, passwords, or other private information. If anything unexpected appears, do not commit or push; unstage it with `git restore --staged <path>` and review again.
5. Only after approving the staged changes, create the first commit:

   ```powershell
   git commit -m "Add H2 student management application"
   ```

6. Copy the HTTPS repository URL shown on the GitHub page and add it as the `origin` remote. Replace the example URL with your own:

   ```powershell
   git remote add origin https://github.com/YOUR-USERNAME/student-management-system.git
   git remote -v
   ```

7. Publish the branch when you are ready:

   ```powershell
   git push -u origin main
   ```

   GitHub may open a browser window for authentication. After the push completes, refresh the GitHub repository page to see your files.
