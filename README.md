# Sanus
A simple patients management system using Swing.

## Installation
  - Import the repo as a project in Eclipse.
  - Go to `main.MainClass` and run.
 
If you have any issues compiling, clean the project and make sure `Project -> Build Automatically` is ticked.

## Important Notes:
- User name: marco // Password: pass
- The file import.csv has the right template for the import.
- Tested on Mac OS X and Linux (Ubuntu).

## Content:
- Source and compiled code: see dir PatientRegistry (this is the Eclipse workspace).
- JavaDoc: see PatientRegistry/Sanus/doc
- Video: https://youtu.be/bodC7oZ2Cnk

## Features Implemented
- All exceptions handled.
- JavaDoc generated in doc folder.
- All basic features implemented.
- Optional features implemented.
- Used a database SQLite.
- References included as comment.

### Detailed list of features
- GUI application using Swing.
- Login frame that uses the table users in the SQLite database.
- Name of the practitioner comes from the relevant users table.
- Three panels that are dynamically switched and added to the HomePage frame. The elements that are always there are added to the main panel the others are added to specific panels. A variable panel is the current chosen panel.
- Screen of patients:
  * See all patients in the JTable.
  * Double click on a patient to access full information in a card.
  * Edit all elements in the card.
  * When editing pictures, only picture formats are allowed.
  * Delete patient.
  * Access extra photos.
  * Add a patient with minimal information (the extra photos can be added later).
  * Medical condition can be changed according to those available. A link can be clicked to access a web page with specific information.
  * After every edit the panels are updated. Also the JTable in the HomePage frame is updated.
  * Search button allows to search for all fields in the patients table. This is achieved through a UNION query. The JTable will update with the results.
- Screen with appointments:
  * See all appointments in the JTable.
  * Double click on an appointment to access full information in a card.
  * Edit all elements in the card.
  * When editing pictures, only picture formats are allowed.
  * Delete appointment.
  * Add an appointment. You can only select a patient that exists from a drop down menu.
  * After every edit the panels are updated. Also the JTable in the HomePage frame is updated.
  * Search button allows to search for all fields in the patients table. This is achieved through a UNION query. The JTable will update with the results.
- Import and Export pane
  * Please use the file import.csv which has the right template for the import. One dialog box will appear confirming you added every patient. You can only import *.csv files.
  * For the export, select the destination folder (do not double click). It will create a results.csv file.
- Logout button at the top right.
