Google RSS Tag Library README (BUILD)
=====================================


Overview
========

This README file describes the Google RSS Tag Library source distribution.

The Google RSS Tag Library is an extension to, and thus requires, the
Google Tag Library, which can be found at:

	<http://google-taglib.sourceforge.net/>


See the "LICENSE.txt" file for licensing details.

The Google RSS Tag Library uses the Google Web APIs, but is not associated
with or sponsored by Google, Inc.

Google Web APIs
===============

The Google Web APIs Java library is required in order to build the Google
RSS Tag Library. The library's JAR file (googleapi.jar) should be placed
into the lib directory.

	http://www.google.com/apis/download.html
	

Building the Source
===================

The Google RSS Tag Library is intended to be build directly within the
Google Tag Library source tree:

	/google-taglib/rss/


The Google RSS Tag Library is built using Ant. 

	http://ant.apache.org/
	

To build the JAR, to do the following:

	ant
	
To build the TLD, do the following:

	ant tld
	
To build the release archive, do the following:

	ant release
	
To build the Examples WAR, do the following:

	ant examples
	
To list all available build targets, do the following:

	ant -projecthelp


===========================================================================
$Id$