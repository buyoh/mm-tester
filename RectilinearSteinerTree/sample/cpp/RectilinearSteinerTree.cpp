#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int main ()
{
    int N;
    cin >> N;

    set < pair < int,int > > used;
    for (int i = 0; i < N; i++) {
        int x,y;
        cin >> x >> y;
        used.insert(make_pair(x, y));
    }

    const int size = 100;
    cout << size * size - N << endl;
    for (int x = 0; x < size; x++) {
        for (int y = 0; y < size; y++) {
            if (used.find(make_pair(x, y)) == used.end()) {
                cout << x << " " << y << endl;
            }
        }
    }

    return 0;
}
