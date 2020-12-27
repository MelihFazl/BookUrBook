----CS102 PROJECT GROUP NUMBER----
g1A

----PROJECT TITLE---
BookUrBook

----DESCRIPTION----
BookUrBook is an Android app whose users can sell and buy second-hand school materials. Those users can only register by @.edu.tr mails, which enhances
the safety of the app. In this app, users can communicate with other users via chat.

----CURRENT STATUS AND WHAT WORKS?----
Group/project Selection: Completed.
Requirements: Completed.
UI Design: Completed.
Detailed Design: On progress.
Implementation: On progress.

Regarding detail design stage, a big partition of it is ready, yet we update it while implementing the project if one of us comes with a new idea.

Regarding Implementation,
Login, Register, and verification systems are done. Furthermore, Main menu are completed.
Moreover, Core classes and interfaces that are shown in the detailed design report are done.
Post List, My Posts, Post, Edit Post, Add Post, and Blocklist are the screens whose UIs are designed and ready to be connected with database and each other.
A big partition of filtering and sorting systems are done. They work for local variable but they have not been tested with the database connection yet.
For the Settings screen, it can change user's avatar for now and a few things left to be implemented.
For chat, database connection is almost ready and UI will be added later on.
Notification system is going to be tried to be added.
Remaining screens will be created.


----GROUP MEMBERS and CONTRIBUTIONS----

---Ferhat Korkmaz---
-> Mostly, database management by using FireBase and Connecting MVC Models with the gotten information from Database for activities (with Melih).
-> Login-Register-Logout systems on which only the users with @.edu.tr mails can register with a verification code that are sent to their email.
-> Setting and displaying the user avatar.
-> Designing main menu, login , verification , register, and welcome screens (with Melih).
-> Implementing the JavaMailAPI into the project in order to send a mail that contains the verification code.
-> Making wishlist system in database and connecting it to the app properly.
-> Connecting the admin panel with database.
-> Developing a proper report system for the posts and chats (with Kerem)
-> Block system (With Melih)
-> Eliminating banned and blocked (by the current user) users' posts from the postlist and wishlist screens.
-> While registering, it checks more. (Alphanumerical email, password, and username)
-> Feedback system by using JavaMailAPI.
-> Resetting password for the users.
-> Fixing bugs with my group mates.

----Melih Fazıl Keskin----
-> Planning of database structure design with Ferhat. We created our Database in Firebase.
-> After Register, adding user information to database (with Ferhat)
-> After Login or Register, retrieving user information from database and generating User.(with Ferhat)
-> Planning of the chat structure in database
-> Creating Chat and Message model classes
-> In ChatActivity, retrieving the current user's chat data(not including all of the messages yet)
and updating whenever a change occurs in database.
-> Designing the application logo that appears on the initial screens.


--- Miray Ayerdem---
-> Adding and coding some methods to User and Post Classes
-> Creating Wishlist Class as a model class and designing it again Wishlist screen according to feedback
-> Creating Wishlist screen layout in Android Studio using xml, its adapter and its activity class using Java
-> Creating My Posts screen layout in Android Studio using xml, its adapter and its activity class using Java
-> Creating My Blocklist screen layout and its pop-ups in Android Studio using xml, its adapter and its activity class using Java
-> Designing Settings screen layout in Android Studio using xml


--- Kaan Tek---
-> Mostly working on user interface and adapting the Java classes to Android environment.
-> Designed Post List screen in Android Studio and built an adapter for the recycler view.
-> Designed Filter screen so that the user can filter de Post List results.
-> Designing My Chats screen in Android Studio and building an adapter for the recycler view.
-> Designing Chat screen in Android Studio and building an adapter for the recycler view.
-> Making the connection between the database and the user interface of some classes.
-> Creating dialog pop up for user interraction.


---Kerem Şahin---
->Creation of Model Classes and implementation of their methods.
->Post Details Screen layout in Android Studio using xml, design and the creation of activi-ty classes.
->Create Post Screen layout in Android Studio using xml, design and the creation of activity class.
->Edit Post layout in Android Studio using xml, design and the creation of activity classes.
->Creation of the report post pop up.
->Additions that provide the user-interface interactions (as pop up screens) for the previously mentioned screens. 
->Creation of the notification system when there is change on the wishlist and when there is a message with Melih and Kaan.
->Creation of the send feedback dialog.
->Revisions on the report system with Ferhat.
->Creation of different toolbars for different screens and the connection of the toolbar buttons with the activity classes.