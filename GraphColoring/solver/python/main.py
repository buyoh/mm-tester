import sys

N,M = map(int,input().split())
a = [];
b = [];

for i in range(M):
	at,bt = map(int,input().split())
	a.append(at)
	b.append(bt)

for i in range(N):
    print(i)

sys.stdout.flush()
