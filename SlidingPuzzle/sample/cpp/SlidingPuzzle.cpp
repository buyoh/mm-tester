#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int N,M;
int Board[30][30];
int Board_t[30][30];

int Score;
int bposX;
int bposY;
vector < pair < int,int > > ans;

struct XorShift {
    unsigned int x;
    XorShift () : x(2463534242U) {}
    unsigned int rand() {
        x ^= (x << 13);
        x ^= (x >> 17);
        x ^= (x << 5);
        return x;
    }
} Random;

int get_score ()
{
    int ret = 0;
    for (int x = 0; x < N; x++) {
        for (int y = 0; y < M; y++) {
            int num = x * M + y + 1;
            ret += abs(num - Board[x][y]);
        }
    }
    return ret;
}

bool accept (double diff, double temp) 
{
    if (diff >= 0) return true;
    double P = exp(diff / temp) * 4294967295.0;
    return Random.rand() < P;
}

int get_nxt_board (int x, int y)
{
    if (x == bposX && y != bposY) {
        if (y < bposY) {
            for (int i = bposY; i > y; i--) {
                Board[x][i] = Board[x][i - 1];
            }
        } else {
            for (int i = bposY; i < y; i++) {
                Board[x][i] = Board[x][i + 1];
            }
        }
        Board[x][y] = -1;
        return get_score();
    }
    if (x != bposX && y == bposY) {
        if (x < bposX) {
            for (int i = bposX; i > x; i--) {
                Board[i][y] = Board[i - 1][y];
            }
        } else {
            for (int i = bposX; i < x; i++) {
                Board[i][y] = Board[i + 1][y];
            }
        }
        Board[x][y] = -1;
        return get_score();
    }
    return 1e9;
}

void solve ()
{
    for (int i = 0; i < 3000000; i++) {
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                Board_t[x][y] = Board[x][y];
            }
        }
        int x = Random.rand() % N;
        int y = Random.rand() % M;
        int nxt = get_nxt_board(x, y);
        if (accept((double)(Score - nxt), 40.0) && nxt != 1e9) {
            Score = nxt;
            bposX = x;
            bposY = y;
            ans.push_back(make_pair(x, y));
        } else {
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < M; y++) {
                    Board[x][y] = Board_t[x][y];
                }
            }    
        }
    }
}

void intialize ()
{
    Score = get_score();
    for (int x = 0; x < N; x++) {
        for (int y = 0; y < M; y++) {
            if (Board[x][y] == -1) {
                bposX = x;
                bposY = y;
            }
        }
    }
}

int main () {

    cin >> N >> M;
    for (int x = 0; x < N; x++) {
    	for (int y = 0; y < M; y++) {
    		cin >> Board[x][y];
    	}
    }

    intialize();
    solve();

    cout << ans.size() << endl;
    for (int i = 0; i < (int)ans.size(); i++) {
        cout << ans[i].first << " " << ans[i].second << endl;
    }
}

