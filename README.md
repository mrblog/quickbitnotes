# Quick Bit Notes #
Private person to person asynchronous messaging through images.

<b>Quick Bit Notes</b> is an experiment in an alternative to Email for exchanging brief messages in which the text of your message is stored in human readable form as images rather than in machine readable text.  Message contents are never transferred through Internet mail servers or gateways.

Leave a Quick Bit Note for a friend. That friend (and only that friend) can then log in and view the Quick Bit Note. 

## App Engine Installation / Configuration ##

Of course you will need to change `webinf/appengine-web.xml`
to the name of your app engine appsport domain.

There are also a couple of init-param values you will need to
change in `webinf/web.xml`

Change the value for `'thisDomain'` to the name of your appspot
host/domain or the domain where the app is going to run if 
using your own Google Apps domain.

Change the value for `'adminEmail'` to the email address you want to 
use for feedback from users (users must be logged in to send 
feedback).


## Using Ant ##

Run `ant build` to compile and create the `/war` directory. Use
`ant runserver` to run the dev server.  Use `ant update` to update
to the GAE servers.
