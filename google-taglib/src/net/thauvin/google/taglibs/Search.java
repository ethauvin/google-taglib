/*
 *  @(#)Search.java
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
import javax.servlet.jsp.PageContext;

/**
 * A custom tag used to perform Google searches.
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public class Search extends QuerySupport
{
	private GoogleSearchBean bean = null;
	private boolean cache = GoogleSearchBean.DEFAULT_CACHE;
	private boolean filter = GoogleSearchBean.DEFAULT_FILTER;
	private String lr = GoogleSearchBean.DEFAULT_LR;
	private int maxResults = GoogleSearchBean.DEFAULT_MAX_RESULTS;
	private String restrict = GoogleSearchBean.DEFAULT_RESTRICT;
	private boolean safeSearch = GoogleSearchBean.DEFAULT_SAFE_SEARCH;
	private String site = GoogleSearchBean.DEFAULT_SITE;
	private int start = GoogleSearchBean.DEFAULT_START;
	private String type = GoogleSearchBean.DEFAULT_TYPE;

	/**
	 * Sets the cache attribute.
	 *
	 * @param cache The new attribute value.
	 */
	public final void setCache(String cache)
	{
		this.cache = Boolean.valueOf(cache).booleanValue();
	}

	/**
	 * Sets the (related-query) filter attribute.
	 *
	 * @param filter The new attribute value.
	 */
	public final void setFilter(String filter)
	{
		this.filter = Boolean.valueOf(filter).booleanValue();
	}

	/**
	 * Sets the lr (language restrict) attribute.
	 *
	 * @param lr The new attribute value.
	 */
	public final void setLr(String lr)
	{
		this.lr = lr;
	}

	/**
	 * Sets the maximum number of results to be returned.
	 *
	 * @param maxResults The new attribute value.
	 */
	public final void setMaxResults(String maxResults)
	{
		try
		{
			this.maxResults = Integer.valueOf(maxResults).intValue();
		}
		catch (NumberFormatException e)
		{
			;// Do nothing
		}
	}

	/**
	 * Sets the restrict attribute.
	 *
	 * @param restrict The new restrict attribute.
	 */
	public final void setRestrict(String restrict)
	{
		this.restrict = restrict;
	}

	/**
	 * Sets the safeSearch attribute.
	 *
	 * @param safeSearch The new attribute value.
	 */
	public final void setSafeSearch(String safeSearch)
	{
		this.safeSearch = Boolean.valueOf(safeSearch).booleanValue();
	}

	/**
	 * Sets the site attribute.
	 *
	 * @param site The new attribute value.
	 */
	public final void setSite(String site)
	{
		this.site = site;
	}

	/**
	 * Sets the start attribute.
	 *
	 * @param start The new attribute value.
	 */
	public final void setStart(String start)
	{
		try
		{
			this.start = Integer.valueOf(start).intValue();
		}
		catch (NumberFormatException e)
		{
			;// Do nothing
		}
	}

	/**
	 * Sets the (file) type attribute
	 *
	 * @param type The new attribute value.
	 */
	public final void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Returns the cache attribute.
	 *
	 * @return The attribute value.
	 */
	private final boolean getCache()
	{
		return getBoolParam(TagUtility.CACHE_PARAM, cache);
	}

	/**
	 * Returns the (related-query) filter attribute.
	 *
	 * @return The attribute value.
	 */
	private final boolean getFilter()
	{
		return getBoolParam(TagUtility.FILTER_PARAM, filter);
	}

	/**
	 * Returns the lr (language restrict) attribute.
	 *
	 * @return The attribute value.
	 */
	private final String getLr()
	{
		return getStringParam(TagUtility.LR_PARAM, lr);
	}

	/**
	 * Returns the maximum number of results to be returned.
	 *
	 * @return The attribute value.
	 */
	private final int getMaxResults()
	{
		return getIntParam(TagUtility.MAX_RESULTS_PARAM, maxResults);
	}

	/**
	 * Returns the restrict attribute.
	 *
	 * @return The attribute value.
	 */
	private final String getRestrict()
	{
		return getStringParam(TagUtility.RESTRICT_PARAM, restrict);
	}

	/**
	 * Returns the safeSearch attribute.
	 *
	 * @return The attribute value.
	 */
	private final boolean getSafeSearch()
	{
		return getBoolParam(TagUtility.SAFE_SEARCH_PARAM, safeSearch);
	}

	/**
	 * Returns the site attribute.
	 *
	 * @return The attribute value.
	 */
	private final String getSite()
	{
		String site = getStringParam(TagUtility.SITE_PARAM, this.site);

		if (site.length() > 0)
		{
			return ("site:" + site + ' ');
		}

		return "";
	}

	/**
	 * Returns the start attribute.
	 *
	 * @return The attribute value.
	 */
	private final int getStart()
	{
		return getIntParam(TagUtility.START_PARAM, start);
	}

	/**
	 * Returns the (file) type attribute.
	 *
	 * @return The attribute value
	 */
	private final String getType()
	{
		String type = getStringParam(TagUtility.TYPE_PARAM, this.type);

		if (type.length() > 0)
		{
			return (" filetype:" + type);
		}

		return "";
	}

	/**
	 * Converts a request parameter to a boolean.
	 *
	 * @param paramName The parameter name.
	 * @param defaultValue The default value to use if the parameter is empty.
	 * @return The boolean value.
	 */
	private boolean getBoolParam(String paramName, boolean defaultValue)
	{
		String param =
				TagUtility.getParameter(pageContext.getRequest(), paramName);

		if (TagUtility.isValidString(param, true))
		{
			return Boolean.valueOf(param).booleanValue();
		}

		return defaultValue;
	}

	/**
	 * Converts a request parameter to an int.
	 *
	 * @param paramName The parameter name.
	 * @param defaultValue The default value to use if the parameter is empty.
	 * @return The int value.
	 */
	private int getIntParam(String paramName, int defaultValue)
	{
		String param =
				TagUtility.getParameter(pageContext.getRequest(), paramName);

		if (TagUtility.isValidString(param, true))
		{
			try
			{
				return Integer.valueOf(param).intValue();
			}
			catch (NumberFormatException e)
			{
				;// Do nothing
			}
		}

		return defaultValue;
	}

	/**
	 * Converts a request parameter to a string.
	 *
	 * @param paramName The parameter name.
	 * @param defaultValue The default value to use if the parameter is empty.
	 * @return The string value.
	 */
	private String getStringParam(String paramName, String defaultValue)
	{
		String param =
				TagUtility.getParameter(pageContext.getRequest(), paramName);

		if (TagUtility.isValidString(param, true))
		{
			return param;
		}

		return defaultValue;
	}

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
				bean.setProxyServer(pageContext.getServletContext()
						.getInitParameter(TagUtility.GOOGLE_PROXY_HOST),
						pageContext.getServletContext()
						.getInitParameter(TagUtility.GOOGLE_PROXY_PORT),
						pageContext.getServletContext()
						.getInitParameter(TagUtility.GOOGLE_PROXY_USERNAME),
						pageContext.getServletContext()
						.getInitParameter(TagUtility.GOOGLE_PROXY_PASSWORD));

                bean.setKeywords(getQuery());
                
				bean.getGoogleSearch(getKey(), getSite() + getQuery() + getType(),
						getStart(), getMaxResults(), getFilter(),
						getRestrict(), getSafeSearch(), getLr());
			}
			catch (Exception e)
			{
				throw TagUtility.outputError("search", e);
			}
		}
		else if (!getCache())
		{
			bean.reset();
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
		// Get the Google bean
		bean = TagUtility.getGoogleSearchBean(pageContext);

		// Create a new bean if it doesn't exists
		if (bean == null)
		{
			try
			{
				bean = new GoogleSearchBean();

				// Set the bean as named session attribute
				pageContext.setAttribute(TagUtility.GOOGLE_SEARCH_BEAN, bean,
						PageContext.SESSION_SCOPE);
			}
			catch (Exception e)
			{
				throw new JspException("An unknown error ocurred while creating the Google search bean.");
			}
		}

		return EVAL_BODY_TAG;
	}

	/**
	 * Release method.
	 */
	public void release()
	{
		super.release();

		// Reset all attributes
		start = GoogleSearchBean.DEFAULT_START;
		maxResults = GoogleSearchBean.DEFAULT_MAX_RESULTS;
		filter = GoogleSearchBean.DEFAULT_FILTER;
		safeSearch = GoogleSearchBean.DEFAULT_SAFE_SEARCH;
		restrict = GoogleSearchBean.DEFAULT_RESTRICT;
		lr = GoogleSearchBean.DEFAULT_LR;
		site = GoogleSearchBean.DEFAULT_SITE;
		cache = GoogleSearchBean.DEFAULT_CACHE;
		type = GoogleSearchBean.DEFAULT_TYPE;

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
		super.reset();
	}
}
