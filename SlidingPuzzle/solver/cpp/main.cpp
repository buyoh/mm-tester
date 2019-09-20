#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int main ()
{
    int N;
    cin >> N;

    int Board[10][10];
    for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
            cin >> Board[r][c];
        }
    }

    cout << N * N << endl;
    for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
            cout << r << " " << c << endl;
        }
    }

    return 0;
}
