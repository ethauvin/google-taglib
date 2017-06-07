/*
 *  @(#)QuerySupport.java
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

import net.thauvin.google.TagUtility;


/**
 * Implements supports for specifying the Google query string.
 * <p>
 * The query can be specified using one the following:
 * <ul>
 * <li>The {@link net.thauvin.google.TagUtility#QUERY_PARAM query} request parameter.</li>
 * <li>Directly within the body of the tag.</li>
 * </ul>
 *
 * @author Erik C. Thauvin
 * @created April 29, 2002
 * @version $Revision$
 * @since 1.0
 */
public abstract class QuerySupport extends KeySupport
{
	/**
	 * Release method.
	 */
	public void release()
	{
		super.release();
	}

	/**
	 * Returns the specified query.
	 *
	 * @return The query value.
	 */
	protected final String getQuery()
	{
		String query =
			TagUtility.getParameter(pageContext.getRequest(),
									TagUtility.QUERY_PARAM);

		if (TagUtility.isValidString(query, true))
		{
			return query;
		}

		query = TagUtility.getTagBody(bodyContent, true);

		if (TagUtility.isValidString(query, true))
		{
			return query;
		}

		return "";
	}

	/**
	 * Reset the values.
	 */
	protected void reset()
	{
		super.reset();
	}
}
