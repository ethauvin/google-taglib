Google Tag Library README (BUILD)
=================================


Overview
========

This README file describes the Google Tag Library source distribution.

With the Google JSP Tag Library, developers and designers can easily
incorporate Google queries, search results, cached pages and spelling
suggestions into any web site or application.

For more information about using the custom tag library in your own web
applications, point your browser to:

	http://google-taglib.sourceforge.net/

See the "LICENSE.txt" file for licensing details.

The Google Tag Library uses the Google Web APIs, but is not associated with
or sponsored by Google, Inc.

Google Web APIs
===============

The Google Web APIs Java library is required in order to build the Google
Tag Library. The library's JAR file (googleapi.jar) should be placed into
the lib directory.

	http://www.google.com/apis/download.html
	

Building the Source
===================

The Google Tag Library is built using Ant. 

	http://ant.apache.org/
	

To build the JAR, to do the following:

	ant
	
To build the TLDs, do the following:

	ant tlds
	
To build the release archives, do the following:

	ant release
	
To build the Examples WAR, do the following:

	ant examples
	
To list all available build targets, do the following:

	ant -projecthelp


===========================================================================
$Id$