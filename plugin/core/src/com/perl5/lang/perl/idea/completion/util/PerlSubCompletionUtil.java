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

package com.perl5.lang.perl.idea.completion.util;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.perl5.lang.perl.extensions.packageprocessor.PerlExportDescriptor;
import com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlCallValue;
import com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValue;
import com.perl5.lang.perl.idea.completion.inserthandlers.SubSelectionHandler;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.util.PerlPackageUtil;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public class PerlSubCompletionUtil {
  public static final SubSelectionHandler SUB_SELECTION_HANDLER = new SubSelectionHandler();


  @NotNull
  public static LookupElementBuilder getSubDefinitionLookupElement(@NotNull PerlSubDefinitionElement subDefinition) {
    return getSubDefinitionLookupElement(subDefinition, null);
  }

  @NotNull
  public static LookupElementBuilder getSubDefinitionLookupElement(@NotNull PerlSubDefinitionElement subDefinition,
                                                                   @Nullable PerlExportDescriptor exportDescriptor) {
    return getSubDefinitionLookupElement(
      exportDescriptor == null ? subDefinition.getSubName() : exportDescriptor.getImportedName(),
      subDefinition);
  }

  @NotNull
  public static LookupElementBuilder getSubDefinitionLookupElement(@NotNull String subName,
                                                                   @NotNull PerlSubDefinitionElement subDefinition) {
    LookupElementBuilder newElement = LookupElementBuilder
      .create(subDefinition, subName)
      .withIcon(subDefinition.getIcon(0))
      .withStrikeoutness(subDefinition.isDeprecated())
      .withTypeText(subDefinition.getNamespaceName(), true);

    String argsString = subDefinition.getSubArgumentsListAsString();

    if (!argsString.isEmpty()) {
      newElement = newElement
        .withInsertHandler(SUB_SELECTION_HANDLER)
        .withTailText(argsString);
    }

    return newElement;
  }

  public static LookupElementBuilder getImportedEntityLookupElement(@NotNull PsiElement element,
                                                                    @NotNull PerlExportDescriptor exportDescriptor) {
    if (element instanceof PerlSubDefinitionElement) {
      return getSubDefinitionLookupElement((PerlSubDefinitionElement)element, exportDescriptor);
    }
    else if (element instanceof PerlSubDeclarationElement) {
      return getSubDeclarationLookupElement((PerlSubDeclarationElement)element, exportDescriptor);
    }
    else if (element instanceof PerlGlobVariable) {
      return getGlobLookupElement((PerlGlobVariable)element, exportDescriptor);
    }
    throw new RuntimeException("Don't know how to make lookup element for " + element.getClass());
  }

  @NotNull
  public static LookupElementBuilder getSubDeclarationLookupElement(@NotNull PerlSubDeclarationElement subDeclaration) {
    return getSubDeclarationLookupElement(subDeclaration, null);
  }

  @NotNull
  public static LookupElementBuilder getSubDeclarationLookupElement(@NotNull PerlSubDeclarationElement subDeclaration,
                                                                    @Nullable PerlExportDescriptor exportDescriptor) {
    return LookupElementBuilder
      .create(subDeclaration, subDeclaration.getSubName())
      .withIcon(subDeclaration.getIcon(0))
      .withStrikeoutness(subDeclaration.isDeprecated())
      .withInsertHandler(SUB_SELECTION_HANDLER)
      .withTypeText(subDeclaration.getNamespaceName(), true)
      ;
  }

  @NotNull
  public static LookupElementBuilder getGlobLookupElement(@NotNull PerlGlobVariable globVariable) {
    return getGlobLookupElement(globVariable, null);
  }

  /**
   * Probably duplicate of {@link PerlVariableCompletionUtil#createVariableLookupElement(com.perl5.lang.perl.psi.PerlGlobVariable)}
   */
  @NotNull
  public static LookupElementBuilder getGlobLookupElement(@NotNull PerlGlobVariable globVariable,
                                                          @Nullable PerlExportDescriptor exportDescriptor) {
    String lookupString = exportDescriptor == null ? globVariable.getName() : exportDescriptor.getImportedName();
    return LookupElementBuilder
      .create(globVariable, StringUtil.notNullize(lookupString))
      .withIcon(globVariable.getIcon(0))
      .withInsertHandler(SUB_SELECTION_HANDLER)
      .withTypeText(globVariable.getNamespaceName(), true)
      ;
  }

  public static void fillWithUnresolvedSubs(@NotNull PerlSubElement subDefinition, @NotNull CompletionResultSet resultSet) {
    final String packageName = subDefinition.getNamespaceName();
    if (packageName == null) {
      return;
    }

    final Set<String> namesSet = new THashSet<>();
    PsiFile containingFile = subDefinition.getContainingFile();
    containingFile.accept(new PerlRecursiveVisitor() {
      @Override
      protected boolean shouldVisitLightElements() {
        return true;
      }

      @Override
      public void visitMethod(@NotNull PsiPerlMethod method) {
        PerlCallValue methodValue = PerlCallValue.from(method);
        if (methodValue == null) {
          super.visitMethod(method);
          return;
        }

        PerlValue namespaceValue = methodValue.getNamespaceNameValue();

        if (namespaceValue.canRepresentNamespace(packageName)) {
          PerlSubNameElement subNameElement = method.getSubNameElement();

          if (subNameElement != null && subNameElement.isValid()) {

            String subName = subNameElement.getName();

            if (StringUtil.isNotEmpty(subName) && !namesSet.contains(subName)) {
              PsiReference[] references = subNameElement.getReferences();
              if (references.length == 0) {
                super.visitMethod(method);
                return;
              }

              for (PsiReference reference : references) {
                if (reference.resolve() != null) {
                  super.visitMethod(method);
                  return;
                }
              }
              // unresolved
              namesSet.add(subName);
              resultSet.addElement(LookupElementBuilder.create(subName));
            }
          }
        }
        super.visitMethod(method);
      }
    });
  }

  public static void fillWithNotOverridedSubs(@NotNull PerlSubElement subDefinition, @NotNull CompletionResultSet resultSet) {
    PerlPackageUtil.processNotOverridedMethods(
      PsiTreeUtil.getParentOfType(subDefinition, PerlNamespaceDefinitionElement.class),
      subDefinitionBase ->
      {
        resultSet.addElement(LookupElementBuilder.create(subDefinitionBase, subDefinitionBase.getSubName()));
        return true;
      }
    );
  }
}
