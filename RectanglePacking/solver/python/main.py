import sys

N = int(input())
w = []
h = []

for i in range(N):
	wt,ht = map(int,input().split())
	w.append(wt)
	h.append(ht)

for i in range(N):
	print(str(100 * (i % 10)) + " " + str(100 * (i // 10)))

sys.stdout.flush()
