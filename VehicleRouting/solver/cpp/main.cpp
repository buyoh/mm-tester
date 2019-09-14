#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int main(int argc, char *argv[]) {

    int N,M; 
    cin >> N >> M;

    int depotX, depotY;
    vector < int > posX(N), posY(N);
    vector < int > cap(M), speed(M);

    cin >> depotX >> depotY;
    for (int i = 0; i < N; i++) cin >> posX[i] >> posY[i];
    for (int i = 0; i < M; i++) cin >> cap[i] >> speed[i];

    vector < vector < int > > ans;
    int t_idx = 0;
    while (N) {
        vector < int > truck = {t_idx, min(N, cap[t_idx])};
        for (int i = 0; i < truck[1]; i++) {
            truck.push_back(--N);
        }
        t_idx = (t_idx + 1) % M;
        ans.push_back(truck);
    }

    cout << ans.size() << endl;
    for (int i = 0; i < ans.size(); i++) {
        for (int j = 0; j < ans[i].size(); j++) {
            cout << ans[i][j] << (j < ans[i].size() - 1 ? " " : "\n");
        }
    }
    
    return 0;
    
}
