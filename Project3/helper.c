#include "helper.h"

// Helper functions shared amongst each of the node files

extern int TraceLevel;
extern float clocktime;

// Uses the method passed in to print when it was called
void print_trace(char *methodCalled){
    printf("%s was called at time t=%f.\n", methodCalled, clocktime);
}

// Initializes the costs in the distance table passed in to a value of infinity
void set_to_infinity(int numNetworkNodes, struct distance_table *dt){

    int r;
    int c;
    for(r = 0; r < numNetworkNodes; r++){
        for(c = 0; c < numNetworkNodes; c++){
            dt->costs[r][c] = INFINITY;
        }
    }

}

// Initializes the costs in the distance table for the adjacent nodes
void set_adjacent_costs(struct NeighborCosts *neighbors, struct distance_table *dt){

    int n;
    for(n = 0; n < neighbors->NodesInNetwork; n++){
        dt->costs[n][n] = neighbors->NodeCosts[n];
    }

}

// Finds the smallest cost for a column in the distance table passed in
int determine_smallest_column_value(int column, int numNetworkNodes, struct distance_table *dt){

    int n;
    int min = INFINITY;

    for(n = 0; n < numNetworkNodes; n++){
        int temp = dt->costs[n][column];
        min = temp < min ? temp : min;
    }

    return min;

}

// Produces a packet in each node within the network,
// and then calculates the smallest cost in the distance table when sending it to each neighbor
void send_to_neighbors(int srcID, int numNetworkNodes, struct distance_table *dt){

    int n;

    for(n = 0; n < numNetworkNodes; n++){

        if(n != srcID){

            struct RoutePacket *routePacket = (struct RoutePacket *)malloc(sizeof(struct RoutePacket));
            routePacket->destid = n;
            routePacket->sourceid = srcID;
            int i;

            for(i = 0; i<numNetworkNodes; i++){
                routePacket->mincost[i] = determine_smallest_column_value(i, numNetworkNodes, dt);
            }

            toLayer2(*routePacket);
            printf("At time t=%f, node %d sends packet to node %d\n", clocktime, srcID, n);

        }
    }
}

// Checks to see if any of the nodes needs to be updated
int check_node_costs(int numNetworkNodes, struct RoutePacket *rcvdpkt, struct distance_table *dt){

    int srcID = rcvdpkt->sourceid;
    int flag = 0;
    int n;

    for(n = 0; n < numNetworkNodes; n++)
    {
        int *current = &(dt->costs[n][srcID]);
        int new_min = rcvdpkt->mincost[n] + dt->costs[srcID][srcID];
        
        if (*current > new_min && new_min >= 0){
            *current = new_min;
            flag++;
        }
    }

    return flag;

}
