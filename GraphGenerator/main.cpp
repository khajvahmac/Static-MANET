#include "Graph.h";

int main()
{
	Graph g(100,100,100);

	g.generate_connected_random_graph3(3);
	g.info();
	g.export_graph();

	return 0;
}