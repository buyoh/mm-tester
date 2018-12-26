#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int main ()
{
    int N,M;
    cin >> N >> M;

    int Board[10][10];
    for (int r = 0; r < N; r++) {
        for (int c = 0; c < M; c++) {
            cin >> Board[r][c];
        }
    }

    cout << N * M << endl;
    for (int r = 0; r < N; r++) {
        for (int c = 0; c < M; c++) {
            cout << r << " " << c << endl;
        }
    }

    return 0;
}
