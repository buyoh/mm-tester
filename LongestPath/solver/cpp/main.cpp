#include <iostream>
#include <vector>
using namespace std;

int main ()
{
    int N,M;
    cin >> N >> M;

    vector < int > x(N),y(N);
    for (int i = 0; i < N; i++) {
        cin >> x[i] >> y[i];
    }

    vector < int > a(M),b(M);
    for (int i = 0; i < M; i++) {
        cin >> a[i] >> b[i];
    }

    int K = 2;
    cout << K << endl;
    cout << a[0] << endl;
    cout << b[0] << endl;
}
