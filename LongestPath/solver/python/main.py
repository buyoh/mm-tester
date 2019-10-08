import sys

N,M = map(int, input().split())
x = []
y = []
a = []
b = []

for i in range(N):
	xt,yt = map(int,input().split())
	x.append(xt)
	y.append(yt)

for i in range(M):
	at,bt = map(int,input().split())
	a.append(at)
	b.append(bt)

K = 2
print(K)
print(a[0])
print(b[0])

sys.stdout.flush()
