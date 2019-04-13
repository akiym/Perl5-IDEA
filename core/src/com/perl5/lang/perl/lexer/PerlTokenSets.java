/*
 * Copyright 2015-2017 Alexandr Evstigneev
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

package com.perl5.lang.perl.lexer;

import com.intellij.psi.tree.TokenSet;
import com.perl5.lang.perl.parser.PerlParserUtil;
import com.perl5.lang.perl.parser.moose.MooseElementTypes;

import static com.perl5.lang.perl.parser.MooseParserExtension.MOOSE_RESERVED_TOKENSET;
import static com.perl5.lang.perl.parser.PerlParserUtil.CLOSE_QUOTES;

/**
 * Created by hurricup on 23.10.2016.
 */
public interface PerlTokenSets extends PerlElementTypes, MooseElementTypes {
  /**
   * Quote openers with three or four quotes
   */
  TokenSet COMPLEX_QUOTE_OPENERS = TokenSet.create(
    RESERVED_S,
    RESERVED_TR,
    RESERVED_Y
  );
  TokenSet SIMPLE_QUOTE_OPENERS = TokenSet.create(
    RESERVED_Q,
    RESERVED_QQ,
    RESERVED_QX,
    RESERVED_QW,
    RESERVED_QR,
    RESERVED_M
  );
  TokenSet ALL_QUOTE_OPENERS = TokenSet.orSet(SIMPLE_QUOTE_OPENERS, COMPLEX_QUOTE_OPENERS);

  TokenSet OPERATORS_TOKENSET = TokenSet.create(
    OPERATOR_CMP_NUMERIC,
    OPERATOR_LT_NUMERIC,
    OPERATOR_GT_NUMERIC,

    OPERATOR_CMP_STR,
    OPERATOR_LE_STR,
    OPERATOR_GE_STR,
    OPERATOR_EQ_STR,
    OPERATOR_NE_STR,
    OPERATOR_LT_STR,
    OPERATOR_GT_STR,

    OPERATOR_HELLIP,
    OPERATOR_FLIP_FLOP,
    OPERATOR_CONCAT,

    OPERATOR_PLUS_PLUS,
    OPERATOR_MINUS_MINUS,
    OPERATOR_POW,

    OPERATOR_RE,
    OPERATOR_NOT_RE,

    //			OPERATOR_HEREDOC, // this is an artificial operator, not the real one; fixme uncommenting breaks parsing of print $of <<EOM
    OPERATOR_SHIFT_LEFT,
    OPERATOR_SHIFT_RIGHT,

    OPERATOR_AND,
    OPERATOR_OR,
    OPERATOR_OR_DEFINED,
    OPERATOR_NOT,

    OPERATOR_ASSIGN,

    QUESTION,
    COLON,

    OPERATOR_REFERENCE,

    OPERATOR_DIV,
    OPERATOR_MUL,
    OPERATOR_MOD,
    OPERATOR_PLUS,
    OPERATOR_MINUS,

    OPERATOR_BITWISE_NOT,
    OPERATOR_BITWISE_AND,
    OPERATOR_BITWISE_OR,
    OPERATOR_BITWISE_XOR,

    OPERATOR_AND_LP,
    OPERATOR_OR_LP,
    OPERATOR_XOR_LP,
    OPERATOR_NOT_LP,

    COMMA,
    FAT_COMMA,

    OPERATOR_DEREFERENCE,

    OPERATOR_X,
    OPERATOR_FILETEST,

    // syntax operators
    OPERATOR_POW_ASSIGN,
    OPERATOR_PLUS_ASSIGN,
    OPERATOR_MINUS_ASSIGN,
    OPERATOR_MUL_ASSIGN,
    OPERATOR_DIV_ASSIGN,
    OPERATOR_MOD_ASSIGN,
    OPERATOR_CONCAT_ASSIGN,
    OPERATOR_X_ASSIGN,
    OPERATOR_BITWISE_AND_ASSIGN,
    OPERATOR_BITWISE_OR_ASSIGN,
    OPERATOR_BITWISE_XOR_ASSIGN,
    OPERATOR_SHIFT_LEFT_ASSIGN,
    OPERATOR_SHIFT_RIGHT_ASSIGN,
    OPERATOR_AND_ASSIGN,
    OPERATOR_OR_ASSIGN,
    OPERATOR_OR_DEFINED_ASSIGN,

    OPERATOR_GE_NUMERIC,
    OPERATOR_LE_NUMERIC,
    OPERATOR_EQ_NUMERIC,
    OPERATOR_NE_NUMERIC,
    OPERATOR_SMARTMATCH
  );

