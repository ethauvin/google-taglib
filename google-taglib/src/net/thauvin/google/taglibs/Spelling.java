/*
 *  @(#)Spelling.java
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

import javax.servlet.jsp.JspException;


/**
 * A custom tag used to access and retrieve Google spelling suggestions.
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public class Spelling extends QuerySupport
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

				String result = bean.getSpellingSuggestion(query);

				if (!TagUtility.isValidString(result, true))
				{
					result = query;
				}

				// Output the spelling suggestion
				pageContext.getOut().write(result);
			}
			catch (Exception e)
			{
				throw TagUtility.outputError("Spelling", e);
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
