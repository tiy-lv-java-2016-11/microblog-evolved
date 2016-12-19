# Microblog Evolved

## Description

Time to add more serious functionality to our little microblog and get a bit more practice with past conepts.

## Requirements

* If the messages are simple strings change them to objects.
* Add an ID field to the class.
* Add a `created_at` field to the class that is set in the constructor.
* Create a static method on the Message class that will take a list of Message objects and loop through them to get the next id available. (ie take the highest current `id` out of all the messages and return the next number).  This will allow you to add new messages with unique ids. 

* Add a password field to the login form. If the user doesn't exist, create a new user and store the password in the `microblogevolved.User` object. If the user does exists, check the password and, if it's wrong, don't let them log in (you can decide the details for yourself).
* Add multi-user support by storing your users in a `HashMap<String, microblogevolved.User>` and putting your `ArrayList<Message>` inside the `microblogevolved.User` object.
* In the `/create-user` route, save the username into the session. In the `/` route, get the username out of the session and subsequently get your `microblogevolved.User` object.
* Show a logout button when the user is logged in. It should invalidate the session and refresh the page so you can log in again with a new user.

* Create a page called `message_detail.html` that will display the details of the message including date, author, and the text of the message.
* Add a `/message` route that takes in the message id as a query parameter and displays the message.
* Add a form in `messages_detail.html` which lets you delete the current message.  Redirect them back to the list of messages. 
* Add a form in `messages_detail.html` which lets you edit the message by simply entering the new text into a text input. 
* Put a link back to your message list page for navigation.
* Link all messages on the message list page to their details page.

## Hard Mode
* Make the microblog persist data by saving the messages a postgres database.
    * The 2 tables will be microblogevolved.User and Message
    * Make sure to create the database and tables
    * Message table should have a foreign key to microblogevolved.User
    * Make the database generate the `id` and the `created_at` date
* Display the date in the format "January 1, 1990"
* Make another page `user_messages.html` which will take a `user_id` query parameter and display all of the user's messages
* Link all instances of the author's name throughout your site to this page

## Resources
*[Github Repo](https://github.com/tiy-lv-java-2016-11/microblog-evolved)
*[Spark Session Documentation](http://sparkjava.com/documentation.html#sessions)
