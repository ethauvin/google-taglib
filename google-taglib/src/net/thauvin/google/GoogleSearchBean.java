/*
 *  @(#)GoogleSearchBean.java
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

import com.google.soap.search.GoogleSearch;
import com.google.soap.search.GoogleSearchFault;
import com.google.soap.search.GoogleSearchResult;
import com.google.soap.search.GoogleSearchResultElement;

import java.net.URL;
import java.net.URLEncoder;


/**
 * Implements methods used to perform searches on Google.
 *
 * @author Erik C. Thauvin
 * @created April 25, 2002
 * @version $Revision$
 * @since 1.0
 */
public class GoogleSearchBean
{
	/**
	 * The default cache flag.
	 */
	public static final boolean DEFAULT_CACHE = true;

	/**
	 * The default related-queries filter.
	 */
	public static final boolean DEFAULT_FILTER = true;

	/**
	 * The default language restricts.
	 */
	public static final String DEFAULT_LR = "";

	/**
	 * The default maximum number of results to be returned.
	 */
	public static final int DEFAULT_MAX_RESULTS = 10;

	/**
	 * The default document store restrict.
	 */
	public static final String DEFAULT_RESTRICT = "";

	/**
	 * The default SafeSearch.
	 */
	public static final boolean DEFAULT_SAFE_SEARCH = false;

	/**
	 * The default site.
	 */
	public static final String DEFAULT_SITE = "";

	/**
	 * The default index of the result to be returned.
	 */
	public static final int DEFAULT_START = 0;

	/**
	 * The default filetype.
	 */
	public static final String DEFAULT_TYPE = "";

	/**
	 * The <em>next</em> keyword.
	 */
	public static final String NEXT_KEYWORD = "next";

	/**
	 * The <em>previous</em> keyword.
	 */
	public static final String PREVIOUS_KEYWORD = "previous";

	// Invalid key error message.
	private static final String INVALID_KEY_ERROR =
		"The authorization key has not been specified.";
	private GoogleSearch service = null;
	private GoogleSearchResult result = null;
	private GoogleSearchResultElement elements[] = null;
	private boolean keySet = false;
	private int maxResults = 10;

	/**
	 * Constructs a new instance of the bean.
	 */
	public GoogleSearchBean()
	{
		service = new GoogleSearch();
	}

	/**
	 * Constructs a new instance of the bean with the specified authorization
	 * key.
	 *
	 * @param key The authorization key.
	 */
	public GoogleSearchBean(String key)
	{
		this();
		setKey(key);
	}

	/**
	 * Sets the authorization key.
	 *
	 * @param key The key string.
	 */
	public final void setKey(String key)
	{
		service.setKey(key);

		if (isValidString(key))
		{
			keySet = true;
		}
		else
		{
			keySet = false;
		}
	}

	/**
	 * Returns true if the authorization key is set.
	 *
	 * @return true or false.
	 */
	public final boolean isKeySet()
	{
		return keySet;
	}

	/**
	 * Sets the HTTP proxy host, port, user name and password.
	 *
	 * @param proxyHost The host to use for the HTTP proxy.
	 * @param proxyPort The port to use for the HTTP proxy.
	 * @param proxyUserName The user name to use for the HTTP proxy.
	 * @param proxyPassword The password to use for the HTTP proxy.
	 */
	public void setProxyServer(String proxyHost, String proxyPort,
							   String proxyUserName, String proxyPassword)
	{
		int port = -1;

		if (isValidString(proxyPort))
		{
			try
			{
				port = Integer.valueOf(proxyPort).intValue();
			}
			catch (NumberFormatException e)
			{
				; // Do nothing.
			}
		}

		setProxyServer(proxyHost, port, proxyUserName, proxyPassword);
	}

	/**
	 * Sets the HTTP proxy host, port, user name and password.
	 *
	 * @param proxyHost The host to use for the HTTP proxy.
	 * @param proxyPort The port to use for the HTTP proxy.
	 * @param proxyUserName The user name to use for the HTTP proxy.
	 * @param proxyPassword The password to use for the HTTP proxy.
	 */
	public void setProxyServer(String proxyHost, int proxyPort,
							   String proxyUserName, String proxyPassword)
	{
		if (isValidString(proxyHost))
		{
			service.setProxyHost(proxyHost);

			if (proxyPort > 0)
			{
				service.setProxyPort(proxyPort);
			}

			if (isValidString(proxyUserName))
			{
				service.setProxyUserName(proxyUserName);
			}

			if (isValidString(proxyPassword))
			{
				service.setProxyPassword(proxyPassword);
			}
		}
	}

	/**
	 * Returns the results of the search.
	 *
	 * @return The GoogleSearchResult object.
	 */
	public final GoogleSearchResult getResult()
	{
		return result;
	}

