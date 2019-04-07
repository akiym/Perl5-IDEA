/*
 * Copyright 2015-2018 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.codeInsight.typeInference.value;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlUnknownValue.UNKNOWN_VALUE;

/**
 * Represents a plain value - string or number
 */
public final class PerlStaticValue extends PerlValue {

  @NotNull
  private final String myValue;

  private PerlStaticValue(@NotNull String value) {
    myValue = value;
  }

  public PerlStaticValue(@NotNull StubInputStream dataStream) throws IOException {
    super(dataStream);
    myValue = Objects.requireNonNull(dataStream.readNameString());
  }

  @Override
  protected void serializeData(@NotNull StubOutputStream dataStream) throws IOException {
    dataStream.writeName(myValue);
  }

  @Override
  protected int getSerializationId() {
    return PerlValuesManager.STATIC_ID;
  }

  @NotNull
  public String getValue() {
    return myValue;
  }

  @NotNull
  @Override
  PerlStaticValue createBlessedCopy(@NotNull PerlValue bless) {
    return this;
  }

  @NotNull
  @Override
  protected Set<String> getSubNames(@NotNull Project project,
                                    @NotNull GlobalSearchScope searchScope,
                                    @Nullable Set<PerlValue> recursion) {
    return Collections.singleton(myValue);
  }

  @NotNull
  @Override
  protected Set<String> getNamespaceNames(@NotNull Project project,
                                          @NotNull GlobalSearchScope searchScope,
                                          @Nullable Set<PerlValue> recursion) {
    return Collections.singleton(myValue);
  }

  @Override
  public boolean canRepresentNamespace(@Nullable String namespaceName) {
    return myValue.equals(namespaceName);
  }

  @Override
  public boolean canRepresentSubName(@Nullable String subName) {
    return myValue.equals(subName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    PerlStaticValue aStatic = (PerlStaticValue)o;

    return myValue.equals(aStatic.myValue);
  }

  @Override
  protected int computeHashCode() {
    int result = super.computeHashCode();
    result = 31 * result + myValue.hashCode();
    return result;
  }

  @NotNull
  public static PerlValue create(@Nullable String value) {
    return value == null ? UNKNOWN_VALUE : PerlValuesManager.intern(new PerlStaticValue(value));
  }

  @Override
  public String toString() {
    return myValue;
  }
}