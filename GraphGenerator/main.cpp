#include "Graph.h";

int main()
{
	Graph g(100,100,100);

	g.generate_connected_random_graph3(3);
	g.info();
	g.export_graph("data1.dat");

	Graph f(100, 100, 100);
	f.generate_connected_random_graph3(3);
	f.info();
	f.export_graph("data2.dat");

	Graph h(100, 100, 100);
	h.generate_connected_random_graph3(4);
	h.info();
	h.export_graph("data3.dat");

	Graph z(100, 100, 100);
	z.generate_connected_random_graph3(4);
	z.info();
	z.export_graph("data4.dat");

	Graph r(100, 100, 100);
	r.generate_connected_random_graph3(5);
	r.info();
	r.export_graph("data5.dat");

	Graph y(100, 100, 100);
	y.generate_connected_random_graph3(5);
	y.info();
	y.export_graph("data6.dat");

	
	return 0;
}