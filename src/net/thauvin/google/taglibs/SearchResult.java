/*
 *  @(#)SearchResult.java
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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


/**
 * A custom tag used to parse and loop through Google search results.
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public class SearchResult extends BodyTagSupport
{
	private GoogleSearchBean bean = null;
	private StringBuffer output = null;
	private int loopCount = 0;

	/**
	 * doAfterBody method.
	 *
	 * @return EVAL_BODY_TAG or SKIP_BODY.
	 * @exception JspTagException
	 */
	public int doAfterBody()
					throws JspTagException
	{
		// Get the body content
		final String body = bodyContent.getString();

		// Append the body to the ouput
		output.append(body);

		try
		{
			// Clear the body content
			bodyContent.clear();
		}
		catch (java.io.IOException e)
		{
			throw TagUtility.nestedException("An error occurred while clearing the content of the 'searchResult' tag.",
											 e);
		}

		// Increment the loop count, if there are more elements
		if (++loopCount < bean.getResultElementsCount())
		{
			return EVAL_BODY_TAG;
		}

		return SKIP_BODY;
	}

	/**
	 * doEndTag method.
	 *
	 * @return EVAL_PAGE.
	 * @exception JspTagException
	 */
	public int doEndTag()
				 throws JspTagException
	{
		// Is there anything to ouput?
		if (output.length() > 0)
		{
			try
			{
				// Write the output into the tag body
				bodyContent.getEnclosingWriter().write(output.toString());
			}
			catch (java.io.IOException e)
			{
				throw TagUtility.outputError("searchResult", e);
			}
		}

		// Reset the values
		reset();

		return EVAL_PAGE;
	}

	/**
	 * doStartTag method.
	 *
	 * @return EVAL_BODY_TAG.
	 * @exception JspException
	 */
	public int doStartTag()
				   throws JspException
	{
		output = new StringBuffer();

		// Reset the loopCount
		loopCount = 0;

		// Get the Google bean
		bean = TagUtility.getGoogleSearchBean(pageContext);

		// Are there any elements?
		if ((bean != null) && (bean.getResultElementsCount() > 0))
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

		// Reset the output
		output = null;

		// Reset the values
		reset();
	}

	/**
	 * Returns the current search result element specified property.
	 *
	 * @param name The property name.
	 * @return The property value.
	 */
	protected String getElementProperty(String name)
	{
		if (bean != null)
		{
			return bean.getResultElementProperty(loopCount, name);
		}

		return "";
	}

	/**
	 * Reset the values.
	 */
	protected void reset()
	{
		// Reset the loop count value
		loopCount = 0;
	}
}
