/*
 *  @(#)SearchResultSupport.java
 *
 *  Copyright (c) 2002-2003, Erik C. Thauvin (erik@thauvin.net)
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 *  Neither the name of the author nor the names of its contributors may be
 *  used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  $Id$
 *
 */
package net.thauvin.google.taglibs;

import net.thauvin.google.GoogleSearchBean;
import net.thauvin.google.TagUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import javax.servlet.jsp.JspException;


/**
 * Implements methods used to retrieve Google Search Result properties.
 *
 * @author Erik C. Thauvin
 * @created April 26, 2002
 * @version $Revision$
 * @since 1.0
 */
public abstract class SearchResultSupport extends StyleSupport
{
	/**
	 * The Google search bean.
	 */
	protected GoogleSearchBean bean = null;

	/**
	 * doEndTag method.
	 *
	 * @return EVAL_PAGE
	 * @exception JspException
	 */
	public int doEndTag()
				 throws JspException
	{
		try
		{
			// Is the result valid?
			if ((bean != null) && (bean.isValidResult()))
			{
				// Output the property
				pageContext.getOut().write(getResultProperty());
			}
		}
		catch (Exception e)
		{
			throw TagUtility.outputError(getTagName(), e);
		}

		// Reset the values
		reset();

		return EVAL_PAGE;
	}

	/**
	 * doStartTag method.
	 *
	 * @return EVAL_BODY_TAG or SKIP_BODY.
	 * @exception JspException
	 */
	public int doStartTag()
				   throws JspException
	{
		// Get the Google bean
		bean = TagUtility.getGoogleSearchBean(pageContext);

		// Is the result valid?
		if ((bean != null) && (bean.isValidResult()))
		{
			return EVAL_BODY_TAG;
		}

		return SKIP_BODY;
	}

	/**
	 * Release method.
	 */
	public void release()
	{
		super.release();

		// Reset the bean
		bean = null;

		// Reset the values
		reset();
	}

	/**
	 * Returns the result of the build action and must be implemented by classes
	 * which extend SearchResultSupport.
	 * <p>
	 * For example:
	 * <p>
	 * <blockquote>return "Some Value";</blockquote>
	 *
	 * @return The property name.
	 */
	protected abstract String getPropertyName();

	/**
	 * Returns the value of specified search result property.
	 *
	 * @return The property value.
	 */
	protected String getResultProperty()
	{
		String name = getPropertyName();
		String value = bean.getResultProperty(name);

		if ((TagUtility.isValidString(value))
				&& (name.equals(GoogleSearchBean.NEXT_KEYWORD)
				|| name.equals(GoogleSearchBean.PREVIOUS_KEYWORD)))
		{
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			String url =
				HttpUtils.getRequestURL(request).toString() + '?'
				+ TagUtility.START_PARAM + '=' + value + '&'
				+ TagUtility.requestParamsToUrl(request, TagUtility.START_PARAM);

			return (TagUtility.buildRefLink(url,
											TagUtility.getTagBody(bodyContent),
											target, style, css));
		}
		else
		{
			return value;
		}
	}

	/**
	 * Returns the name of the tag extending SearchResultSupport.
	 * <p>
	 * For example:
	 * <p>
	 * <blockquote> void getErrorString() { return "MyTag"; }</blockquote>
	 *
	 * @return The tag name.
	 */
	protected abstract String getTagName();

	/**
	 * Reset the values.
	 */
	protected void reset()
	{
		super.reset();
	}
}
