import sys
from sets import Set

posX = [];
posY = [];
used = Set([])

N = int(raw_input())
for i in range(N):
	p = map(int,raw_input().split())
	posX.append(p[0])
	posY.append(p[1])
	used.add(p[0] * 100 + p[1])

print (10000 - N)
for x in range(100):
	for y in range(100):
		p = x * 100 + y
		if p not in used:
			print str(x) + " " + str(y)

sys.stdout.flush()
