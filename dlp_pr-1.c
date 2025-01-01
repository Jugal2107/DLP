
#include <stdio.h>
#include <string.h>
int main() {
   char str[8] ;
   scanf("%s",str);
   if(strlen(str) == 2)
   {
   if(str[0] == 'b' && str[1] == 'b') printf("valid");
   else printf("invalid");
   }
   if(strlen(str) > 2 && strlen(str) < 8)
   {
       int i;
       for(i =0; i<strlen(str)-2 ; i++)
       {
           if(str[i] == 'a') continue;
           else
           {printf("Invalid");
           return 0; }
       }
       if(str[i] == 'b' && str[i+1] == 'b') printf("valid");
       else printf("invalid");
   }
   return 0;
}
