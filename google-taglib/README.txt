Google Tag Library README
=========================


Overview
========

This README file describes the Google Tag Library distribution.

With the Google JSP Tag Library, developers and designers can easily
incorporate Google queries, search results, cached pages and spelling
suggestions into any web site or application.

For more information about using the custom tag library in your own web
applications, point your browser to:

	http://google-taglib.sourceforge.net/

See the "LICENSE.txt" file for licensing details.

The Google Tag Library uses the Google Web APIs, but is not associated with
or sponsored by Google, Inc.

Installation
============

The Google Tag Library requires a JSP (1.1 or higher) container, such as
Tomcat.

To use the Google Tag Library, simply copy the library's JAR file
(google.jar) to your application's WEB-INF/lib directory. The Tag Library
Descriptor (google.tld) should be placed in your application's WEB-INF
directory.

You should also modify your web application deployment descriptor (web.xml)
file to map the Google Tag Library TLD URI to its location:

	<taglib> 
		<taglib-uri>/google-taglib</taglib-uri>
		<taglib-location>/WEB-INF/google.tld</taglib-location>
	</taglib>


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

The license key can also be specified using the key search form parameter,
key tag attribute or the google_key application scope attribute.


TagLib Directive
================

Using the Google Tag Library is easy; you simply need to import it into your
JSP pages using the taglib directive. For instance, you would include the
following line at the top of your JSP page:

	<%@taglib uri="/google-taglib" prefix="google"%>


===========================================================================
$Id$