import sys

N = int(input())
posX = [];
posY = [];

for i in range(N):
	x,y = map(int,input().split())
	posX.append(x)
	posY.append(y)

for i in range(N):
	print (i)

sys.stdout.flush()
