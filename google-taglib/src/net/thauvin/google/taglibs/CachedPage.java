/*
 *  @(#)CachedPage.java
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


/**
 * A custom tag used to access and retrieve pages cached on Google.
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public class CachedPage extends QuerySupport
{
	/**
	 * doEndTag method.
	 *
	 * @return EVAL_PAGE
	 * @exception JspException
	 */
	public int doEndTag()
				 throws JspException
	{
		final String query = getQuery();

		if (TagUtility.isValidString(query, true))
		{
			try
			{
				final GoogleSearchBean bean = new GoogleSearchBean(getKey());

				bean.setProxyServer(pageContext.getServletContext()
											   .getInitParameter(TagUtility.GOOGLE_PROXY_HOST),
									pageContext.getServletContext()
											   .getInitParameter(TagUtility.GOOGLE_PROXY_PORT),
									pageContext.getServletContext()
											   .getInitParameter(TagUtility.GOOGLE_PROXY_USERNAME),
									pageContext.getServletContext()
											   .getInitParameter(TagUtility.GOOGLE_PROXY_PASSWORD));

				// Output the body
				pageContext.getOut().write(bean.getCachedPage(query));
			}
			catch (Exception e)
			{
				throw TagUtility.outputError("cachedPage", e);
			}
		}

		// Reset the values
		reset();

		return EVAL_PAGE;
	}

	/**
	 * Release method.
	 */
	public void release()
	{
		super.release();

		// Reset the values
		reset();
	}

	/**
	 * Reset the values.
	 */
	protected void reset()
	{
		super.reset();
	}
}