  TokenSet CUSTOM_EXPR_KEYWORDS = TokenSet.create(
    RESERVED_GREP,
    RESERVED_MAP,
    RESERVED_SORT,

    RESERVED_SAY,
    RESERVED_PRINT,
    RESERVED_PRINTF,

    RESERVED_USE,
    RESERVED_NO,
    RESERVED_REQUIRE,

    RESERVED_UNDEF,
    RESERVED_RETURN,

    RESERVED_SCALAR,
    RESERVED_KEYS,
    RESERVED_VALUES,
    RESERVED_EACH,
    RESERVED_DEFINED,
    RESERVED_WANTARRAY
  );

  TokenSet MODIFIERS_TOKENSET = TokenSet.create(
    RESERVED_IF,
    RESERVED_UNTIL,
    RESERVED_UNLESS,
    RESERVED_FOR,
    RESERVED_FOREACH,
    RESERVED_WHEN,
    RESERVED_WHILE
  );

  TokenSet DEFAULT_KEYWORDS_TOKENSET = TokenSet.orSet(
    ALL_QUOTE_OPENERS,
    CUSTOM_EXPR_KEYWORDS,
    MODIFIERS_TOKENSET,
    TokenSet.create(
      RESERVED_MY,
      RESERVED_OUR,
      RESERVED_STATE,
      RESERVED_LOCAL,
      RESERVED_ELSIF,
      RESERVED_ELSE,
      RESERVED_GIVEN,
      RESERVED_DEFAULT,
      RESERVED_CONTINUE,
      RESERVED_FORMAT,
      RESERVED_SUB,
      RESERVED_PACKAGE,
      RESERVED_DO,
      RESERVED_EVAL,
      RESERVED_GOTO,
      RESERVED_REDO,
      RESERVED_NEXT,
      RESERVED_LAST,
      RESERVED_EXIT
    ));

  TokenSet TRY_CATCH_KEYWORDS_TOKENSET = TokenSet.create(
    RESERVED_TRY,
    RESERVED_CATCH,
    RESERVED_FINALLY,
    RESERVED_CATCH_WITH,
    RESERVED_EXCEPT,
    RESERVED_OTHERWISE,
    RESERVED_CONTINUATION
  );

  TokenSet METHOD_SIGNATURES_KEYWORDS_TOKENSET = TokenSet.create(
    RESERVED_METHOD,
    RESERVED_FUNC
  );

  TokenSet KEYWORDS_TOKENSET = TokenSet.orSet(
    DEFAULT_KEYWORDS_TOKENSET,
    MOOSE_RESERVED_TOKENSET,
    METHOD_SIGNATURES_KEYWORDS_TOKENSET,
    TRY_CATCH_KEYWORDS_TOKENSET
  );

  TokenSet ANNOTATIONS_KEYS = TokenSet.create(
    ANNOTATION_DEPRECATED_KEY,
    ANNOTATION_RETURNS_KEY,
    ANNOTATION_OVERRIDE_KEY,
    ANNOTATION_METHOD_KEY,
    ANNOTATION_ABSTRACT_KEY,
    ANNOTATION_INJECT_KEY,
    ANNOTATION_NOINSPECTION_KEY,
    ANNOTATION_TYPE_KEY

  );

