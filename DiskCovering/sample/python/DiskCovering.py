import sys
from sets import Set

posX = [];
posY = [];

N,R = map(int,raw_input().split())
for i in range(N):
	p = map(int,raw_input().split())
	posX.append(p[0])
	posY.append(p[1])

print (N)
for i in range(N):
	print str(posX[i]) + " " + str(posY[i])

sys.stdout.flush()
