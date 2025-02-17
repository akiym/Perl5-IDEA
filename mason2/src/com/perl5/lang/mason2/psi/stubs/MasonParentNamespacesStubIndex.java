/*
 * Copyright 2015-2019 Alexandr Evstigneev
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

package com.perl5.lang.mason2.psi.stubs;

import com.intellij.psi.stubs.StubIndexKey;
import com.perl5.lang.mason2.psi.MasonNamespaceDefinition;
import com.perl5.lang.perl.psi.stubs.PerlStubIndexBase;
import org.jetbrains.annotations.NotNull;


public class MasonParentNamespacesStubIndex extends PerlStubIndexBase<MasonNamespaceDefinition> {
  public static final int VERSION = 1;
  public static final StubIndexKey<String, MasonNamespaceDefinition> KEY = StubIndexKey.createIndexKey("perl.mason2.namespace.parent");

  @Override
  public int getVersion() {
    return super.getVersion() + VERSION;
  }

  @NotNull
  @Override
  public StubIndexKey<String, MasonNamespaceDefinition> getKey() {
    return KEY;
  }
}