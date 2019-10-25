#include <iostream>
#include <vector>
using namespace std;

int main () {

    int N; 
    cin >> N;

    vector < int > posX(N), posY(N);
    for (int i = 0; i < N; i++) {
        cin >> posX[i] >> posY[i];
    }
    
    cout << 1 << endl;
    cout << 500 << " " << 500 << endl;

    return 0;
}
