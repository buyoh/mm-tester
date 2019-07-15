#include <iostream>
#include <vector>
using namespace std;

int main () {

    int N,K;
    cin >> N >> K;
    
    vector < int > x(N),y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    for (int i = 0; i < K; i++) {
        cout << i / 4 * 200 + 100 << " " << i % 4 * 250 + 100 << endl;
    }
}
