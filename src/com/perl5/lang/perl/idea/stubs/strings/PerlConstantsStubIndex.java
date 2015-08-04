/*
 * Copyright 2015 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.stubs.strings;

import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import com.perl5.lang.perl.psi.PerlString;
import com.perl5.lang.perl.psi.impl.PerlStringContentElementImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 03.08.2015.
 */
public class PerlConstantsStubIndex extends StringStubIndexExtension<PerlString>
{
	public static final int VERSION = 2;
	public static final StubIndexKey<String, PerlString> KEY = StubIndexKey.createIndexKey("perl.constant");

	@Override
	public int getVersion()
	{
		return super.getVersion() + VERSION;
	}

	@NotNull
	@Override
	public StubIndexKey<String, PerlString> getKey()
	{
		return KEY;
	}
}
