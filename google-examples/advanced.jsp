<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/google-taglib" prefix="google" %>
<google:search/>
<html>
<head>
	<title>Google Search: <google:searchQuery/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<style type="text/css">
	<!--
		body,td,div,.p,a{font-family:arial,sans-serif}
		div,td{color:#000}
		.f,.fl:link{color:#6f6f6f}
		a:link,.w,a.w:link,.w a:link{color:#00c}
		a:visited,.fl:visited{color:#551a8b}
		a:active,.fl:active{color:#f00}
		.b{font-size: 12pt; color:#00c; font-weight:bold}
	//-->
	</style>
</head>
<!--
	Copyright (c) 2002-2003, Erik C. Thauvin
	All rights reserved.
-->
<body bgcolor="#FFFFFF" text="#000000">
<table width="100%" border="0" cellpadding="2" cellspacing="0">
  <tr>
	<td bgcolor="#3366cc"><font size="-1" color="#ffffff">Searched the web for <b><google:searchQuery/></b>. &nbsp; </font></td>
	<td bgcolor="#3366cc" align="right"><font size="-1" color="#ffffff">Results <b><google:startIndex/></b> - <b><google:endIndex/></b> of about <b><google:estimatedTotal/></b>.   Search took <b><google:searchTime/></b> seconds.</font>
</table>
<google:searchResult>
<p><google:element name="title-url" target="_blank"/><br>
  <font size="-1"><google:element name="snippet"/></font>
<br>
  <span class="f"><font size="-1">Description:</font></span> <font size="-1"><google:element name="summary"/></font><br>
  <span class="f"><font size="-1">Category:</font></span> <font size="-1"><a class="fl" href="http://directory.google.com/<google:element name="directoryCategoryName"/>" target="_blank"><google:element name="directoryCategoryName"/></a></font><br>
  <font size="-1" color="#008000"><google:element name="staticQuery"/> - <a class="fl" href="cache.jsp?q=<google:element name="cachedQuery"/>" target="_blank">Cached</a> - <google:element name="cachedSize"/> - <a class="fl" href="advanced.jsp?q=<google:element name="relatedQuery"/>">Similar Pages</a></font>
</google:searchResult>
<p>
<table width="100%">
	<tr>
		<td align="left">
			<google:previous css="b">Previous</google:previous>
			&nbsp;
		</td>
		<td align="right">
			&nbsp;
			<google:next css="b">Next</google:next>
		</td>
	</tr>
</table>
</body>
</html>