	/**
	 * Returns an array of result elements that corresponds to the actual list
	 * of search results.
	 *
	 * @return The array of result elements.
	 */
	public final GoogleSearchResultElement[] getResultElements()
	{
		return elements;
	}

	/**
	 * Returns the count of result elements.
	 *
	 * @return The result elements count.
	 * @see #getResultElements()
	 */
	public final int getResultElementsCount()
	{
		if (elements != null)
		{
			return elements.length;
		}

		return 0;
	}

	/**
	 * Returns true whenever the result set is valid, indicating that a search
	 * was performed.
	 *
	 * @return true or false.
	 */
	public final boolean isValidResult()
	{
		if (result != null)
		{
			return true;
		}

		return false;
	}

	/**
	 * Returns a cached web page from Google.
	 *
	 * @param url The page's URL.
	 * @return The HTML code of the cached page.
	 * @exception GoogleSearchFault
	 */
	public String getCachedPage(String url)
						 throws GoogleSearchFault
	{
		if (isKeySet())
		{
			reset();

			return new String(service.doGetCachedPage(url));
		}

		throw new GoogleSearchFault(INVALID_KEY_ERROR);
	}

	/**
	 * Invokes a Google search.
	 *
	 * @param q The Google query.
	 * @param start The index of the result to be returned.
	 * @param maxResults The maximum number of results to be returned.
	 * @param filter The related-queries filter.
	 * @param restrict The document store restrict value (e.g.: "linux").
	 * @param safeSearch Enable or disable SafeSearch.
	 * @param lr The language restricts for the search.
	 * @return The results of the search.
	 * @exception GoogleSearchFault
	 */
	public GoogleSearchResult getGoogleSearch(String q, int start,
											  int maxResults, boolean filter,
											  String restrict,
											  boolean safeSearch, String lr)
									   throws GoogleSearchFault
	{
		if (isKeySet())
		{
			reset();

			service.setQueryString(q);
			service.setStartResult(start);

			this.maxResults = maxResults;
			service.setMaxResults(maxResults);

			service.setFilter(filter);
			service.setRestrict(restrict);
			service.setSafeSearch(safeSearch);
			service.setLanguageRestricts(lr);

			result = service.doSearch();

			if (result != null)
			{
				elements = result.getResultElements();
			}

			return result;
		}

		throw new GoogleSearchFault(INVALID_KEY_ERROR);
	}

	/**
	 * Returns the GoogleSearch attribute of the GoogleSearchBean object.
	 *
	 * @param key The authorization key.
	 * @param q The Google query.
	 * @param start The index of the result to be returned.
	 * @param maxResults The maximum number of results to be returned.
	 * @param filter The related-queries filter.
	 * @param restrict The document store restrict value (e.g.: "linux").
	 * @param safeSearch Enable or disable SafeSearch.
	 * @param lr The language restricts for the search.
	 * @return The results of the search.
	 * @exception GoogleSearchFault
	 * @see #getGoogleSearch(String, int, int, boolean, String, boolean, String)
	 */
	public GoogleSearchResult getGoogleSearch(String key, String q, int start,
											  int maxResults, boolean filter,
											  String restrict,
											  boolean safeSearch, String lr)
									   throws GoogleSearchFault
	{
		setKey(key);

		return getGoogleSearch(q, start, maxResults, filter, restrict,
							   safeSearch, lr);
	}

	/**
	 * Returns the GoogleSearch attribute of the GoogleSearchBean object.
	 *
	 * @param q The Google query.
	 * @return The results of the search.
	 * @exception GoogleSearchFault
	 * @see #getGoogleSearch(String, int, int, boolean, String, boolean, String)
	 */
	public GoogleSearchResult getGoogleSearch(String q)
									   throws GoogleSearchFault
	{
		return getGoogleSearch(q, DEFAULT_START, DEFAULT_MAX_RESULTS,
							   DEFAULT_FILTER, DEFAULT_RESTRICT,
							   DEFAULT_SAFE_SEARCH, DEFAULT_LR);
	}

