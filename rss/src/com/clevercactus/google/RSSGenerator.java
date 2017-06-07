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

import org.xml.sax.SAXException;

import java.io.PrintWriter;
import java.util.Date;

/**
 * A class that uses JAXP and SAX to print an SearchResults 2.0 feed to a PrintWriter.
 * Output of the SearchResults happens as data is added. See the main() method for a
 * simple usage example.
 *
 * @version 1.0, 29/08/2003
 * @author Diego Doval
 */
public
class RSSGenerator
	extends FeedGenerator
{

	/**
	 * The one and only constructor.
	 */
	public
	RSSGenerator()
	{
	}

	/**
	 * Prints a valid SearchResults 2.0 header based on the information passed.
	 *
	 * @param title feed title
	 * @param link feed link
	 * @param description feed description
	 * @param author feed author (must be an email address)
	 * @param lastBuildDate the date the feed was built
	 * @throws FeedGenerationException
	 */
	public
	void
	printHeader(String title, String link, String description, String author, Date lastBuildDate)
		throws FeedGenerationException
	{
		try {
			String language = "en-us";
			String generator = "clevercactus rss generator 1.0";

			feedAttributes_d.clear();
			feedAttributes_d.addAttribute("", "", "version", "", "2.0");
			transformerHandler_d.startElement("","","rss",feedAttributes_d);
			feedAttributes_d.clear();
			transformerHandler_d.startElement("","","channel",feedAttributes_d);
			transformerHandler_d.startElement("","","title",feedAttributes_d);
			transformerHandler_d.characters(title.toCharArray(),0,title.length());
			transformerHandler_d.endElement("","","title");
			transformerHandler_d.startElement("","","link",feedAttributes_d);
			transformerHandler_d.characters(link.toCharArray(),0,link.length());
			transformerHandler_d.endElement("","","link");
			transformerHandler_d.startElement("","","description", feedAttributes_d);
			transformerHandler_d.characters(description.toCharArray(),0,description.length());
			transformerHandler_d.endElement("","","description");
			transformerHandler_d.startElement("","","language", feedAttributes_d);
			transformerHandler_d.characters(language.toCharArray(),0,language.length());
			transformerHandler_d.endElement("","","language");
			transformerHandler_d.startElement("","","lastBuildDate",elementAttributes_d);
			String dtString = getAsRFC822String(lastBuildDate);
			transformerHandler_d.characters(dtString.toCharArray(),0,dtString.length());
			transformerHandler_d.endElement("","","lastBuildDate");
			transformerHandler_d.startElement("","","generator", feedAttributes_d);
			transformerHandler_d.characters(generator.toCharArray(),0,generator.length());
			transformerHandler_d.endElement("","","generator");
			transformerHandler_d.startElement("","","managingEditor", feedAttributes_d);
			transformerHandler_d.characters(author.toCharArray(),0,author.length());
			transformerHandler_d.endElement("","","managingEditor");
			transformerHandler_d.startElement("","","webMaster", feedAttributes_d);
			transformerHandler_d.characters(author.toCharArray(),0,author.length());
			transformerHandler_d.endElement("","","webMaster");
		}
		catch (SAXException e) {
			throw new FeedGenerationException(e);
		}
	}

	/**
	 * Prints a valid SearchResults 2.0 item based on the information passed.
	 *
	 * @param title the entry title
	 * @param description the entry body/description
	 * @param link the entry's GUID
	 * @param date the entry publication date
	 * @throws FeedGenerationException
	 */
	public
	void
	printItem(String title, String description, String link, Date date)
		throws FeedGenerationException
	{
		try {
			elementAttributes_d.clear();
			transformerHandler_d.startElement("","","item",elementAttributes_d);
			elementAttributes_d.clear();
			transformerHandler_d.startElement("","","title",elementAttributes_d);
			transformerHandler_d.characters(title.toCharArray(),0,title.length());
			transformerHandler_d.endElement("","","title");
			elementAttributes_d.clear();
			transformerHandler_d.startElement("","","description",elementAttributes_d);
			transformerHandler_d.characters(description.toCharArray(),0,description.length());
			transformerHandler_d.endElement("","","description");
			elementAttributes_d.clear();
			transformerHandler_d.startElement("","","pubDate",elementAttributes_d);
			String dtString = getAsRFC822String(date);
			transformerHandler_d.characters(dtString.toCharArray(),0,dtString.length());
			transformerHandler_d.endElement("","","pubDate");
			elementAttributes_d.clear();
			transformerHandler_d.startElement("","","link",elementAttributes_d);
			transformerHandler_d.characters(link.toCharArray(),0,link.length());
			transformerHandler_d.endElement("","","link");

			transformerHandler_d.endElement("","","item");
		}
		catch (SAXException e) {
			throw new FeedGenerationException(e);
		}

	}

  /**
	 * Closes the feed's main elements (i.e., "rss" and "channel").
	 * @throws FeedGenerationException
	 */
	public
	void
	close()
		throws FeedGenerationException
	{
		try {
			transformerHandler_d.endElement("","","channel");
			transformerHandler_d.endElement("","","rss");
			transformerHandler_d.endDocument();
		}
		catch (SAXException e) {
			throw new FeedGenerationException(e);
		}
	}


	/**
	 * Simple test to generate a 2.0 feed.
	 */
	public static
	void
	main(String[] args)
	{
		try {
			RSSGenerator gen = new RSSGenerator();
			gen.init(new PrintWriter(System.out));
			gen.printHeader("Test feed", "http://something/", "test feed desc", "test@test.com", new Date());
			gen.printItem("Item title 1", "Item desc 1", "guid", new Date());
			gen.printItem("Item title 2", "Item desc 2", "guid", new Date());
			gen.close();
		}
		catch (FeedGenerationException e) {
			e.printStackTrace();
		}
	}
}
