import sys

N = int(input())
posX = [];
posY = [];

for i in range(N):
	x,y = map(int,input().split())
	posX.append(x)
	posY.append(y)

print("1")
print("500 500")

sys.stdout.flush()
