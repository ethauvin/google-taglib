Google RSS Tag Library README
=============================


Overview
========

This README file describes the Google RSS Tag Library distribution.

The Google RSS Tag Library is an extension to, and thus requires,
the Google Tag Library, which can be found at:

	<http://google-taglib.sourceforge.net/>


See the "LICENSE.txt" file for licensing details.

The Google RSS Tag Library uses the Google Web APIs, but is not associated
with or sponsored by Google, Inc.


Installation
============

The Google RSS Tag Library requires a JSP (1.1 or higher) container, such
as Tomcat.

To use the Google RSS Tag Library, simply copy the library's JAR file
(google-rss.jar) to your application's WEB-INF/lib directory. The Tag Library
Descriptor (google-rss.tld) should be placed in your application's WEB-INF
directory.

You should also modify your web application deployment descriptor (web.xml)
file to map the Google RSS Tag Library TLD URI to its location:

	<taglib> 
		<taglib-uri>/google-rss-taglib</taglib-uri>
		<taglib-location>/WEB-INF/google-rss.tld</taglib-location>
	</taglib>


TagLib Directive
================

Using the Google RSS Tag Library is easy; you simply need to import it into
your JSP pages using the taglib directive. For instance, you would include
the following line at the top of your JSP page:

	<%@taglib uri="/google-rss-taglib" prefix="rss"%>
	
	
SearchResults Tag
=================

The <rss:searchResults/> tag is used to display the search results returned
by a Google search (performed using the Google Tag Library) in RSS format.

For example:

	<%@ page contentType="text/xml" %>
	<%@ taglib uri="/google-taglib" prefix="google" %>
	<%@ taglib uri="/google-rss-taglib" prefix="rss" %>
	<google:search>my search query</google:search>
	<rss:searchResults>
	
Please refer to the Google Tag Library documentation for detailed information
on performing searches.


===========================================================================
$Id$