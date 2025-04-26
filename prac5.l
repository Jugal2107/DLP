%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_TOKENS 1000  

typedef struct {
    char type[20];
    char value[50];
} Token;

Token tokens[MAX_TOKENS];
int token_count = 0;

void add_token(const char* type, const char* value) {
    if (token_count < MAX_TOKENS) {
        strcpy(tokens[token_count].type, type);
        strcpy(tokens[token_count].value, value);
        token_count++;
    }
}

void print_tokens() {
    printf("\nTokens:\n");
    for (int i = 0; i < token_count; i++) {
        printf("%s: %s\n", tokens[i].type, tokens[i].value);
    }
}

%}

%option noyywrap

%%

"int"|"char"|"return"    { add_token("Keyword", yytext); }
[a-zA-Z_][a-zA-Z0-9_]*   { add_token("Identifier", yytext); }
[0-9]+                   { add_token("Constant", yytext); }
[0-9]+[a-zA-Z_]+         { /* Ignore */ }
'[^']*'                  { add_token("String", yytext); }
[{}();,]                 { add_token("Punctuation", yytext); }
[=+*/-]                  { add_token("Operator", yytext); }
"//".*                   { /* Ignore */ }
"/*"([^*]|\*+[^*/])*\*+"/"  { /* Ignore */ }
[ \t\n\r]+               { /* Ignore */ }
.                        { add_token("Lexical Error", yytext); }

%%

int main() {
    printf("Enter C code :\n");

    while (yylex()) { /* Keep scanning input */ }

    print_tokens();  

    printf("\nPress Enter to exit...\n");
    getchar();  
    return 0;
}
