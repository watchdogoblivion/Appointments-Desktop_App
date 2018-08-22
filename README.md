This is an appointment scheduling application. It relies on the MYSQL database and maven. Access to database may go down in the future depending on hosts terms. If that occurs, only the login page will be accessible until new host is assigned in Login.java file.

To login, use these credentials,
Username: Samuel Johnson,
Password: 123

Feel free to add and remove customers and appointments. If username or password is modified, I can reset and modify any changes since only I have direct access to the mysql database. If they are changed, please revert them for simplicity.

Reports are genrated in Appointments/target folder

Code does need refactoring, there are areas that do not use DRY principle.

This project produced an application that allows the user to schedule appointments for a company. The user can add, view, modify, and delete customers information as well as appointments. The customers can be placed into checked-in, absent, or no-show categories before having their appointment wiped from the database. There is a calendar view that makes it easy to see which dates have appointments and how many appointments on those dates. Reports can be generated and exported to either desktop or external source. Locales are accounted for and the language can be changed from within the profile menu which is also reflected on the reports.
