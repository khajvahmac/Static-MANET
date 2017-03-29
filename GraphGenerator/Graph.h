#ifndef _GRAPH_H
#define _GRAPH_H

#include <vector>
#include <set>

using std::vector;
using std::set;

class Graph
{
public:
	Graph();										// default constructor
	Graph(int numberOfNodes);						// constructor 2
	Graph(int numberOfNodes, double density);		// constructor 3
	Graph(int numberOfNodes, int numberOfEdges);	// constructor 4
	~Graph();										// default destructor

	bool contains_edge(int from, int to);			// check if current node already exists
	void add_edge(int from, int to);				// add an edge to graph
	void DFS();										// print DFS traversal of the complete graph
	void generate_random_graph();					// generates a random graph

private:
	int N;											// number of nodes
	int E;											// number of edges
	int E_actual;									// number of actual edges
	double density;									// density of the graph [0,1]
	vector< set<int> >* adj;						// pointer to an array containing adjacency list
	void DFSUtil(int node, bool visited[]);			// recursive calls of DFS
};

#endif // !_GRAPH_H