  TokenSet STRING_CONTENT_TOKENSET = TokenSet.create(
    STRING_CONTENT,
    STRING_CONTENT_XQ,
    STRING_CONTENT_QQ
  );

  TokenSet HEREDOC_BODIES_TOKENSET = TokenSet.create(
    HEREDOC,
    HEREDOC_QQ,
    HEREDOC_QX
  );


  TokenSet QUOTE_MIDDLE = TokenSet.create(REGEX_QUOTE, REGEX_QUOTE_E);

  TokenSet REGEX_QUOTE_OPEN = TokenSet.create(PerlElementTypesGenerated.REGEX_QUOTE_OPEN, REGEX_QUOTE_OPEN_E);
  TokenSet QUOTE_OPEN_ANY = TokenSet.orSet(
    REGEX_QUOTE_OPEN,
    PerlParserUtil.OPEN_QUOTES,
    QUOTE_MIDDLE
  );

  TokenSet QUOTE_CLOSE_FIRST_ANY = TokenSet.orSet(
    TokenSet.create(REGEX_QUOTE_CLOSE),
    QUOTE_MIDDLE,
    CLOSE_QUOTES
  );

  TokenSet QUOTE_CLOSE_PAIRED = TokenSet.orSet(
    CLOSE_QUOTES,
    TokenSet.create(REGEX_QUOTE_CLOSE)
  );

  TokenSet SIGILS = TokenSet.create(
    SIGIL_SCALAR, SIGIL_ARRAY, SIGIL_HASH, SIGIL_GLOB, SIGIL_CODE, SIGIL_SCALAR_INDEX
  );

  TokenSet STATEMENTS = TokenSet.create(
    STATEMENT, USE_STATEMENT, NO_STATEMENT
  );

  TokenSet LAZY_CODE_BLOCKS = TokenSet.create(LP_CODE_BLOCK, LP_CODE_BLOCK_WITH_TRYCATCH);

  TokenSet LAZY_PARSABLE_REGEXPS = TokenSet.create(
    LP_REGEX_REPLACEMENT,
    LP_REGEX,
    LP_REGEX_X,
    LP_REGEX_XX
  );

  TokenSet LAZY_PARSABLE_STRINGS = TokenSet.create(
    LP_STRING_Q,
    LP_STRING_QQ,
    LP_STRING_XQ

  );

  TokenSet HEREDOC_ENDS = TokenSet.create(HEREDOC_END, HEREDOC_END_INDENTABLE);

  TokenSet LOOP_CONTROL_KEYWORDS = TokenSet.create(
    RESERVED_NEXT, RESERVED_LAST, RESERVED_REDO
  );

  TokenSet CAST_EXPRESSIONS = TokenSet.create(
    ARRAY_CAST_EXPR, CODE_CAST_EXPR, GLOB_CAST_EXPR, HASH_CAST_EXPR, SCALAR_INDEX_CAST_EXPR, SCALAR_CAST_EXPR);

  TokenSet SLICES = TokenSet.create(HASH_SLICE, ARRAY_SLICE);

  TokenSet REGEX_OPERATIONS = TokenSet.create(REPLACEMENT_REGEX, COMPILE_REGEX, MATCH_REGEX);

  TokenSet VARIABLES = TokenSet.create(SCALAR_VARIABLE, ARRAY_VARIABLE, HASH_VARIABLE, CODE_VARIABLE, GLOB_VARIABLE, ARRAY_INDEX_VARIABLE);

  TokenSet STRINGS = TokenSet.create(STRING_BARE, STRING_DQ, STRING_XQ, STRING_SQ);

  TokenSet CALLS = TokenSet.create(SUB_CALL_EXPR, NESTED_CALL);
  TokenSet VARIABLE_DECLARATIONS = TokenSet.create(
    VARIABLE_DECLARATION_GLOBAL,
    VARIABLE_DECLARATION_LEXICAL,
    VARIABLE_DECLARATION_LOCAL
  );
}
