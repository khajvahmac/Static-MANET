#include "Graph.h";

int main()
{
	Graph g(200,100,100);

	g.generate_connected_random_graph3(4);
	g.info();
	g.export_graph();

	return 0;
}