/*
 *  Copyright (c) 2003-2004 clevercactus ltd.
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
 */
package com.clevercactus.google;

import com.google.soap.search.GoogleSearchResultElement;

import java.io.Writer;
import java.io.IOException;
import java.util.Iterator;
import java.util.Date;
import java.util.ArrayList;

/**
 * Date: 30-Oct-2003
 * Time: 13:41:41
 * @author Diego Doval
 * @author Erik C. Thauvin
 */
public
class GoogleResultsWrapper
{
	ArrayList items_d = new ArrayList();


	private
	GoogleResultsWrapper()
	{
	}

	private
	Iterator
	iterator()
	{
		return items_d.iterator();
	}

	private
	void
	addItem(GoogleResultsWrapperItem it)
	{
		items_d.add(it);
	}

	private static
	GoogleResultsWrapper
	createWrapper(GoogleSearchResultElement[] elements)
	{
		if (elements == null || elements.length == 0) {
			return null;
		}
		GoogleResultsWrapper wrapper = new GoogleResultsWrapper();
		for (int i = 0; i < elements.length; i++) {
			GoogleSearchResultElement element = elements[i];
			wrapper.addItem(new GoogleResultsWrapperItem(element.getTitle(), element.getURL(), element.getSnippet()));
		}
		return wrapper;
	}

	/**
	 * Output the results to a writer.
	 *
	 * @param out the writer to use
	 * @param elements the search result elements
	 * @param query the search query
	 * @param feedType the type of feed to use (options are "rss" and "atom")
	 */
	public static
	void
	outputResults(Writer out, GoogleSearchResultElement[] elements, String query, String feedType)
	{
		outputResults(out, createWrapper(elements), query, feedType);
	}

	/**
	 * Output the results to a writer.
	 *
	 * @param out the writer to use
	 * @param wrapper the results object obtained from the parsing of results
	 * @param query the search query
	 * @param feedType the type of feed to use (options are "rss" and "atom")
	 */
	private static
	void
	outputResults(Writer out, GoogleResultsWrapper wrapper, String query, String feedType)
	{
		try {
			if (out == null || feedType == null) {
				try {
					out.write("Null parameter in output results.\n");
				}
				catch (IOException e1) {
				}
				return;
			}
			if (!feedType.equals(FeedGeneratorFactory.FEED_TYPE_RSS)
						&& !feedType.equals(FeedGeneratorFactory.FEED_TYPE_ATOM)) {
				try {
					out.write("Invalid feed type ("+feedType+"). Supported types are 'rss' and 'atom'.\n");
				}
				catch (IOException e1) {
				}
				return;
			}
			try {
				FeedGenerator gen = FeedGeneratorFactory.getGeneratorForType(feedType);
				gen.init(out);
				Date date = new Date();

				String description;
				if ((query == null) || (query.trim().length() == 0)) {
					description = "Google Tag Library";
				} else {
					description = "Search results for: \"" + query + '"';
				}

				gen.printHeader("Google Search Feed Output", "http://google-taglib.sourceforge.net/", description, "check@page.for.info", date);
				if (wrapper != null) {
					Iterator items = wrapper.iterator();
					while (items.hasNext()) {
						GoogleResultsWrapperItem item = (GoogleResultsWrapperItem) items.next();
						gen.printItem(item.getTitle(), item.getSnippet(), item.getURL(), date);
					}
				}
				gen.close();
			}
			catch (FeedGenerationException e) {
				try {
					out.write("Error.\n");
					out.write(e.getMessage());
				}
				catch (IOException e1) {
				}
			}
		}
		finally {
			try {
				out.close();
			}
			catch (IOException e) {
			}
		}
	}

}