/*
C++ program that defines Graph class, it's construction and traversal.
*/

#include "Graph.h"

#include <algorithm>
#include <time.h>

Graph::Graph()
{
	this->N = 0;
	adj = new vector< set<int> >;
}

Graph::Graph(int NumberOfNodes)
{
	if (NumberOfNodes < 0) NumberOfNodes = 0;

	this->N = NumberOfNodes;
	this->E = 0;
	this->E_actual = 0;
	this->density = 0;
	this->adj = new vector< set<int> >;
	this->adj->resize(N);
}

Graph::Graph(int NumberOfNodes, double Density)
{
	if (NumberOfNodes < 0) NumberOfNodes = 0;
	if (Density < 0.) Density = 0.;
	if (Density > 100.) Density = 100.;

	this->N = NumberOfNodes;
	this->density = Density;
	this->E = Density*N*(N - 1) / 2;;
	this->E_actual = 0;
	this->adj = new vector< set<int> >;
	this->adj->resize(N);
}

Graph::Graph(int NumberOfNodes, int NumberOfEdges)
{
	if (NumberOfNodes < 0) NumberOfNodes = 0;
	if (NumberOfEdges < 0) NumberOfEdges = 0;
	if (NumberOfEdges > NumberOfNodes*(NumberOfNodes - 1) / 2)
		NumberOfEdges = NumberOfNodes*(NumberOfNodes - 1) / 2;

	this->N = NumberOfNodes;
	this->E = NumberOfEdges;
	this->E_actual = 0;
	this->density = NumberOfEdges / (NumberOfNodes*(NumberOfNodes - 1) / 2);
	this->adj = new vector< set<int> >;
	this->adj->resize(N);
}

Graph::~Graph()
{
	this->adj->clear();
	this->adj->shrink_to_fit();
}

bool Graph::contains_edge(int from, int to)
{
	if (from < 0 || to < 0 || from > N || to > N)
		return false;

	return (*adj)[from].find(to) != (*adj)[from].end();
}

void Graph::add_edge(int from, int to)
{
	if (from < 0 || to < 0 || from >= N || to >= N)
		return;

	/* if we asume graph is bidirectional, then the reverse
	edge should also be added */

	(*adj)[from].insert(to);

	this->E_actual++;
}

void Graph::DFSUtil(int from, bool visited[])
{
	// mark the current node as visited and print it
	visited[from] = true;
	printf("%d\n", from);

	// check all non visited neighbours and visit them recursively.
	for (auto it = (*adj)[from].begin(); it != (*adj)[from].end(); ++it)
	{
		int to = (*it);
		if (!visited[to])
			DFSUtil(to, visited);
	}
}

// DFS. It uses recursive DFSUtil()
void Graph::DFS()
{
	// M\mark all the vertices as not visited
	bool *visited = new bool[N];
	for (int i = 0; i < N; i++)
		visited[i] = false;

	//call the recursive function to print DFS traversal
	for (int i = 0; i < N; i++)
		if (visited[i] == false)
			DFSUtil(i, visited);
}

void Graph::generate_random_graph()
{
	int from, to;
	while (E_actual != E)
	{
		from = rand() % N;
		to = rand() % N;

		if (from != to && !contains_edge(from, to))
			add_edge(from, to);
	}
}

int main()
{
	return 0;
}