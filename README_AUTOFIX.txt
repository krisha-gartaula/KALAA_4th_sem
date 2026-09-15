KALAA - Automated first-pass fixes and additions
================================================

What I did (automated pass)
- Converted all .html files to .jsp (kept original .html as .bak next to each file).
- Added basic JSP page directives at the top of converted files.
- Looked for web.xml and set welcome-file to index.jsp where possible.
- Added skeleton servlets (src/main/java/com/example/kalaa/servlets):
  - UserAdminServlet.java
  - CategoryAdminServlet.java
  - StoryAdminServlet.java
  These handle POST requests and insert into MySQL database 'kalaa'. Update DB credentials before running.
- If admin JSP pages (user.jsp, categories.jsp, story.jsp) had no form, a simple form was injected that posts to the above servlets.
- Created SQL file db/kalaa_schema.sql with CREATE TABLE statements and sample data.
- Packaged corrected project into corrected_kalaa.zip at /mnt/data/corrected_kalaa.zip

Important next steps (you must do)
- Update database connection details in the servlet Java files (DB_URL, DB_USER, DB_PASS) or move to a safer config.
- Hash user passwords before storing (the sample admin password in SQL is plain text 'admin' — only for testing).
- Add authentication checks for admin pages (the servlets currently accept POSTs; add session checks to restrict to admins).
- If your project uses a different package structure or build system, you may need to adjust the servlet package or add dependencies to pom.xml:
    - Add MySQL connector dependency:
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
      </dependency>
- Build with Maven: mvn clean package
- Deploy to Tomcat 10 / Jakarta-compatible container.

Files changed or added:
- Converted .html -> .jsp (originals renamed with .bak)
- Added servlets under src/main/java/com/example/kalaa/servlets/
- db/kalaa_schema.sql
- README (this file)