#include "Graph.h";

int main()
{
	Graph g(8,100,100);

	g.generate_connected_random_graph2();
	g.info();
	g.export_graph();

	return 0;
}