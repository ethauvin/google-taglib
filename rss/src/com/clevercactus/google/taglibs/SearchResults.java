/*
 * @(#)SearchResults.java
 *
 *  Copyright (c) 2003-2004, Erik C. Thauvin (erik@thauvin.net)
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
package com.clevercactus.google.taglibs;

import com.clevercactus.google.GoogleResultsWrapper;

import net.thauvin.google.GoogleSearchBean;
import net.thauvin.google.TagUtility;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * A custom tag used to display Google search results in RSS format.
 *
 * @author Erik C. Thauvin
 * @version $Revision$, $Date$
 *
 * @created Oct 30, 2003
 * @since 1.0
 */
public class SearchResults extends BodyTagSupport
{
	/**
	 * The Google search bean.
	 */
	protected GoogleSearchBean bean = null;

	/**
	 * doEndTag method.
	 *
	 * @return EVAL_PAGE
	 *
	 * @throws JspException
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			// Is the result valid?
			if (bean != null)
			{
				// Output the feed
				GoogleResultsWrapper.outputResults(pageContext.getOut(),
												   bean.getResultElements(),
												   bean.getResultProperty("searchKeywords"),
												   "rss");
			}
		}
		catch (Exception e)
		{
			throw TagUtility.outputError("rss", e);
		}

		// Reset the values
		reset();

		return EVAL_PAGE;
	}

	/**
	 * doStartTag method.
	 *
	 * @return SKIP_BODY.
	 *
	 * @throws JspException
	 */
	public int doStartTag() throws JspException
	{
		// Get the Google bean
		bean = TagUtility.getGoogleSearchBean(pageContext);

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
	 * Reset the values.
	 */
	protected void reset()
	{
		// super.reset();
	}
}
