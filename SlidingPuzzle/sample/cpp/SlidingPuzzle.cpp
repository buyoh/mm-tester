#include <iostream>
#include <vector>
using namespace std;

int main () {

    int N,M;
    cin >> N >> M;

    vector < vector < int > > Board = vector < vector < int > > (N, vector < int > (M));
    for (int x = 0; x < N; x++) {
    	for (int y = 0; y < M; y++) {
    		cin >> Board[x][y];
    	}
    }

    int ans = 0;
    cout << ans << endl;
    for (int i = 0; i < ans; i++) {
        cout << i << endl;
    }
}

