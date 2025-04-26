%{
#include <stdio.h>
%}

%%
charusat    { printf("university"); }
.|\n        { printf("%s", yytext); }

%%

int main() {
    yylex(); // Start lexical analysis
    return 0;
}

int yywrap() {
    return 1;
}
