/*
 *  @(#)TagUtility.java
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
package net.thauvin.google;

import java.net.URLEncoder;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;


/**
 * Implements various methods commonly used by custom tags.
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public class TagUtility
{
	/**
	 * The name of the cache request parameter.
	 */
	public static final String CACHE_PARAM = "cache";

	/**
	 * The name of the filter request parameter.
	 */
	public static final String FILTER_PARAM = "filter";

	/**
	 * The name of the Google Search bean attribute.
	 */
	public static final String GOOGLE_SEARCH_BEAN = "GoogleSearchBean";

	/**
	 * The name of the input encoding context parameter.
	 */
	public static final String IE_PARAM = "ie";

	/**
	 * The name of the key context parameter.
	 */
	public static final String KEY_CONTEXT_PARAM = "google_key";

	/**
	 * The name of the key request parameter.
	 */
	public static final String KEY_PARAM = "key";

	/**
	 * The name of the language restrict context parameter.
	 */
	public static final String LR_PARAM = "lr";

	/**
	 * The name of the maxResults request parameter.
	 */
	public static final String MAX_RESULTS_PARAM = "maxResults";

	/**
	 * The name of the output encoding context parameter.
	 */
	public static final String OE_PARAM = "oe";

	/**
	 * The name of the query request parameter.
	 */
	public static final String QUERY_PARAM = "q";

	/**
	 * The name of the restrict context parameter.
	 */
	public static final String RESTRICT_PARAM = "restrict";

	/**
	 * The name of the safeSearch context parameter.
	 */
	public static final String SAFE_SEARCH_PARAM = "safeSearch";

	/**
	 * The name of the site context parameter.
	 */
	public static final String SITE_PARAM = "site";

	/**
	 * The name of the start request parameter.
	 */
	public static final String START_PARAM = "start";

	/**
	 * Protected constructor to disable instantiation.
	 */
	protected TagUtility()
	{
		// Disabled
	}

	/**
	 * Returns the body of a tag.
	 *
	 * @param bodyContent The body.
	 * @return The tag body string or null.
	 */
	public static final String getTagBody(BodyContent bodyContent)
	{
		return getTagBody(bodyContent, false);
	}

	/**
	 * Returns the body of a tag.
	 *
	 * @param bodyContent The body.
	 * @param trim Trim the body if true
	 * @return The tag body string or null.
	 */
	public static final String getTagBody(BodyContent bodyContent, boolean trim)
	{
		try
		{
			if (trim)
			{
				return bodyContent.getString().trim();
			}
			else
			{
				return bodyContent.getString();
			}
		}
		catch (NullPointerException e)
		{
			return null;
		}
	}

	/**
	 * Validates a string value by insuring it is not null or empty.
	 * <p>
	 * Beginning and trailing spaces are removed whenever the trim
	 * flag is set to true.
	 *
	 * @param stringValue The String value.
	 * @param trim The trim flag.
	 * @return true if valid, false if not.
	 */
	public static final boolean isValidString(String stringValue, boolean trim)
	{
		if ((stringValue != null))
		{
			if (trim)
			{
				return isValidString(stringValue.trim());
			}
			else
			{
				return isValidString(stringValue);
			}
		}

		return false;
	}

	/**
	 * Validates a string value by insuring it is not null or empty.
	 *
	 * @param stringValue The String value.
	 * @return true if valid, false if not.
	 */
	public static final boolean isValidString(String stringValue)
	{
		if ((stringValue != null) && (stringValue.length() > 0))
		{
			return true;
		}

		return false;
	}

	/**
	 * Returns the GoogleSearchBean attribute using the default
	 * {@link #GOOGLE_SEARCH_BEAN bean name}.
	 *
	 * @param pageContext The page context.
	 * @return The default Google search bean.
	 */
	public static GoogleSearchBean getGoogleSearchBean(PageContext pageContext)
	{
		// Fetch the bean using the default name
		return ((GoogleSearchBean)pageContext.findAttribute(GOOGLE_SEARCH_BEAN));
	}

	/**
	 * Returns the value a specified parameter.
	 *
	 * @param request The ServletRequest object
	 * @param paramName The parameter name String value.
	 * @return The parameter value or null.
	 */
	public static String getParameter(ServletRequest request, String paramName)
	{
		// The parameter value
		String paramValue = null;

		if (isValidString(paramName))
		{
			// Get all parameters names
			final Enumeration names = request.getParameterNames();

			// Loop thru the names
			while (names.hasMoreElements())
			{
				// Get the next parameter name
				final String name = (String)names.nextElement();

				// Is it our parameter?
				if (name.equalsIgnoreCase(paramName))
				{
					// Get the parameter value
					paramValue = request.getParameter(name);
				}
			}
		}

		// Return the parameter value
		return paramValue;
	}

	/**
	 * Builds a HTML reference link:
	 * <p>
	 * For example: <code>&lt;a href="url" class="css" style="style" target="target"&gt;body&lt;/a&gt;</code>.
	 *
	 * @param url The reference URL.
	 * @param body The link body text.
	 * @param target The link target.
	 * @param style The link CSS style.
	 * @param css The link CSS class
	 * @return A HTML reference link.
	 */
	public static final String buildRefLink(String url, String body,
											String target, String style,
											String css)
	{
		final StringBuffer refLink = new StringBuffer();

		// Output the hyperlink reference
		refLink.append("<A HREF=\"").append(url).append('"');

		// Is a target specified?
		if (TagUtility.isValidString(target))
		{
			// Output the HREF target
			refLink.append(" TARGET=\"").append(target).append('"');
		}

		if (TagUtility.isValidString(css))
		{
			// Ouput the CSS class
			refLink.append(" CLASS=\"").append(css).append('"');
		}

		if (TagUtility.isValidString(style))
		{
			// Output the CSS style
			refLink.append(" STYLE=\"").append(style).append('"');
		}

		// Close HREF
		refLink.append('>').append(body).append("</A>");

		return (refLink.toString());
	}

	/**
	 * Returns the default tag misplaced (not in container) error exception.
	 *
	 * @param tag The tag name.
	 * @param container The container name.
	 * @return The default misplaced error exception.
	 */
	public static final JspTagException misplacedError(String tag,
													   String container)
	{
		return new JspTagException("The '" + tag
								   + "' tag must be located within the '"
								   + container + "' container tag.");
	}

	/**
	 * Returns the default tag misplaced (not in valid container) error
	 * exception.
	 *
	 * @param tag The tag name.
	 * @return The default misplaced error exception.
	 */
	public static final JspTagException misplacedError(String tag)
	{
		return new JspTagException("The '" + tag
								   + "' tag must be located within a valid container tag.");
	}

	/**
	 * Builds name=value pair with URL encoding.
	 *
	 * @param name The name string.
	 * @param value The value string.
	 * @return The name=value pair.
	 */
	public static final String nameValuePair(String name, String value)
	{
		return (URLEncoder.encode(name) + "=" + URLEncoder.encode(value));
	}

	/**
	 * Returns a nested exception.
	 * <p>
	 * The nested exception format is as follows:
	 * <blockquote>
	 * <code>
	 * New Message<br>
	 * &lt;space>&gt;space&gt;nested exception is:&lt;space&gt;<br>
	 * &lt;tab&gt; java.lang.Exception: Old Message
	 * </code>
	 * </blockquote>
	 *
	 * @param msg The new exception message.
	 * @param old The old exception object.
	 * @return A nested exception.
	 */
	public static final JspTagException nestedException(String msg,
														Exception old)
	{
		return new JspTagException(msg + "\n  nested exception is: \n\t"
								   + old.toString());
	}

	/**
	 * Returns the default tag output error exception.
	 *
	 * @param tag The tag name.
	 * @param e The caught exception.
	 * @return The default tag output error exception.
	 */
	public static final JspTagException outputError(String tag, Exception e)
	{
		StringBuffer buff =
			new StringBuffer("An error occurred while processing the '" + tag
							 + "' tag.");

		if (e != null)
		{
			buff.append("\n\nException:\n");
		}

		if (e != null)
		{
			buff.append("\n  nested exception is: \n\t"
						+ e.getClass().getName() + ": "
						+ e.getLocalizedMessage());
		}

		return new JspTagException(buff.toString());
	}

	/**
	 * Converts the request's parameter names/values into ampersand-delimited
	 * and URL-encoded name/value pairs.
	 *
	 * @param request The servlet request object.
	 * @param remove The parameter to be removed.
	 * @return The name/value pairs.
	 */
	public static final String requestParamsToUrl(HttpServletRequest request,
												  String remove)
	{
		final StringBuffer buff = new StringBuffer();
		final Enumeration names = request.getParameterNames();

		// Are there any parameters?
		if (names.hasMoreElements())
		{
			// Loop through the parameter names
			while (names.hasMoreElements())
			{
				// Get the next parameter name
				final String name = (String)names.nextElement();

				// Is it the name to be removed?
				if ((remove == null) || (!remove.equals(name)))
				{
					// Get the parameter values
					final String values[] = request.getParameterValues(name);

					// Loop through the values
					for (int i = 0; i < values.length; i++)
					{
						// Is it the very first parameter?
						if (buff.length() > 0)
						{
							// Append an ampersand to the buffer
							buff.append("&");
						}

						// Append the parameter name and value to the buffer
						buff.append(nameValuePair(name, values[i]));
					}
				}
			}
		}

		return (buff.toString());
	}
}
