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
	Graph(int numberOfNodes,
		int width = 300, int height = 300);				// constructor

	~Graph();											// default destructor

	void info();										// prints general info about the graph

	bool contains_edge(int from, int to);				// check if current node already exists

	void add_directed_edge(int from, int to);			// add an directed edge to graph
	void add_undirected_edge(int node1, int node2);		// add an undirected edge to graph
	void remove_directed_edge(int from, int to);		// remove directed edge from graph
	void remove_undirected_edge(int node1, int node2);	// remove undirected edge from graph

	void DFS(int node, bool* const visited);			// DFS traversal of the complete graph
	void generate_random_graph();						// generates a random graph
	void generate_connected_random_graph();				// generates a connected random graph
	void generate_connected_random_graph2();			// generates a connected random graph
	void generate_connected_random_graph3(int density);	// generates a connected random graph

	void export_graph();								// exports the graph

	void set_hex_zone();								// calculates number of zones in 1 row and 1 column
	int get_hex_zone(double x, double y);				// gets zone number by coordinates

private:
	int gWidth;											// width of the graph zone
	int gHeight;										// height of the graph zone
	int N;												// number of nodes
	int E;												// number of edges
	double density;										// density of the graph [0,1]
	bool isConnected;									// check if graph is connected
	double hexSide;										// size of a side of a hexagon
	int zRowCount, zColCount;							// number of zones rows and columns
	vector< set<int> > adj;								// pointer to an array containing adjacency list
	vector<Node*> nodes;								// vector of pointers to node objects

	void calculate_density();							// calculate density of graph from number of edges
	void rebuild_adjacency_list();						// rebuild the adjacency list
};

#endif // !_GRAPH_H
