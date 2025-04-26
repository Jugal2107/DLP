%{
#include "y.tab.h"
%}

%%
i      { return IF; }
t      { return THEN; }
e      { return ELSE; }
a      { return A; }
b      { return B; }
\n     { return 0; }
.      { return yytext[0]; }
%%
