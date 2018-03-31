#include <iostream>
#include <vector>
using namespace std;

int main(int argc, char *argv[]) {

    int N; 
    cin >> N;

    vector < int > h(N), w(N);
    for (int i = 0; i < N; i++) {
        cin >> h[i] >> w[i];
    }

    for (int i = 0; i < N; i++) {
        cout << 0 << " " << 5 * (i % 20) << " " << 5 * (i / 20) << endl;
    }

    return 0;
    
}
