#include <iostream>
#include <vector>
using namespace std;

int main () {

    int N,R;
    cin >> N >> R;
    
    vector < int > x(N),y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    cout << N << endl;
    for (int i = 0; i < N; i++) {
        cout << x[i] << " " << y[i] << endl;
    }
}
