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

import java.util.HashMap;

/**
 * Date: 05-Sep-2003
 * Time: 20:41:19
 */
public
class FeedGeneratorFactory
{
	public static HashMap feedTypes_sd = new HashMap();

	public static final String FEED_TYPE_RSS = "rss";
	public static final String FEED_TYPE_ATOM = "atom";

	static {
		addGenerator(FEED_TYPE_RSS, RSSGenerator.class);
		addGenerator(FEED_TYPE_ATOM, AtomGenerator.class);
	}

	public static
	boolean
	supportsFormat(String type)
	{
		return type != null && (type.equals(FEED_TYPE_RSS) || type.equals(FEED_TYPE_ATOM));
	}

	public static
	void
	addGenerator(String type, Class cls)
	{
		feedTypes_sd.put(type, cls);
	}

	public static
	FeedGenerator
	getGeneratorForType(String type)
	{
		Class cls = (Class) feedTypes_sd.get(type);
		try {
			return (FeedGenerator) cls.newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
