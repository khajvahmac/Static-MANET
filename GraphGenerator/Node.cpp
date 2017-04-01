#include "Node.h"

Node::Node()
{
	x = 0;
	y = 0;
	r = 0;
	zone = 0;
	cluster = 0;
	batteryLevel = 0;
	tCost = 0;
	isCriticalNode = 0;
}

Node::Node(int coordX, int coordY, int transmissionRadius)
{
	x = coordX;
	y = coordY;
	r = transmissionRadius;
	zone = 0;
	cluster = 0;
	batteryLevel = 0;
	tCost = 0;
	isCriticalNode = 0;
}

Node::Node(int coordX, int coordY, int transmissionRadius, int Zone,
	int Cluster, double BatteryLevel, double transmissionCost)
{
	x = coordX;
	y = coordY;
	r = transmissionRadius;
	zone = Zone;
	cluster = Cluster;
	batteryLevel = BatteryLevel;
	tCost = transmissionCost;
	isCriticalNode = 0;
}

Node::~Node()
{

}

double dist(Node* const a, Node* const b)
{
	return sqrt(pow(a->getX() - b->getX(), 2) + pow(a->getY() - b->getY(), 2));
}