/*
 *  @(#)KeySupport.java
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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


/**
 * Implements support for specifying the key used for authorization in the
 * Google API.
 * <p>
 * The key can be specified using one the following:
 * <ul>
 * <li>The {@link #key key} attribute.</li>
 * <li>The {@link net.thauvin.google.TagUtility#KEY_PARAM key} request parameter.</li>
 * <li>The {@link net.thauvin.google.TagUtility#KEY_CONTEXT_PARAM key} context
 * parameter.</li>
 * <li>The {@link net.thauvin.google.TagUtility#KEY_CONTEXT_PARAM key} application scope
 * attribute.</li>
 * </ul>
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public abstract class KeySupport extends BodyTagSupport
{
	/**
	 * The key attribute.
	 */
	protected String key = null;

	/**
	 * Sets the key attribute.
	 *
	 * @param key The new attribute value.
	 * @see #getKey
	 */
	public final void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * Returns the key attribute.
	 *
	 * @return The attribute value.
	 * @see #setKey(String)
	 */
	public String getKey()
	{
		if (TagUtility.isValidString(key, true))
		{
			return key;
		}
		else
		{
			String keyParam =
				TagUtility.getParameter(pageContext.getRequest(),
										TagUtility.KEY_PARAM);

			if (TagUtility.isValidString(keyParam, true))
			{
				return keyParam;
			}

			keyParam =
				pageContext.getServletContext().getInitParameter(TagUtility.KEY_CONTEXT_PARAM);

			if (TagUtility.isValidString(keyParam, true))
			{
				return keyParam;
			}

			try
			{
				keyParam =
					(String)(pageContext.getAttribute(TagUtility.KEY_CONTEXT_PARAM,
													  PageContext.APPLICATION_SCOPE));

				if (TagUtility.isValidString(keyParam, true))
				{
					return keyParam;
				}
			}
			catch (NullPointerException e)
			{
				; // Do nothing
			}
		}

		return "";
	}

	/**
	 * Release method.
	 */
	public void release()
	{
		super.release();

		// Reset the key value
		key = null;

		// Reset the values
		reset();
	}

	/**
	 * Reset the values.
	 */
	protected void reset()
	{
		//super.reset();
	}
}
