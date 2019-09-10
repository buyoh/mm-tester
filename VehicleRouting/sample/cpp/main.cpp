#include <iostream>
#include <vector>
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
    vector < int > ans_t;
    while (N--) {
        if (ans_t.size() == 0) {
            ans_t.push_back(0);
            ans_t.push_back(cap[0]);
        }
        ans_t.push_back(N);
        if (ans_t.size() == cap[0] + 2) {
            ans.push_back(ans_t);
            ans_t.clear();
        } else if (N == 0) {
            ans_t[1] = ans_t.size() - 2;
            ans.push_back(ans_t);
        }
    }

    cout << ans.size() << endl;
    for (int i = 0; i < ans.size(); i++) {
        for (int j = 0; j < ans[i].size(); j++) {
            cout << ans[i][j] << (j < ans[i].size() - 1 ? " " : "\n");
            cerr << ans[i][j] << (j < ans[i].size() - 1 ? " " : "\n");
        }
    }
    
    return 0;
    
}
