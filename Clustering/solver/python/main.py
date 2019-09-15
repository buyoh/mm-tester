import sys

N,K = map(int, input().split())
x = []
y = []

for i in range(N):
	xt,yt = map(int,input().split())
	x.append(xt)
	y.append(yt)

for i in range(K):
	rx = i // 4 * 200 + 100
	ry = i % 4 * 250 + 100
	print(str(rx) + " " + str(ry))

sys.stdout.flush()
