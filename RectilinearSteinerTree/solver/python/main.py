import sys

posX = [];
posY = [];
used = set([])

N = int(input())
for i in range(N):
	x,y = map(int,input().split())
	posX.append(x)
	posY.append(y)
	used.add(x * 100 + y)

print(10000 - N)
for x in range(100):
	for y in range(100):
		p = x * 100 + y
		if p not in used:
			print(str(x) + " " + str(y))

sys.stdout.flush()
