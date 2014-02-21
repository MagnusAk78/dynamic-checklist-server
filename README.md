dynamic-checklist-server
========================

Server part for an application that handles a dynamic (in time) checklist.


This is a non active server in the form of a Couch App. Installation requires a
CouchDB NoSQL database. There are cloud based CouchDB servers available such as
Cloudant (http://www.cloudant.com).

Packaging has been done with kanso (http://kan.so). As of now kanso is not 
supported to run on Windows.

==========================================================================

Get it running...



1. Run kanso install to get required dependecies.

  $ kanso install
  
2. Push the app to CouchDB whatever CouchDB instance you are using.

  $ kanso push http://username:password:{couchdb-location}/{database_name}
  
3. Follow the link displayed after successful push.

==========================================================================