	/**
	 * Returns the specified property of the given element index in the current
	 * result set.
	 * <p>The properties are:</p>
	 * <table border="3">
	 * <tr><td><code>"summary"</code></td><td>Returns the ODP summary text
	 * string.</td></tr>
	 * <tr><td><code>"url"</code></td><td>Returns the absolute URL path of the
	 * search.</td></tr>
	 * <tr><td><code>"snippet"</code></td><td>Returns a text snippet of the
	 * query in context.</td></tr>
	 * <tr><td><code>"title"</code></td><td>Returns the title (HTML) of the
	 * search result.</td></tr>
	 * <tr><td><code>"cachedSize"</code></td><td>Returns the size of (size +
	 * <code>k</code>) the cached version of the URL, in kilobytes.</td></tr>
	 * <tr><td><code>"relatedInformationPresent"</code></td><td>Returns
	 * <code>true</code> when the <em>related:</em> query term is supported for
	 * this URL; <code>false</code>, otherwise.</td></tr>
	 * <tr><td><code>"hostName"</code></td><td>Returns the host name.</td></tr>
	 * <tr><td><code>"directoryTitle"</code></td><td>Returns the ODP directory
	 * title.</td></tr>
	 * <tr><td><code>"directoryCategoryName"</code></td><td>Returns the ODP
	 * directory name of the current ODP category.</td></tr>
	 * <tr><td><code>"directoryCategoryEncoding"</code></td><td>Returns the
	 * encoding scheme of the current ODP category.</td></tr>
	 * <tr><td><code>"relatedQuery"</code></td><td>Returns the related query
	 * string, suitable for use as a {@link #getGoogleSearch(String) search}
	 * query string.<br>For example:
	 * <code>related:www.example.com/search?q=vacation%20hawaii</code></td></tr>
	 * <tr><td><code>"cachedQuery"</code></td><td>Returns the cached query
	 * string, suitable for use as a {@link #getCachedPage(String) cached} query
	 * string.<br>For example: <code>www.example.com/search?q=vacation%20hawaii</code>
	 * </td></tr>
	 * <tr><td><code>"staticQuery"</code></td><td>The static query, suitable for
	 * display.<br>For example: <code>www.example.com/search?q=vacation hawaii</code>
	 * </td></tr>
	 * </table>
	 *
	 * @param index The element index.
	 * @param property The property name.
	 * @return The property value.
	 */
	public String getResultElementProperty(int index, String property)
	{
		if (elements != null)
		{
			if ((index >= 0) && (index < elements.length))
			{
				if (property.equalsIgnoreCase("url"))
				{
					return elements[index].getURL();
				}
				else if (property.equalsIgnoreCase("summary"))
				{
					return elements[index].getSummary();
				}
				else if (property.equalsIgnoreCase("snippet"))
				{
					return elements[index].getSnippet();
				}
				else if (property.equalsIgnoreCase("title"))
				{
					return elements[index].getTitle();
				}
				else if (property.equalsIgnoreCase("cachedSize"))
				{
					return elements[index].getCachedSize();
				}
				else if (property.equalsIgnoreCase("hostName"))
				{
					return elements[index].getHostName();
				}
				else if (property.equalsIgnoreCase("relatedInformationPresent"))
				{
					return String.valueOf(elements[index]
										  .getRelatedInformationPresent());
				}
				else if (property.equalsIgnoreCase("directoryTitle"))
				{
					return elements[index].getDirectoryTitle();
				}
				else if (property.equalsIgnoreCase("directoryCategoryName"))
				{
					return elements[index].getDirectoryCategory()
										  .getFullViewableName();
				}
				else if (property.equalsIgnoreCase("directoryCategoryEncoding"))
				{
					return elements[index].getDirectoryCategory()
										  .getSpecialEncoding();
				}
				else if (property.toLowerCase().endsWith("query"))
				{
					try
					{
						URL url = new URL(elements[index].getURL());
						String urlString = url.toString();
						String staticQuery =
							urlString.substring(urlString.indexOf(url.getHost()));

						if (property.equalsIgnoreCase("relatedQuery"))
						{
							return ("related:" + URLEncoder.encode(staticQuery));
						}
						else if (property.equalsIgnoreCase("cachedQuery"))
						{
							return (URLEncoder.encode(staticQuery));
						}
						else if (property.equalsIgnoreCase("staticQuery"))
						{
							return staticQuery;
						}
					}
					catch (Exception e)
					{
						; // Do nothing
					}
				}
			}
		}

		return "";
	}

