#ifndef _GRAPH_H
#define _GRAPH_H

#include "Node.h"

#include <vector>

using std::vector;
using std::pair;

class Graph
{
public:
	Graph();											// default constructor
	Graph(int numberOfNodes);							// constructor 2
	Graph(int numberOfNodes, double density, 
			int areaWidth = 300, int areaHeight = 300);	// constructor 3

	~Graph();											// default destructor

	void info();										// prints general info about the graph

	bool contains_edge(int from, int to);				// check if current node already exists

	void add_directed_edge(int from, int to);			// add an directed edge to graph
	void add_undirected_edge(int node1, int node2);		// add an undirected edge to graph
	void remove_directed_edge(int from, int to);		// remove directed edge from graph
	void remove_undirected_edge(int node1, int node2);	// remove undirected edge from graph

	void DFS(int node, bool* const visited);			// DFS traversal of the complete graph
	void generate_random_graph();						// generates a random graph
	void generate_connected_random_graph();				// generates a random graph

private:
	int gWidth;											// width of the graph zone
	int gHeight;										// height of the graph zone
	int N;												// number of nodes
	int E;												// number of edges
	int E_actual;										// number of actual edges
	double density;										// density of the graph [0,1]
	bool isConnected;									// check if graph is connected
	vector< set<int> > adj;								// pointer to an array containing adjacency list
	vector<Node*> nodes;								// vector of pointers to node objects

	void rebuild_adjacency_list();
};

#endif // !_GRAPH_H
