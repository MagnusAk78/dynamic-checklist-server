<h1>dynamic checklist server</h1>
<p>
Server part for an application that handles a checklist. The point is that the the frequency of required checks can be utilised to optimise the list.
</p>

<p>
This is a non active server in the form of a Couch App. Installation requires a CouchDB NoSQL database (http://couchdb.apache.org). There are cloud based CouchDB servers available such as Cloudant (http://www.cloudant.com).
</p>

<p>
Packaging has been done with kanso (http://kan.so). As of now kanso is not supported to run on Windows.
</p>
<hr>

<h2>Get it running</h2>

<ol>
<li>Get an instance of CouchDB either by installig it or using a cloud service.</li>

<li>Create a new database on the CouchDB instance.</li>

<li>Install Kanso.</li>

<li>
    Upload the server to the CouchDB instance.<br><br>

    Run kanso install to get required dependecies.<br>
        <code>$ kanso install</code><br><br>

    Push the app to CouchDB whatever CouchDB instance you are using.<br>
        <code>$ kanso push http://username:password:{couchdb-location}/{database_name}</code><br><br>

    Follow the link displayed after successful push.
</li>
    
</ol>

==========================================================================
