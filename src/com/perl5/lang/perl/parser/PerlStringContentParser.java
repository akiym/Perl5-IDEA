/*
 * Copyright 2016 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;

/**
 * Created by hurricup on 23.02.2016.
 */
public class PerlStringContentParser extends PerlParserImpl
{
	private final IElementType myStringType;

	public PerlStringContentParser(IElementType stringType)
	{
		myStringType = stringType;
	}

	@Override
	public boolean parseFileContents(PsiBuilder b, int l)
	{
		if (b.eof())
		{
			return true;
		}

		if (myStringType == HEREDOC)
		{
			return PerlParserImpl.sq_string_content(b, l);
		}
		else if (myStringType == HEREDOC_QQ)
		{
			return PerlParserImpl.qq_string_content(b, l);
		}
		else if (myStringType == HEREDOC_QX)
		{
			return PerlParserImpl.xq_string_content(b, l);
		}

		throw new RuntimeException("Don't know how to parse contents for " + myStringType);
	}
}