//
//  CalculMetrique.cpp
//  
//
//  Created by Florent Robic on 21/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <cmath>

using namespace std ;

vector<int > Nodes ;
vector<vector<int > > Graph ;
vector<vector<int > > Comm ;
vector<vector<int > > MyComms ;
int Rouge[10] ;
int Vert[10] ;
int Bleu[10] ;

int N,M,C ;

bool comp (int i,int j) { return (i<j); }


int sameComm(int x, int y) {
	int a = (x < y) ? x : y ;
	int b = (x > y) ? x : y ;
	
	int w = 0 ;
	
	bool ba = false ;
	bool bb = false ;
	
	for (int i = 0 ; i < C ; i++) {
		bool ba = false ;
		bool bb = false ;
		for ( int j = 0 ; j < Comm[i].size() ; j++) {
			if (a == Comm[i][j])
				ba = true ;
			if (ba && not bb) {
				if (b == Comm[i][j])
					w++ ;
			}
		}
	}
	
	return w ;
	
}

int intercomm(int x,int y) {
	int retour = 0 ;
	int i = 0 ;
	int j = 0 ;
	
	while (i < MyComms[x].size() && j < MyComms[y].size()) {
		if (MyComms[x][i] == MyComms[y][j]) {
			retour++ ;
			i++ ;
			j++ ;
		}
		else if (MyComms[x][i] > MyComms[y][j]) {
			j++ ;
		}
		else if (MyComms[x][i] < MyComms[y][j]) {
			i++ ;
		}
	}
	return retour ;
}

int unioncomm(int x,int y) {
	int retour = 0 ;
	int i = 0 ;
	int j = 0 ;
	
	while (i < MyComms[x].size() && j < MyComms[y].size()) {
		if (MyComms[x][i] == MyComms[y][j]) {
			retour++ ;
			i++ ;
			j++ ;
		}
		else if (MyComms[x][i] > MyComms[y][j]) {
			j++ ;
			retour++ ;
		}
		else if (MyComms[x][i] < MyComms[y][j]) {
			i++ ;
			retour++ ;
		}
	}
	return retour ;
}

bool isneigh(int a, int b) {
	for (int i = 0 ; i < Graph[a].size() ; i++) {
		if ( b == Graph[a][i] )
			return true ;
	}
	return false ;
}


bool incomm(int x, int nc) {
	for (int i = 0 ; i < Comm[nc].size() ; i++) {
		if (x == Comm[nc][i])
			return true ;
	}
	return false ;
}

int lookat(int x) {
	for (int i = 0 ; i < N ; i++) {
		if (x == Nodes[i])
			return i ;
	}
	return N ;
}

int sizeinter(int a,int b) {
	int r = 0 ;
	int j = 0 ;
	int i = 0 ;
	while ( i < Comm[a].size() && j < Comm[b].size()) {
		if (Comm[a][i] == Comm[b][j]) {
			r++ ;
			j++ ;
			i++ ;
		}
		else if ( Comm[a][i] < Comm[b][j] )
			i++ ;
		else
			j++ ;
	}
	return r ;
}


double cohesion(int nc) {
	int Deltai = 0 ;
	int Deltao = 0 ;
	for (int i = 0 ; i < Comm[nc].size() ; i++) {
		int node = Comm[nc][i] ;
		for (int j = i+1 ; j < Comm[nc].size() ; j++) {
			int nodeb = Comm[nc][j] ;
			if (isneigh(node,nodeb)) {
				for (int k = 0 ; k < Graph[node].size() ; k++) {
					int nodec = Graph[node][k] ;
					if (isneigh(nodeb,nodec)) {
						if(incomm(nodec,nc)) {
							if (nodec > nodeb) {
								Deltai++ ;
							}
						}
						else {
							Deltao++ ;
						}
					}
				}
			}
		}
	}
	double density = (double)Deltai*6./( (double) (Comm[nc].size() * (Comm[nc].size() - 1) * (Comm[nc].size() - 2)) ) ;
	double isolation = (double)Deltai / ((double) Deltai + (double) Deltao) ;
	
	return density*isolation ;
}

