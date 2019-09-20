import sys

N,M = map(int, input().split())
Board = []

for r in range(N):
	p = map(int, input().split())
	Board.append(p);

print(N * M)
for r in range(N):
	for c in range(M):
		print(str(r) + " " + str(c))

sys.stdout.flush()
