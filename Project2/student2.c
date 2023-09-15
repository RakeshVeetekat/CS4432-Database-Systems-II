#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "project2.h"
 
/* ***************************************************************************
 ALTERNATING BIT AND GO-BACK-N NETWORK EMULATOR: VERSION 1.1  J.F.Kurose

   This code should be used for unidirectional or bidirectional
   data transfer protocols from A to B and B to A.
   Network properties:
   - one way network delay averages five time units (longer if there
     are other messages in the channel for GBN), but can be larger
   - packets can be corrupted (either the header or the data portion)
     or lost, according to user-defined probabilities
   - packets may be delivered out of order.

   Compile as gcc -g project2.c student2.c -o p2
**********************************************************************/

/********* STUDENTS WRITE THE NEXT SEVEN ROUTINES *********/
/* 
 * The routines you will write are detailed below. As noted above, 
 * such procedures in real-life would be part of the operating system, 
 * and would be called by other procedures in the operating system.  
 * All these routines are in layer 4.
 */

// Constants
#define A 0
#define B 1
#define READY 0
#define WAITING 1

// Prototype functions
int createChecksum(struct pkt*);
unsigned short performCRCAlgorithm(char*, int);
int checkPacketCorruption(struct pkt*);
void createACK(struct pkt*);
void queueItem(struct msg message);
void removeItem();
struct msg firstQueueItem();
int isBufferEmpty();

// Global variables
int stateA;
struct pkt packetA;
int prevPacketB;
struct Node *front = NULL;
struct Node *back = NULL;

// Nodes to use in the queue
struct Node {
    struct msg data;
    struct Node *next;
};

/* 
 * A_output(message), where message is a structure of type msg, containing 
 * data to be sent to the B-side. This routine will be called whenever the 
 * upper layer at the sending side (A) has a message to send. It is the job 
 * of your protocol to insure that the data in such a message is delivered 
 * in-order, and correctly, to the receiving side upper layer.
 */
void A_output(struct msg message) {
    if (stateA == WAITING) {
        queueItem(message); // If A is waiting to send the message, queue the message here
    } else {
        packetA.acknum = 0;
        packetA.checksum = 0;
        packetA.seqnum = (packetA.seqnum + 1) % 2;
        strncpy(packetA.payload, message.data, MESSAGE_LENGTH);
        packetA.checksum = createChecksum(&packetA);
        tolayer3(A, packetA);
        stateA = WAITING;
        startTimer(A, 1000);
    }
}

/*
 * Just like A_output, but residing on the B side.  USED only when the 
 * implementation is bi-directional.
 */
void B_output(struct msg message)  {
    // Not required
}

/* 
 * A_input(packet), where packet is a structure of type pkt. This routine 
 * will be called whenever a packet sent from the B-side (i.e., as a result 
 * of a tolayer3() being done by a B-side procedure) arrives at the A-side. 
 * packet is the (possibly corrupted) packet sent from the B-side.
 */
void A_input(struct pkt packet) {
    if (!checkPacketCorruption(&packet) && packet.acknum == packetA.seqnum) {
        stopTimer(AEntity);
        stateA = READY;
        if (!isBufferEmpty()) {
            struct msg message = firstQueueItem();
            A_output(message);
            removeItem();
        }
    }
}

/*
 * A_timerinterrupt()  This routine will be called when A's timer expires 
 * (thus generating a timer interrupt). You'll probably want to use this 
 * routine to control the retransmission of packets. See starttimer() 
 * and stoptimer() in the writeup for how the timer is started and stopped.
 */
void A_timerinterrupt() {
    stopTimer(A);
    tolayer3(A, packetA);
    startTimer(A, 20);
}  

/* The following routine will be called once (only) before any other    */
/* entity A routines are called. You can use it to do any initialization */
void A_init() {
    stateA = READY;
    packetA.seqnum = 1;
}


/* 
 * Note that with simplex transfer from A-to-B, there is no routine  B_output() 
 */

/*
 * B_input(packet),where packet is a structure of type pkt. This routine 
 * will be called whenever a packet sent from the A-side (i.e., as a result 
 * of a tolayer3() being done by a A-side procedure) arrives at the B-side. 
 * packet is the (possibly corrupted) packet sent from the A-side.
 */
void B_input(struct pkt packet) {
    if (packet.seqnum != ((prevPacketB + 1) % 2) || checkPacketCorruption(&packet)) {
        createACK(&packet);
        tolayer3(B, packet);
    } else {
        struct msg message;
        strcpy(message.data, packet.payload);
        tolayer5(B, message);
        prevPacketB = (prevPacketB + 1) % 2;
        createACK(&packet);
        tolayer3(B, packet);
    }
}

/*
 * B_timerinterrupt()  This routine will be called when B's timer expires 
 * (thus generating a timer interrupt). You'll probably want to use this 
 * routine to control the retransmission of packets. See starttimer() 
 * and stoptimer() in the writeup for how the timer is started and stopped.
 */
void  B_timerinterrupt() {
    // Not required
}

/* 
 * The following routine will be called once (only) before any other   
 * entity B routines are called. You can use it to do any initialization 
 */
void B_init() {
    prevPacketB = 1;
}

// Helper functions

// Performs the cyclic redundancy check algorithm needed for the checksum
unsigned short performCRCAlgorithm(char* buffer, int size) {
    unsigned short ch;
    unsigned short w = 0;
    for (int i = 0; i < size; i++) {
        ch = buffer[i] << 8;
        for (int j = 0; j < 8; j++) {
            if ((ch & 0x8000) ^ (w & 0x8000)) {
                w = (w <<= 1) ^ 4129;
            } else {
                w <<= 1;
            }
            ch <<= 1;
        }
    }
    return w;
}

// Calculates checksum for each packet
int createChecksum(struct pkt *packet) {
    int sum = 0;
    struct pkt obj = *packet;
    char pkt_char[sizeof(obj)];
    obj.checksum = 0;
    memcpy(pkt_char, &obj, sizeof(obj));
    sum = performCRCAlgorithm(pkt_char, sizeof(pkt_char));
    return sum;
}

/* Creates ACK */
void createACK(struct pkt *packet) {
    packet->acknum = prevPacketB;
    packet->checksum = 0;
    packet->seqnum = 1;
    packet->checksum = createChecksum(packet);
}

/* Uses checksum to check whether packet is corrupted */
int checkPacketCorruption(struct pkt *packet) {
    if (createChecksum(packet) == packet->checksum) {
        return 0;
    } else {
        return 1;
    }
}

/* Inserts item to end of queue */
void queueItem(struct msg message) {
    struct Node *newNode = (struct Node *) malloc(sizeof(struct Node));
    newNode->data = message;
    newNode->next = NULL;
    if (front == NULL && back == NULL) {
        front = back = newNode;
    } else {
        back->next = newNode;
        back = newNode;
    }
}

/* Removes first element in queue */
void removeItem() {
    if (front == NULL) {
        return;
    }
    struct Node *temp = front;
    if (front == back) {
        front = back = NULL;
    } else {
        front = front->next;
    }
    free(temp);
}

/* Returns first element in queue */
struct msg firstQueueItem() {
    if (front == NULL) {
        struct msg temp;
        return temp;
    } else {
        return front->data;
    }
}

/* Returns whether the buffer is empty or not */
int isBufferEmpty() {
    if (front == NULL) {
        return 1;
    } else {
        return 0;
    }
}

