# akka-http-slick-contact-management
A Akka-Http template with contact management example. 

About the Project
=================

This project has two end points:-

*POST /contacts*

*GET /contacts?name=...*

Using this application, you will be able to insert contect in databse and return relevant records when queried by name. If there are duplicate records,which are repeated but with different phone numbers will be merged, and when queried, contacts will return phone numbers as a set or sequence.

The insert request is always a JSON request containing a contact, which should be like following schema.

*{*

*"name": str,*

*"lastName": str,*

*"phone": str*

*}*

Response of a search will be like the following structure:

*[*

*{*

*name: "Foo",*

*lastName: "Bar",*

*phones: ["+123", "+456"]*

*},*

*...*

*]*


Technology Stack
================

Scala,
Akka-HTTP
Slick
MySQL

Steps to execute the application
================================

`sbt run`

Steps to execute test-cases
===========================

`sbt clean coverage test`

`sbt coverageReport`





