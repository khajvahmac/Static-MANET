#include "Graph.h";

int main()
{
	Graph g(100,100,100);

	g.generate_connected_random_graph();
	g.info();
	g.export_graph();

	return 0;
}