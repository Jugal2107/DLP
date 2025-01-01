#include <stdio.h>
#include <string.h>

#define MAX_STATES 10
#define MAX_SYMBOLS 10
#define MAX_STRING_LENGTH 100

int getSymbolIndex(char symbol, char symbols[], int numSymbols) {
    for (int i = 0; i < numSymbols; i++) {
        if (symbols[i] == symbol) {
            return i;
        }
    }
    return -1;
}

int main() {
    int numStates, numSymbols, numAcceptStates;
    int startState, acceptingStates[MAX_STATES];
    char symbols[MAX_SYMBOLS];
    int transition[MAX_STATES][MAX_SYMBOLS];
    char inputString[MAX_STRING_LENGTH];


    printf("Number of input symbols: ");
    scanf("%d", &numSymbols);

    printf("Input symbols: ");
    for (int i = 0; i < numSymbols; i++) {
        scanf(" %c", &symbols[i]);
    }

    printf("Enter number of states: ");
    scanf("%d", &numStates);
    printf("Initial state: ");
    scanf("%d", &startState);
    printf("Number of accepting states: ");
    scanf("%d", &numAcceptStates);
    printf("Accepting states: ");

    for (int i = 0; i < numAcceptStates; i++) {
        scanf("%d", &acceptingStates[i]);
    }
    printf("Transition table:\n");
    for (int i = 0; i < numStates; i++) {
        for (int j = 0; j < numSymbols; j++) {
            printf("From state %d on symbol '%c' -> Next state: ", i, symbols[j]);
            scanf("%d", &transition[i][j]);
        }
    }
    printf("Input string: ");
    scanf("%s", inputString);
    int currentState = startState;
    int isValid = 1;
    for (int i = 0; i < strlen(inputString); i++) {
        int symbolIndex = getSymbolIndex(inputString[i], symbols, numSymbols);
        if (symbolIndex == -1) {
            isValid = 0;
            break;
        }
        currentState = transition[currentState][symbolIndex];
    }
    int isAcceptingState = 0;
    for (int i = 0; i < numAcceptStates; i++) {
        if (currentState == acceptingStates[i]) {
            isAcceptingState = 1;
            break;
        }
    }
    if (isValid && isAcceptingState) {
        printf("Valid string\n");
    } else {
        printf("Invalid string\n");
    }
    return 0;
}
