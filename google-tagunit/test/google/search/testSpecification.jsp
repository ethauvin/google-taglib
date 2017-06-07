<%@ taglib uri="http://www.tagunit.org/tagunit/core" prefix="tagunit" %>

<tagunit:assertBodyContent name="JSP"/>

<tagunit:assertAttribute name="key" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="start" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="maxResults" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="filter" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="restrict" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="safeSearch" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="lr" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="cache" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="site" required="false" rtexprvalue="true"/>
<tagunit:assertAttribute name="type" required="false" rtexprvalue="true"/>