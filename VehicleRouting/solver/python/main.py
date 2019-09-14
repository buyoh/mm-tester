import sys

N,M = map(int,input().split())
depotX,depotY = map(int,input().split())

posX = [];
posY = [];
for i in range(N):
	x,y = map(int,input().split())
	posX.append(x)
	posY.append(y)

cap = []
speed = []
for i in range(M):
	c,s = map(int,input().split())
	cap.append(c)
	speed.append(s)

T = 0
K = (N // cap[T]) + (1 if N % cap[T] > 0 else 0)
ans = [[0 for i in range(0)] for j in range(K)]

for i in range(K):
	if N == 0:
		break
	L = min(N, cap[T])
	ans[i].append(T);
	ans[i].append(L);
	for j in range(L):
		N = N - 1
		ans[i].append(N)

print(K)
for i in range(K):
	for j in ans[i]:
		print(j)

sys.stdout.flush()
