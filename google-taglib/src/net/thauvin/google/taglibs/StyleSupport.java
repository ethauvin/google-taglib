/*
 *  @(#)StyleSupport.java
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

import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * Implements support for the style, target and css tag attributes.
 *
 * @author Erik C. Thauvin
 * @created Sep 4, 2003
 * @version $Revision$
 * @since 1.0
 */
public abstract class StyleSupport extends BodyTagSupport
{
	/**
	 * The css attribute.
	 */
	protected String css = null;

	/**
	 * The style attribute.
	 */
	protected String style = null;

	/**
	 * The target attribute.
	 */
	protected String target = null;

	/**
	 * Sets the css attribute.
	 *
	 * @param css The new attribute value.
	 */
	public final void setCss(String css)
	{
		this.css = css;
	}

	/**
	 * Sets the style attribute.
	 *
	 * @param style The new attribute value.
	 */
	public final void setStyle(String style)
	{
		this.style = style;
	}

	/**
	 * Sets the target attribute.
	 *
	 * @param target The new attribute value.
	 */
	public final void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * Release method.
	 */
	public void release()
	{
		super.release();

		// Reset the values
		reset();
	}

	/**
	 * Reset the values.
	 */
	protected void reset()
	{
		// Reset the target, style and css
		target = style = css = null;
	}
}
