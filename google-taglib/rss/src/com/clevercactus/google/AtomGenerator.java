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
class AtomGenerator
	extends FeedGenerator
{
	/**
	 * The one and only constructor.
	 */
	public
	AtomGenerator()
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
			String generatorName = "clevercactus atom generator 1.0";
			String generatorURL = "http://www.clevercactus.com/google/";
			String copyright = "Copyright (c) 2003-2004 clevercactus ltd.";
			String authorName = "Diego Doval";

			feedAttributes_d.clear();
			feedAttributes_d.addAttribute("", "", "version", "", "0.2");
			feedAttributes_d.addAttribute("", "", "xmlns", "", "http://purl.org/atom/ns#");
			feedAttributes_d.addAttribute("", "", "xml:lang", "", "en");
			transformerHandler_d.startElement("","","feed",feedAttributes_d);

			feedAttributes_d.clear();
			transformerHandler_d.startElement("","","author",feedAttributes_d);
			transformerHandler_d.startElement("","","name",feedAttributes_d);
			transformerHandler_d.characters(authorName.toCharArray(),0,authorName.length());
			transformerHandler_d.endElement("","","name");
			transformerHandler_d.endElement("","","author");

			feedAttributes_d.clear();
			transformerHandler_d.startElement("","","title",feedAttributes_d);
			transformerHandler_d.characters(title.toCharArray(),0,title.length());
			transformerHandler_d.endElement("","","title");
			transformerHandler_d.startElement("","","link",feedAttributes_d);
			transformerHandler_d.characters(link.toCharArray(),0,link.length());
			transformerHandler_d.endElement("","","link");
			transformerHandler_d.startElement("","","tagline", feedAttributes_d);
			transformerHandler_d.characters(description.toCharArray(),0,description.length());
			transformerHandler_d.endElement("","","tagline");
			transformerHandler_d.startElement("","","modified",elementAttributes_d);
			String dtString = getAsISO8601String(lastBuildDate);
			transformerHandler_d.characters(dtString.toCharArray(),0,dtString.length());
			transformerHandler_d.endElement("","","modified");
			feedAttributes_d.addAttribute("", "", "name", "", generatorName);
			transformerHandler_d.startElement("","","generator", feedAttributes_d);
			transformerHandler_d.characters(generatorURL.toCharArray(),0, generatorURL.length());
			transformerHandler_d.endElement("","","generator");
			feedAttributes_d.clear();
			transformerHandler_d.startElement("","","copyright", feedAttributes_d);
			transformerHandler_d.characters(copyright.toCharArray(),0,copyright.length());
			transformerHandler_d.endElement("","","copyright");
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

			transformerHandler_d.startElement("","","entry",elementAttributes_d);
			transformerHandler_d.startElement("","","title",elementAttributes_d);
			transformerHandler_d.characters(title.toCharArray(),0,title.length());
			transformerHandler_d.endElement("","","title");

			transformerHandler_d.startElement("","","issued",elementAttributes_d);
			String dtString = getAsISO8601String(date);
			transformerHandler_d.characters(dtString.toCharArray(),0,dtString.length());
			transformerHandler_d.endElement("","","issued");

			transformerHandler_d.startElement("","","modified",elementAttributes_d);
			transformerHandler_d.characters(dtString.toCharArray(),0,dtString.length());
			transformerHandler_d.endElement("","","modified");

			transformerHandler_d.startElement("","","created",elementAttributes_d);
			transformerHandler_d.characters(dtString.toCharArray(),0,dtString.length());
			transformerHandler_d.endElement("","","created");

			transformerHandler_d.startElement("","","link",elementAttributes_d);
			transformerHandler_d.characters(link.toCharArray(),0,link.length());
			transformerHandler_d.endElement("","","link");

			transformerHandler_d.startElement("","","id",elementAttributes_d);
			transformerHandler_d.characters(link.toCharArray(),0,link.length());
			transformerHandler_d.endElement("","","id");

			elementAttributes_d.clear();
			elementAttributes_d.addAttribute("", "", "type", "", "text/html");
			elementAttributes_d.addAttribute("", "", "mode", "", "escaped");
			elementAttributes_d.addAttribute("", "", "xml:lang", "", "en");

			transformerHandler_d.startElement("","","content",elementAttributes_d);
			transformerHandler_d.startCDATA();
			transformerHandler_d.characters(description.toCharArray(),0,description.length());
			transformerHandler_d.endCDATA();
			transformerHandler_d.endElement("","","content");

			transformerHandler_d.endElement("","","entry");
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
			transformerHandler_d.endElement("","","feed");
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
			AtomGenerator gen = new AtomGenerator();
			gen.init(new PrintWriter(System.out));
			gen.printHeader("Test feed", "http://something/", "test feed desc", "test@test.com", new Date());
			gen.printItem("Item title 1", "Item <b>desc</b> 1", "http://guid/1234", new Date());
			gen.printItem("Item title 2", "Item <b>desc</b> 2", "http://guid/12342", new Date());
			gen.close();
		}
		catch (FeedGenerationException e) {
			e.printStackTrace();
		}
	}
}