int main() {
	
	Rouge[0] = 200 ; Vert[0] = 50 ; Bleu[0] = 50 ;
	Rouge[1] = 50 ; Vert[1] = 200 ; Bleu[1] = 50 ;
	Rouge[2] = 50 ; Vert[2] = 50 ; Bleu[2] = 200 ;
	Rouge[3] = 200 ; Vert[3] = 200 ; Bleu[3] = 50 ;
	Rouge[4] = 200 ; Vert[4] = 50 ; Bleu[4] = 200 ;
	Rouge[5] = 50 ; Vert[5] = 200 ; Bleu[5] = 200 ;
	// Aquisition des données
	
	N = 0 ; // Nombre Noeud
	M = 0 ; // Nombre lien
	int x,y ;
	cin >> x >> y ;
	
	// Graphe
	
	while (x != -1) {
				
		int xx = lookat(x) ;
		
		if ( xx == N ) {
			Nodes.push_back(x) ;
			N++ ;
			Graph.resize(N) ;
			Graph[N-1].resize(0) ;
		}
		

				
		int yy = lookat(y) ;
		if (yy == N) {
			Nodes.push_back(y) ;
			N++ ;
			Graph.resize(N) ;
			Graph[N-1].resize(0) ;
		}
		
		Graph[xx].push_back(yy) ;
		Graph[yy].push_back(xx) ;
		cin >> x >> y ;
	}
	
	
	// Comm
	
	cin >> C ; // NB Communautes
	
	Comm.resize(C) ;
	
	int m=1 ;
	char c ;
	int temp ;

	MyComms.resize(N) ;
	
	for (int i = 0 ; i < N ; i++) {
		MyComms[i].resize(0) ;
	}

	
	while (cin >> temp) {
		cin.get(c) ;
		int t = lookat(temp) ;
		Comm[m-1].push_back(t) ;
		MyComms[t].push_back(m-1) ;
		if ( c == '\n' || c == '\r' ) {
			m++ ;
		}
	}
	
	
	for (int i = 0 ; i < N ; i++) {
		sort(Graph[i].begin(),  Graph[i].end(),comp) ;
		vector<int>::iterator it;
		it = unique( Graph[i].begin(),  Graph[i].end());
		Graph[i].resize( it -  Graph[i].begin() ); 
		M += Graph[i].size() ;
	}
		
	for (int i = 0 ; i < C ; i++ ) {
		sort(Comm[i].begin(),  Comm[i].end(),comp) ;
	}
	
	cout << "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" << endl ;
	cout << "<gexf xmlns:viz=\"http:///www.gexf.net/1.1draft/viz\" version=\"1.1\" xmlns=\"http://www.gexf.net/1.1draft\">" << endl ;
	cout << "<meta lastmodifieddate=\"2010-03-03+23:44\">" << endl ;
	cout << "<creator>Gephi 0.7</creator>" << endl ;
	cout << "</meta>" << endl ;
	cout << "<graph defaultedgetype=\"undirected\" idtype=\"string\" type=\"static\">" << endl ;
	cout << "<nodes>" << endl ;

	for (int i = 0 ; i < N ; i++) {
		if (MyComms[i].size() > 0) {
			cout << "<node id = \"" << i << "\" label=\"" << i << "\">" << endl ;
				cout << "<viz:color r='" << 30 <<"' g='"<< 30 <<"' b='"<< 30 <<"'/>" << endl ;
			cout << "</node>" << endl ;
		}
	}
	for (int i = 0 ; i < C ; i++ ) {
		double cohe = cohesion(i);
		cout << "<node id = \""  << N+i << "\" label=\"" << cohe << "\">" << endl ;
		cout << "<viz:color r='" << (int)(Rouge[i%6]*(0.5+cohe/2)) <<"' g='"<< (int)(Vert[i%6]*(0.5+cohe/2)) <<"' b='"<< (int)(Bleu[i%6]*(0.5+cohe/2)) <<"' a='1' />" << endl ;
		cout << "</node>" << endl ;
	}
	
	
	cout << "</nodes>" << endl ;
			 
	cout << "<edges>" << endl ;
	int k = 0 ;
	for (int i = 0 ; i < N ; i++) {	
		for (int j = 0 ; j < Graph[i].size() ; j++) {
			if (i < Graph[i][j] && MyComms[i].size() > 0 && MyComms[Graph[i][j]].size() > 0) {
				cout << "<edge id=\"" << k++ << "\" source=\""<< i <<"\" target=\"" << Graph[i][j] << "\" weight=\"" << (double)(1.+intercomm(i,Graph[i][j]))/(double)(1.+unioncomm(i,Graph[i][j])) << "\"/>" << endl ;
			}
		}
	}
		
	for (int i = 0 ; i < C ; i++ ) {
		for (int j = 0 ; j < Comm[i].size() ; j++) {
			cout << "<edge label=\"ficelle\" id=\"" << k++ << "\" source=\""<< N+i <<"\" target=\"" << Comm[i][j] << "\" weight=\"" << (sqrt(Comm[i].size())*2*(1-log(cohesion(i)))) << "\">" << endl ;
				cout << "<viz:color r='" << 255 <<"' g='"<< 255 <<"' b='"<< 255 <<"' a='0' />" << endl ;
			cout << "</edge>" << endl ;
		}
	}
	
	
	for (int i = 0 ; i < C ; i++) {
		for (int j = i+1 ; j < C ; j++) {
			if (sizeinter(i,j) > 0) {
				//cout << "<edge id=\"" << k++ << "\" source=\""<< N+i <<"\" target=\"" << N+j << "\" weight=\"" << 10.*(double)sizeinter(i,j)/((double)(Comm[i].size() + Comm[j].size())) << "\"/>" << endl ;
			}
		}
	}
	cout << "</edges>" << endl ;
	cout << "</graph>" << endl ;
	cout << "</gexf>" << endl ;
	
	
	return 0 ;
	
}