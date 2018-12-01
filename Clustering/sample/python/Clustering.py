import sys

N,K = map(int, raw_input().split())
x = []
y = []

for i in range(N):
	p = map(int,raw_input().split())
	x.append(p[0])
	y.append(p[1])

for i in range(K):
	rx = i / 4 * 200 + 100
	ry = i % 4 * 250 + 100
	print str(rx) + " " + str(ry)

sys.stdout.flush()
