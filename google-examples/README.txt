Google Tag Library Examples README
==================================


Overview
========

This README file describes the Google Tag Library Examples web application.

This web application contains a collection of examples demonstrating the
usage of the Google JSP Tag Library. For more information about using the
custom tag library in your own web applications, point your browser to:

	http://google-taglib.sourceforge.net/

See the "LICENSE.txt" file for licensing details.

The Google Tag Library uses the Google Web APIs, but is not associated with
or sponsored by Google, Inc.


Google Web APIs
===============

The Google Web APIs Java library is required in order to use the Google Tag
Library. The library's JAR file (googleapi.jar) should be placed into the
application's WEB-INF/lib directory.

	http://www.google.com/apis/download.html

You must also register with Google (free) in order to obtain a license key.
The license key is required to access Google's Web Services.

	https://www.google.com/accounts/NewAccount
	

Google License Key
==================

The license key can be specified as a context parameter in the web
application deployment descriptor (web.xml) as follows:

	<context-param>
		<param-name>google_key</param-name>
		<param-value>000000000000000000000000</param-value>
	</context-param>

The license key can also be temporarily specified from the index page of
the web application.


Examples
========

The following examples are included: 

	- Perform a simple Google search 
	- Perform an advanced Google search 
	- View a page cached by Google 
	- Perform a Google spelling suggestion 


===========================================================================
$Id$
