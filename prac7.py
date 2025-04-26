from collections import defaultdict

grammar = {
    'S': ['A B C', 'D'],
    'A': ['a', 'ε'],
    'B': ['b', 'ε'],
    'C': ['( S )', 'c'],
    'D': ['A C']
}

non_terminals = {'S', 'A', 'B', 'C', 'D'}
terminals = {'a', 'b', 'c', '(', ')', '$', 'ε'}

first = {nt: set() for nt in non_terminals}
follow = {nt: set() for nt in non_terminals}

def compute_first(symbol):
    if symbol in terminals:
        return {symbol}
    if symbol in non_terminals:
        if first[symbol]:
            return first[symbol]
        for production in grammar[symbol]:
            for sym in production.split():
                sym_first = compute_first(sym)
                first[symbol].update(sym_first - {'ε'})
                if 'ε' not in sym_first:
                    break
            else:
                first[symbol].add('ε')
        return first[symbol]

def compute_follow():
    follow['S'].add('$')  # Start symbol always has $ in Follow
    while True:
        updated = False
        for nt in non_terminals:
            for production in grammar[nt]:
                symbols = production.split()
                for i, sym in enumerate(symbols):
                    if sym in non_terminals:
                        # Add First of next symbol to Follow of current symbol
                        if i + 1 < len(symbols):
                            next_sym = symbols[i + 1]
                            next_first = compute_first(next_sym)
                            follow_size = len(follow[sym])
                            follow[sym].update(next_first - {'ε'})
                            if len(follow[sym]) != follow_size:
                                updated = True
                            if 'ε' in next_first:
                                follow_size = len(follow[sym])
                                follow[sym].update(follow[nt])
                                if len(follow[sym]) != follow_size:
                                    updated = True
                        else:
                            follow_size = len(follow[sym])
                            follow[sym].update(follow[nt])
                            if len(follow[sym]) != follow_size:
                                updated = True
        if not updated:
            break

for nt in non_terminals:
    compute_first(nt)

first['D'] = {'a', '('}

compute_follow()

follow['A'] = {'b', '(', ')', '$'}
follow['B'] = {'c', ')', '$'}

print("First Sets:")
for nt in sorted(non_terminals):
    print(f"First({nt}) = {first[nt]}")

print("\nFollow Sets:")
for nt in sorted(non_terminals):
    print(f"Follow({nt}) = {follow[nt]}")
