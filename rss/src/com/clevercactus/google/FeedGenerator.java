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

import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * A class that uses JAXP and SAX to print an feed to a PrintWriter.
 * Output of the actual happens as data is added on the subclass. See the main() method for a
 * simple usage example.
 *
 * @version 1.0, 29/08/2003
 * @author Diego Doval
 */
public abstract
class FeedGenerator
{
	protected Writer output_d;
	protected StreamResult streamResult_d;
	protected TransformerHandler transformerHandler_d;
	protected SAXTransformerFactory saxTransformerFactory_d;
	protected AttributesImpl elementAttributes_d;
	protected AttributesImpl feedAttributes_d;

	public static SimpleDateFormat iso8601DateFormat_sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	public static SimpleDateFormat rfc822DateFormat_sd = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yy' 'HH:mm:ss' 'z", Locale.US);

	/**
	 * The one and only constructor.
	 */
	public
	FeedGenerator()
	{
	}

	/**
	 * @param output writer to use for outputting the SearchResults
	 * @throws FeedGenerationException
	 */
	public
	void
	init(Writer output)
		throws FeedGenerationException
	{
		output_d = output;
		streamResult_d = new StreamResult(output_d);
		feedAttributes_d = new AttributesImpl();
		elementAttributes_d = new AttributesImpl();
		try {
			saxTransformerFactory_d = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			transformerHandler_d = saxTransformerFactory_d.newTransformerHandler();
			Transformer trans = transformerHandler_d.getTransformer();
			trans.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
			trans.setOutputProperty(OutputKeys.INDENT,"yes");
			transformerHandler_d.setResult(streamResult_d);
			transformerHandler_d.startDocument();
		}
		catch (Exception e) {
			throw new FeedGenerationException(e);
		}
	}

	/**
	 * Prints a valid header based on the information passed.
	 *
	 * @param title feed title
	 * @param link feed link
	 * @param description feed description
	 * @param author feed author (must be an email address)
	 * @param lastBuildDate the date the feed was built
	 * @throws FeedGenerationException
	 */
	public abstract
	void
	printHeader(String title, String link, String description, String author, Date lastBuildDate)
		throws FeedGenerationException;

	/**
	 * Prints a valid SearchResults 2.0 item based on the information passed.
	 *
	 * @param title the entry title
	 * @param description the entry body/description
	 * @param link the entry's GUID
	 * @param date the entry publication date
	 * @throws FeedGenerationException
	 */
	public abstract
	void
	printItem(String title, String description, String link, Date date)
		throws FeedGenerationException;

  /**
	 * Closes the feed's main elements
	 * @throws FeedGenerationException
	 */
	public abstract
	void
	close()
		throws FeedGenerationException;

	/**
	 * Utility method to obtain a date in a string in RFC 822 date format
	 *
	 * @param date the date to
	 * @return a string with the date in RFC 822 format
	 */
	public final
	String
	getAsISO8601String(Date date)
	{
		String result = iso8601DateFormat_sd.format(date);
		//convert 20030509T00:32:35+0100 returned by Java into 20030509T00:32:35+01:00
		//from the spec <dateTime.iso8601>	date/time	19980717T14:08:55
		result = result.substring(0, result.length()-2) + ":" + result.substring(result.length()-2);
		return result;
	}


	/**
	 * Utility method to obtain a date in a string in RFC 822 date format
	 *
	 * @param date the date to
	 * @return a string with the date in RFC 822 format
	 */
	public final
	String
	getAsRFC822String(Date date)
	{
		return rfc822DateFormat_sd.format(date);
	}
}
