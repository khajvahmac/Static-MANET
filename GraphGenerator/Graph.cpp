/*
C++ program that defines Graph class, it's construction and traversal.
*/

#include "Graph.h"

#include <algorithm>
#include <time.h>

typedef unsigned int uint;

Graph::Graph()
{
	N = 0;
	E = 0;
	E_actual = 0;
	density = 0;
	gWidth = 0;
	gHeight = 0;
}

Graph::Graph(int NumberOfNodes)
{
	if (NumberOfNodes < 0) NumberOfNodes = 0;

	N = NumberOfNodes;
	E = 0;
	E_actual = 0;
	density = 0;
	adj.resize(N);
	nodes.resize(N);
	for (int i = 0; i < N; ++i)
		nodes[i] = nullptr;
}

Graph::Graph(int NumberOfNodes, double Density, int areaWidth, int areaHeight)
{
	if (NumberOfNodes < 0) NumberOfNodes = 0;
	if (Density < 0.) Density = 0.;
	if (Density > 100.) Density = 100.;
	if (gWidth < 0) gWidth = 0;
	if (gHeight < 0) gHeight = 0;

	gWidth = areaHeight;
	gHeight = areaHeight;

	N = NumberOfNodes;
	density = Density;
	E = (int)(Density*N*(N - 1) / 2);
	E_actual = 0;
	adj.resize(N);
	nodes.resize(N);
	for (int i = 0; i < N; ++i)
		nodes[i] = nullptr;
}

Graph::~Graph()
{

	for (int i = 0; i < N; ++i)
		adj[i].clear();
	adj.clear();
	adj.shrink_to_fit();

	for (int i = 0; i < N; ++i)
		nodes[i]->~Node();
	nodes.clear();
	nodes.shrink_to_fit();
}

void Graph::info()
{
	printf("Graph width: %d\n", gWidth);
	printf("Grahp height: %d\n", gHeight);
	printf("Number of nodes: %d\n", N);
	printf("Number of edges: %d\n", E);
	printf("Density: %.2f\n", density);
	printf("Connected: %d\n", isConnected);
}

bool Graph::contains_edge(int from, int to)
{
	if (from < 0 || to < 0 || from > N || to > N)
		return false;

	return adj[from].find(to) != adj[from].end();
}

void Graph::add_directed_edge(int from, int to)
{
	if (from < 0 || to < 0 || from >= N || to >= N ||
		adj[from].find(to) != adj[from].end())
		return;

	adj[from].insert(to);

	E_actual++;
}

void Graph::add_undirected_edge(int node1, int node2)
{
	if (node1 < 0 || node2 < 0 || node1 >= N || node2 >= N)
		return;

	add_directed_edge(node1, node2);
	add_directed_edge(node2, node1);
}

void Graph::remove_directed_edge(int from, int to)
{
	if (from < 0 || to < 0 || from >= N || to >= N ||
		adj[from].find(to) == adj[from].end())
		return;

	adj[from].erase(to);

	E_actual--;

}

void Graph::remove_undirected_edge(int node1, int node2)
{
	if (node1 < 0 || node2 < 0 || node1 >= N || node2 >= N)
		return;

	remove_directed_edge(node1, node2);
	remove_directed_edge(node2, node1);
}

void Graph::DFS(int from, bool* const visited)
{
	// mark the current node as visited and print it
	visited[from] = false;
	//printf("%d\n", from);

	// check all non visited neighbours and visit them recursively.
	for (auto it = adj[from].begin(); it != adj[from].end(); ++it)
	{
		int to = (*it);
		if (!visited[to])
			DFS(to, visited);
	}
}

void Graph::generate_random_graph()
{
	srand((uint)time(NULL));

	for (int i = 0; i < N; ++i)
	{
		if (nodes[i]) {
			nodes[i]->~Node();
			nodes[i] = nullptr;
		}
	}

	set<pair<int, int> > curPoints;

	int posX = rand() % gWidth;
	int posY = rand() % gHeight;

	for (int i = 0; i < N; ++i)
	{
		while (curPoints.find({ posX, posY }) != curPoints.end()) {
			posX = rand() % gWidth;
			posY = rand() % gHeight;
		}
		curPoints.insert({ posX, posY });

		nodes[i] = new Node(posX, posY, DEFAULT_TRANSMISSION_RANGE);
	}

	rebuild_adjacency_list();
}

void Graph::generate_connected_random_graph()
{
	while(!isConnected)
		generate_random_graph();
}

void Graph::rebuild_adjacency_list()
{
	for (int i = 0; i < N; ++i)
		adj[i].clear();

	for (int i = 0; i < N; ++i)
		for (int j = i + 1; j < N; ++j)
			if (dist(nodes[i], nodes[j]) <= DEFAULT_TRANSMISSION_RANGE)
				add_undirected_edge(i, j);

	// mark all the vertices as not visited
	int components = 0;
	bool *visited = new bool[N];
	memset(visited, 0, N);

	// recursive DFS traversal
	for (int i = 0; i < N; i++) {
		if (!visited[i]) {
			components++;
			DFS(i, visited);
		}
	}

	isConnected = (components <= 1) ? true : false;

	delete[] visited;
}

int main()
{
	return 0;
}