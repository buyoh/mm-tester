#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

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

struct Timer {
    double CYCLE_PER_SEC = 2800000000.0;
    unsigned long long BEGIN_CYCLE = 0;
    Timer() {}
    unsigned long long int getCycle() {
        unsigned int low, high;
        __asm__ volatile ("rdtsc" : "=a" (low), "=d" (high));
        return ((unsigned long long int)low) | ((unsigned long long int)high << 32);
    }
    void start(void) {
        BEGIN_CYCLE = getCycle();
    }
    double get_time(void) {
        return (double)(getCycle() - BEGIN_CYCLE) / CYCLE_PER_SEC;
    }
} timer;

/*-------------------------------------------------------------------------------------------------*/

const double TIME_LIMIT = 10.0;
const double TEMP_START = 5.0;
const double TEMP_END   = 0.001;
const double TEMP_DIFF  = (TEMP_START - TEMP_END) / TIME_LIMIT;

int N,M;
int oriBoard[30][30];
int Board[30][30];

int Score;
int ori_bposX;
int ori_bposY;
int bposX;
int bposY;

const int AnsNum = 500;
int AnsX[AnsNum];
int AnsY[AnsNum];

int BestScore;
int BestAnsX[AnsNum];
int BestAnsY[AnsNum];

/*-------------------------------------------------------------------------------------------------*/

int get_score ()
{
    int ret = 0;
    for (int x = 0; x < N; x++) {
        for (int y = 0; y < M; y++) {
            if (Board[x][y] >= 0) {
                int nx = (Board[x][y] - 1) / M;
                int ny = (Board[x][y] - 1) % M;
                ret += abs(x - nx) + abs(y - ny);
            }
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

bool move_pannel (int x, int y)
{
    if (x < 0 && y < 0) {
        return false;
    }
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
        bposX = x;
        bposY = y;
        return true;
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
        bposX = x;
        bposY = y;
        return true;
    }
    return false;
}

void solve ()
{
    while (true) {
        double cur_time = timer.get_time();
        double cur_temp = TEMP_START - TEMP_DIFF * cur_time;
        if (cur_time > TIME_LIMIT) break;
        int idx = Random.rand() % AnsNum;
        int orix = AnsX[idx];
        int oriy = AnsY[idx];
        AnsX[idx] = Random.rand() % N;
        AnsY[idx] = Random.rand() % M;
        if (Random.rand() % 5 == 0) {
            AnsX[idx] = -1;
            AnsY[idx] = -1;
        }
        bposX = ori_bposX;
        bposY = ori_bposY;
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < M; y++) {
                Board[x][y] = oriBoard[x][y];
            }
        }
        bool flag = true;
        for (int i = 0; i < AnsNum; i++) {
            if (!move_pannel(AnsX[i], AnsY[i])) {
                flag = false;
                break;
            }
        }
        if (flag) {
            int n_score = get_score();
            if (accept((double)(Score - n_score), cur_temp)) {
                Score = n_score;
                if (BestScore > Score) {
                    BestScore = Score;
                    for (int i = 0; i < AnsNum; i++) {
                        BestAnsX[i] = AnsX[i];
                        BestAnsY[i] = AnsY[i];
                    }
                }
            } else {
                AnsX[idx] = orix;
                AnsY[idx] = oriy;
            }
        } else {
            AnsX[idx] = orix;
            AnsY[idx] = oriy;
        }
    }
}

void intialize ()
{
    for (int x = 0; x < N; x++) {
        for (int y = 0; y < M; y++) {
            if (Board[x][y] == -1) {
                bposX = x;
                bposY = y;
                ori_bposX = x;
                ori_bposY = y;
            }
        }
    }
    for (int i = 0; i < AnsNum; i++) {
        while (true) {
            int x = Random.rand() % N;
            int y = Random.rand() % M;
            if (move_pannel(x, y)) {
                AnsX[i] = x;
                AnsY[i] = y;
                BestAnsX[i] = x;
                BestAnsY[i] = y;
                break;
            }
        }
    }
    Score = get_score();
    BestScore = Score;
}

vector < pair < int,int > > get_ans ()
{
    vector < pair < int,int > > ret;
    for (int i = 0; i < AnsNum; i++) {
        if (BestAnsX[i] >= 0 && BestAnsY[i] >= 0) {
            ret.push_back(make_pair(AnsX[i], AnsY[i]));
        }
    }
    return ret;
}

int main () {

    timer.start();

    cin >> N >> M;
    for (int x = 0; x < N; x++) {
    	for (int y = 0; y < M; y++) {
    		cin >> Board[x][y];
            oriBoard[x][y] = Board[x][y];
    	}
    }

    intialize();
    solve();

    vector < pair < int,int > > ans_inst = get_ans();

    cout << ans_inst.size() << endl;
    for (int i = 0; i < (int)ans_inst.size(); i++) {
        cout << ans_inst[i].first << " " << ans_inst[i].second << endl;
    }
}

