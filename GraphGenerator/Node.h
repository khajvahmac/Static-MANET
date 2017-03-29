#ifndef _NODE_H
#define _NODE_H

class Node
{
public:
	Node();


private:
	int x;									// X coordinate of the node
	int y;									// Y coordinate of the node
	int currentZone;						// zone, in which the current node is
	double curBatteryLevel;					// [0,1]
	double tCost;							// energy cost of 1 transmission
};

#endif // !_NODE_H