	/**
	 * Returns the given property of the result set.
	 * <p>The properties are:</p>
	 * <table border="3">
	 * <tr><td><code>"estimatedTotalResultsCount"</code></td><td>Returns the
	 * estimated total number of results returned for the query.</td></tr>
	 * <tr><td><code>"startIndex"</code></td><td>Returns the index (1-based) of
	 * the first search result in the result elements.</td></tr>
	 * <tr><td><code>"endIndex"</code></td><td>Returns the index (1-based) of
	 * the last search result in the result elements.</td></tr>
	 * <tr><td><code>"searchTime"</code></td><td>Returns the total server time
	 * to process the query, in seconds.</td></tr>
	 * <tr><td><code>"searchTips"</code></td><td>Returns a string providing
	 * instructive suggestions on how to use Google.</td></tr>
	 * <tr><td><code>"searchComments"</code></td><td>Returns a string intended
	 * for display to the end user. (e.g.: list of removed <em>stop words</em>,
	 * etc.)</td></tr>
	 * <tr><td><code>"documentFiltering"</code></td><td>Returns
	 * <code>true</code> if filtering was performed on the search results;
	 * <code>false</code> otherwise.</td></tr>
	 * <tr><td><code>"searchQuery"</code></td><td>Returns the query string that
	 * generated this result.</td></tr>
	 * <tr><td><code>{@link #NEXT_KEYWORD next}</code></td><td>Returns the start
	 * index of the next set of results.</td></tr>
	 * <tr><td><code>{@link #PREVIOUS_KEYWORD previous}</code></td><td>Returns
	 * the start index of the previous set of results.</td></tr>
	 * </table>
	 *
	 * @param property The property name.
	 * @return The property value.
	 */
	public String getResultProperty(String property)
	{
		if (result != null)
		{
			if (property.equalsIgnoreCase("estimatedTotalResultsCount"))
			{
				return String.valueOf(result.getEstimatedTotalResultsCount());
			}
			else if (property.equalsIgnoreCase("startIndex"))
			{
				return String.valueOf(result.getStartIndex());
			}
			else if (property.equalsIgnoreCase("endIndex"))
			{
				return String.valueOf(result.getEndIndex());
			}
			else if (property.equalsIgnoreCase("searchTime"))
			{
				return String.valueOf(result.getSearchTime());
			}
			else if (property.equalsIgnoreCase("searchTips"))
			{
				return result.getSearchTips();
			}
			else if (property.equalsIgnoreCase("searchComments"))
			{
				return result.getSearchComments();
			}
			else if (property.equalsIgnoreCase("documentFiltering"))
			{
				return String.valueOf(result.getDocumentFiltering());
			}
			else if (property.equalsIgnoreCase("searchQuery"))
			{
				return result.getSearchQuery();
			}
			else if (property.equalsIgnoreCase(NEXT_KEYWORD))
			{
				if (result.getEndIndex() < result.getEstimatedTotalResultsCount())
				{
					if (maxResults == (result.getEndIndex()
							- result.getStartIndex() + 1))
					{
						return String.valueOf(result.getEndIndex());
					}
				}
			}
			else if (property.equalsIgnoreCase(PREVIOUS_KEYWORD))
			{
				if (result.getStartIndex() > 1)
				{
					return String.valueOf(result.getStartIndex() - maxResults
										  - 1);
				}
			}
		}

		return "";
	}

	/**
	 * Asks Google to return a spelling suggestion for a word or phrase.
	 *
	 * @param phrase The word or phrase to correct the spelling for.
	 * @return The suggested correct spelling, or null if none.
	 * @exception GoogleSearchFault
	 */
	public String getSpellingSuggestion(String phrase)
								 throws GoogleSearchFault
	{
		if (isKeySet())
		{
			reset();

			return service.doSpellingSuggestion(phrase);
		}

		throw new GoogleSearchFault(INVALID_KEY_ERROR);
	}

	/**
	 * Demonstration program to perform various Google searches.
	 * <p>The arguments are:</p>
	 * <code>&lt;client-key&gt; (search &lt;query&gt; | cached &lt;URL&gt; |
	 * spell &lt;phrase&gt;)</code>
	 *
	 * @param args The command line arguments.
	 */
	public static final void main(String args[])
	{
		GoogleSearchBean bean = new GoogleSearchBean();

		if (args.length == 3)
		{
			String action = args[1];

			try
			{
				bean.setKey(args[0]);

				if (action.equalsIgnoreCase("search"))
				{
					bean.getGoogleSearch(args[2]);

					for (int i = 0; i < bean.getResultElementsCount(); i++)
					{
						System.out.println(bean.getResultElementProperty(i,
																		 "title")
										   + " ("
										   + bean.getResultElementProperty(i,
																		   "url")
										   + ')');
					}
				}
				else if (action.equalsIgnoreCase("spell"))
				{
					System.out.println(bean.getSpellingSuggestion(args[2]));
				}
				else if (action.equalsIgnoreCase("cached"))
				{
					System.out.println(bean.getCachedPage(args[2]));
				}
				else
				{
					usage();
				}
			}
			catch (GoogleSearchFault googleSearchFault)
			{
				googleSearchFault.printStackTrace();
			}
		}
		else
		{
			usage();
		}
	}

	/**
	 * Reset the bean properties.
	 */
	public void reset()
	{
		// Reset the result and elements
		result = null;
		elements = null;
	}

	/**
	 * Validates a string value by insuring it is not null or empty.
	 *
	 * @param stringValue The String value.
	 * @return true if valid, false if not.
	 */
	private boolean isValidString(String stringValue)
	{
		if ((stringValue != null) && (stringValue.trim().length() > 0))
		{
			return true;
		}

		return false;
	}

	/**
	 * Prints the usage and exits.
	 */
	private static void usage()
	{
		System.err.println("Usage: java " + GoogleSearchBean.class.getName()
						   + " <client-key> (search <query> | cached <URL> | spell <phrase>)");
		System.exit(1);
	}
}
