<%
	// Copyright (c) 2002-2003, Erik C. Thauvin
	// All rights reserved.

	String key = request.getParameter("key");
	
	if ((key != null)&& (key.trim().length() > 0))
	{
		pageContext.setAttribute("google_key", key, PageContext.APPLICATION_SCOPE);
%>
	<jsp:forward page="test/servlet/RunTests?uri=/test/google/index.jsp"/>
<%
	}
	else
	{
%>
	<%@include file="index.html"%>
<%
	}
%>
