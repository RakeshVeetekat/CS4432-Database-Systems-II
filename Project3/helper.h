#include <stdio.h>
#include <stdlib.h>
#include "project3.h"

struct distance_table {
  int costs[MAX_NODES][MAX_NODES];
};

// Function prototypes
void print_trace(char *methodCalled);
void set_to_infinity(int numNodesInNetwork, struct distance_table *distanceTable);
void set_adjacent_costs( struct NeighborCosts *neighborCosts, struct distance_table *distanceTable);
int determine_smallest_column_value(int column, int numNodesInNetwork, struct distance_table *distanceTable);
void send_to_neighbors(int source_id, int numNodesInNetwork, struct distance_table *distanceTable);
int check_node_costs(int numNodesInNetwork, struct RoutePacket *rcvdpkt, struct distance_table *distanceTable);
