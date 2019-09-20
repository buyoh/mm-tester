import sys

N = int(input())
Board = []

for r in range(N):
	p = map(int, input().split())
	Board.append(p);

print(N * N)
for r in range(N):
	for c in range(N):
		print(str(r) + " " + str(c))

sys.stdout.flush()
