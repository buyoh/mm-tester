import sys

posX = [];
posY = [];

N,R = map(int,input().split())
for i in range(N):
	x,y = map(int,input().split())
	posX.append(x)
	posY.append(y)

print(N)
for i in range(N):
	print(str(posX[i]) + " " + str(posY[i]))

sys.stdout.flush()
