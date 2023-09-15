//#include <stdio.h>
//#include "project3.h"
#include "helper.h"

#define NODE_ID 2

extern int TraceLevel;

//struct distance_table {
//  int costs[MAX_NODES][MAX_NODES];
//};
struct distance_table dt2;
struct NeighborCosts   *neighbor2;

/* students to write the following two routines, and maybe some others */

// Function prototypes
void printdt2(int MyNodeNumber, struct NeighborCosts *neighbor, struct distance_table *dtptr);

void rtinit2() {

    // Show that this trace has been initialized
    print_trace("rtinit2()");
    // Obtain the costs for every neighbor in rt2
    neighbor2 = getNeighborCosts(NODE_ID);
    // To start the simulation, the cost of all the nodes will be set to infinity
    set_to_infinity(neighbor2->NodesInNetwork, &dt2);
    // Set the costs of the adjacent nodes
    set_adjacent_costs(neighbor2, &dt2);
    // Start to produce packets that are sent to neighbors
    send_to_neighbors(NODE_ID, neighbor2->NodesInNetwork, &dt2);
    // Display the information gathered for rt2 at the initialization step
    printdt2(NODE_ID, neighbor2, &dt2);

}


void rtupdate2( struct RoutePacket *rcvdpkt ) {

    // Show that this trace is just updating rt2
    print_trace("rtupdate2()");

    // Check if any packets need to be created and sent
    int flag = check_node_costs(neighbor2->NodesInNetwork, rcvdpkt, &dt2);
    if(flag){
        send_to_neighbors(NODE_ID, neighbor2->NodesInNetwork, &dt2);
    }

    // Update and display the information gathered for rt2
    printdt2(NODE_ID, neighbor2, &dt2);

}


/////////////////////////////////////////////////////////////////////
//  printdt
//  This routine is being supplied to you.  It is the same code in
//  each node and is tailored based on the input arguments.
//  Required arguments:
//  MyNodeNumber:  This routine assumes that you know your node
//                 number and supply it when making this call.
//  struct NeighborCosts *neighbor:  A pointer to the structure 
//                 that's supplied via a call to getNeighborCosts().
//                 It tells this print routine the configuration
//                 of nodes surrounding the node we're working on.
//  struct distance_table *dtptr: This is the running record of the
//                 current costs as seen by this node.  It is 
//                 constantly updated as the node gets new
//                 messages from other nodes.
/////////////////////////////////////////////////////////////////////
void printdt2( int MyNodeNumber, struct NeighborCosts *neighbor, 
		struct distance_table *dtptr ) {
    int       i, j;
    int       TotalNodes = neighbor->NodesInNetwork;     // Total nodes in network
    int       NumberOfNeighbors = 0;                     // How many neighbors
    int       Neighbors[MAX_NODES];                      // Who are the neighbors

    // Determine our neighbors 
    for ( i = 0; i < TotalNodes; i++ )  {
        if (( neighbor->NodeCosts[i] != INFINITY ) && i != MyNodeNumber )  {
            Neighbors[NumberOfNeighbors] = i;
            NumberOfNeighbors++;
        }
    }
    // Print the header
    printf("                via     \n");
    printf("   D%d |", MyNodeNumber );
    for ( i = 0; i < NumberOfNeighbors; i++ )
        printf("     %d", Neighbors[i]);
    printf("\n");
    printf("  ----|-------------------------------\n");

    // For each node, print the cost by travelling thru each of our neighbors
    for ( i = 0; i < TotalNodes; i++ )   {
        if ( i != MyNodeNumber )  {
            printf("dest %d|", i );
            for ( j = 0; j < NumberOfNeighbors; j++ )  {
                    printf( "  %4d", dtptr->costs[i][Neighbors[j]] );
            }
            printf("\n");
        }
    }
    printf("\n");
}    // End of printdt2

