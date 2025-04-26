%{
#include <stdio.h>
#include <stdlib.h>

void yyerror(const char *s);
int yylex(void);
%}

%token IF THEN ELSE A B

%%
S   : IF E THEN S S1
    | A
    ;

S1  : ELSE S
    |
    ;

E   : B
    ;
%%

void yyerror(const char *s) {
    printf("Invalid string\n");
}

int main() {
    if (yyparse() == 0)
        printf("Valid string\n");
    return 0;
}
