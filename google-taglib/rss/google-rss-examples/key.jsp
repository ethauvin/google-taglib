<%
	// Copyright (c) 2002-2004, Erik C. Thauvin
	// All rights reserved.

	String key = request.getParameter("key");
	
	if ((key != null)&& (key.trim().length() > 0))
	{
		pageContext.setAttribute("google_key", key, PageContext.APPLICATION_SCOPE);
%>
	<%@include file="search.html"%>
<%
	}
	else
	{
%>
	<%@include file="key.html"%>
<%
	}
%>

