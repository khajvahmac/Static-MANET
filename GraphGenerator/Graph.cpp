/*
C++ program that defines Graph class, it's construction and traversal.
*/

#include "Graph.h"

#include <algorithm>
#include <time.h>
#include <fstream>

typedef unsigned int uint;

static bool visited[5000];

Graph::Graph()
{
	N = 0;
	E = 0;
	density = 0;
	gWidth = 0;
	gHeight = 0;
}

Graph::Graph(int NumberOfNodes, int areaWidth, int areaHeight)
{
	if (NumberOfNodes < 0) NumberOfNodes = 0;
	if (gWidth < 0) gWidth = 0;
	if (gHeight < 0) gHeight = 0;

	gWidth = areaWidth;
	gHeight = areaHeight;

	N = NumberOfNodes;
	E = 0;
	density = 0;
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
	printf("\n-----------------\n");
	printf("Graph width: %d\n", gWidth);
	printf("Grahp height: %d\n", gHeight);
	printf("Number of nodes: %d\n", N);
	printf("Number of edges: %d\n", E);
	printf("Density(%%): %.2f\n", density);
	printf("Connected: %d\n", isConnected);
	printf("-----------------\n");
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

	E++;
}

void Graph::add_undirected_edge(int node1, int node2)
{
	if (node1 < 0 || node2 < 0 || node1 >= N || node2 >= N)
		return;

	add_directed_edge(node1, node2);
	add_directed_edge(node2, node1);

	// as we do double increment in add_directed_edge
	E--;
}

void Graph::remove_directed_edge(int from, int to)
{
	if (from < 0 || to < 0 || from >= N || to >= N ||
		adj[from].find(to) == adj[from].end())
		return;

	adj[from].erase(to);

	E--;
}

void Graph::remove_undirected_edge(int node1, int node2)
{
	if (node1 < 0 || node2 < 0 || node1 >= N || node2 >= N)
		return;

	remove_directed_edge(node1, node2);
	remove_directed_edge(node2, node1);

	// as we do double decrement in remove_directed_edge
	E++;
}

void Graph::DFS(int from, bool* const visited)
{
	// mark the current node as visited and print it
	visited[from] = true;
	//printf("%d\n", from);

	// check all non visited neighbours and visit them recursively.
	for (auto it = adj[from].begin(); it != adj[from].end(); ++it)
	{
		int to = (*it);
		if (!visited[to])
			DFS(to, visited);
	}
}

void Graph::generate_connected_random_graph2()
{
	srand((uint)time(NULL));

	for (int i = 0; i < N; ++i)
	{
		if (nodes[i]) {
			nodes[i]->~Node();
			nodes[i] = nullptr;
		}
	}

	int posX = rand() % gWidth;
	int posY = rand() % gHeight;

	set<pair<int, int> > curPoints;

	curPoints.insert({ posX, posY });
	nodes[0] = new Node(posX, posY, DEFAULT_TRANSMISSION_RANGE);

	while (curPoints.size() != N)
	{
		posX = rand() % gWidth;
		posY = rand() % gHeight;

		for (auto x : curPoints) {
			if (curPoints.find({ posX, posY }) == curPoints.end() && dist({ posX, posY }, x) <= DEFAULT_TRANSMISSION_RANGE) {
				curPoints.insert({ posX, posY });
				nodes[curPoints.size() - 1] = new Node(posX, posY, DEFAULT_TRANSMISSION_RANGE);
				break;
			}
		}
	}

	rebuild_adjacency_list();
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
	int generations = 0;
	while (!isConnected) {
		generate_random_graph();
		if (++generations % 1000 == 0)
			printf("generations: %d\r", generations);
	}
}

void Graph::rebuild_adjacency_list()
{
	for (int i = 0; i < N; ++i)
		adj[i].clear();
	E = 0;

	for (int i = 0; i < N; ++i) {
		for (int j = i + 1; j < N; ++j) {
			if (dist(nodes[i], nodes[j]) <= DEFAULT_TRANSMISSION_RANGE) {
				add_undirected_edge(i, j);
			}
		}
	}

	calculate_density();

	// mark all the vertices as not visited
	int components = 0;

	memset(visited, 0, N);

	// recursive DFS traversal
	for (int i = 0; i < N; i++) {
		if (!visited[i]) {
			components++;
			DFS(i, visited);
		}
	}

	isConnected = (components <= 1) ? true : false;
}

void Graph::calculate_density()
{
	density = 100.*E / (N*(N - 1) / 2);
}

void Graph::export_graph()
{
	std::ofstream file;
	file.open("graph.dat");

	file << N << " " << E << " " << DEFAULT_TRANSMISSION_RANGE << "\n";

	for (int i = 0; i < N; ++i)
		file << nodes[i]->getX() << " " << nodes[i]->getY() << "\n";

	for (int i = 0; i < N; ++i) {
		for (auto to : adj[i])
			if (i < to)
				file << i + 1 << " " << to + 1 << "\n";
	}

	file.close();
}

void Graph::set_hex_zone()
{
	zRowCount = (int)(1.0*gWidth / (sqrt(3)*hexSide)) + 5;
	zColCount = (int)((2.*gHeight) / (3 * hexSide)) + 5;
}

int Graph::get_hex_zone(double x, double y)
{
	int row, col;
	if (y <= hexSide) {
		row = 0;
	}
	else {
		y -= hexSide;
		row = (int)(y / (3.*hexSide / 2)) + 1;
	}

	if (row % 2) {
		x -= hexSide*sqrt(3) / 2;
	}
	col = (int)(x / (hexSide*sqrt(3)));

	return row*zColCount + col;
}