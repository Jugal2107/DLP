%{
#include <stdio.h>
#include <string.h>
int hasLower = 0, hasUpper = 0, hasDigit = 0, hasSymbol = 0, length = 0;
%}

%%

[a-z]      { hasLower = 1; length++; }
[A-Z]      { hasUpper = 1; length++; }
[0-9]      { hasDigit = 1; length++; }
[*#$@]     { hasSymbol = 1; length++; }
.          { length++; }
\n         { 
              if (length >= 9 && length <= 15 && hasLower && hasUpper && hasDigit && hasSymbol)
                  printf("Valid password\n");
              else
                  printf("Invalid password\n");

              hasLower = hasUpper = hasDigit = hasSymbol = length = 0;
           }

%%

int main() {
    yylex(); // Start lexical analysis
    return 0;
}

int yywrap() {
    return 1;
}
