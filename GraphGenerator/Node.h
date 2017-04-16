#ifndef _NODE_H
#define _NODE_H

#include <set>
#include <cmath>
using std::set;

#define DEFAULT_TRANSMISSION_RANGE 10

class Node
{
public:
	Node();
	Node(int coordX, int coordY, int transmissionRadius);
	Node(int coordX, int coordY, int transmissionRadius, int zone,
		int cluster, double batteryLevel, double transmissionCost);

	int getX() { return x; };
	int getY() { return y; };
	int getR() { return r; };

	~Node();

private:
	int x;									// X coordinate of the node
	int y;									// Y coordinate of the node
	int r;									// transmission radius
	int zone;								// zone, in which the current node is
	int cluster;							// cluster number to which it belongs to 
	double batteryLevel;					// battery level [0,1]
	double tCost;							// energy cost of 1 transmission
	bool isCriticalNode;					// show if the note is a critical and hence a bridge
};

double dist(Node* const a, Node* const b);
double dist(const std::pair<int, int> a, const std::pair<int, int> b);

#endif // !_NODE_H