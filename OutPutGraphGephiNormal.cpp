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
vector<vector<int > > Triangles ;
int Rouge[100] ;
int Vert[100] ;
int Bleu[100] ;

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
	
	Rouge[0] = 105; Vert[0] = 72; Bleu[0] = 36;
	Rouge[1] = 51; Vert[1] = 23; Bleu[1] = 0;
	Rouge[2] = 41; Vert[2] = 95; Bleu[2] = 153;
	Rouge[3] = 255; Vert[3] = 127; Bleu[3] = 0;
	Rouge[4] = 255; Vert[4] = 255; Bleu[4] = 0;
	Rouge[5] = 127; Vert[5] = 0; Bleu[5] = 127;
	Rouge[6] = 0; Vert[6] = 168; Bleu[6] = 51;
	Rouge[7] = 255; Vert[7] = 0; Bleu[7] = 0;
	Rouge[8] = 201; Vert[8] = 215; Bleu[8] = 229;
	Rouge[9] = 102; Vert[9] = 49; Bleu[9] = 0;
	Rouge[10] = 94; Vert[10] = 135; Bleu[10] = 178;
	Rouge[11] = 204; Vert[11] = 101; Bleu[11] = 0;
	Rouge[12] = 255; Vert[12] = 255; Bleu[12] = 191;
	Rouge[13] = 70; Vert[13] = 17; Bleu[13] = 31;
	Rouge[14] = 31; Vert[14] = 113; Bleu[14] = 127;
	Rouge[15] = 255; Vert[15] = 95; Bleu[15] = 0;
	Rouge[16] = 255; Vert[16] = 255; Bleu[16] = 63;
	Rouge[17] = 108; Vert[17] = 5; Bleu[17] = 95;
	Rouge[18] = 10; Vert[18] = 150; Bleu[18] = 76;
	Rouge[19] = 255; Vert[19] = 31; Bleu[19] = 0;
	Rouge[20] = 191; Vert[20] = 233; Bleu[20] = 12;
	Rouge[21] = 159; Vert[21] = 0; Bleu[21] = 95;
	Rouge[22] = 63; Vert[22] = 189; Bleu[22] = 38;
	Rouge[23] = 223; Vert[23] = 0; Bleu[23] = 31;
	Rouge[24] = 255; Vert[24] = 191; Bleu[24] = 191;
	Rouge[25] = 38; Vert[25] = 60; Bleu[25] = 12;
	Rouge[26] = 255; Vert[26] = 223; Bleu[26] = 0;
	Rouge[27] = 106; Vert[27] = 23; Bleu[27] = 133;
	Rouge[28] = 255; Vert[28] = 63; Bleu[28] = 63;
	Rouge[29] = 12; Vert[29] = 132; Bleu[29] = 38;
	Rouge[30] = 255; Vert[30] = 159; Bleu[30] = 0;
	Rouge[31] = 63; Vert[31] = 71; Bleu[31] = 146;
	Rouge[32] = 255; Vert[32] = 255; Bleu[32] = 127;
	Rouge[33] = 89; Vert[33] = 11; Bleu[33] = 63;
	Rouge[34] = 255; Vert[34] = 127; Bleu[34] = 127;
	Rouge[35] = 25; Vert[35] = 96; Bleu[35] = 25;
	Rouge[36] = 255; Vert[36] = 191; Bleu[36] = 0;
	Rouge[37] = 84; Vert[37] = 47; Bleu[37] = 140;
	Rouge[38] = 255; Vert[38] = 63; Bleu[38] = 0;
	Rouge[39] = 20; Vert[39] = 131; Bleu[39] = 102;
	Rouge[40] = 148; Vert[40] = 175; Bleu[40] = 204;
	Rouge[41] = 153; Vert[41] = 75; Bleu[41] = 0;
	Rouge[42] = 127; Vert[42] = 211; Bleu[42] = 25;
	Rouge[43] = 191; Vert[43] = 0; Bleu[43] = 63;
	Rouge[44] = 255; Vert[44] = 199; Bleu[44] = 143;
	Rouge[45] = 55; Vert[45] = 50; Bleu[45] = 43;
	Rouge[46] = 255; Vert[46] = 215; Bleu[46] = 47;
	Rouge[47] = 89; Vert[47] = 32; Bleu[47] = 103;
	Rouge[48] = 255; Vert[48] = 87; Bleu[48] = 47;
	Rouge[49] = 25; Vert[49] = 116; Bleu[49] = 65;
	Rouge[50] = 255; Vert[50] = 135; Bleu[50] = 15;
	Rouge[51] = 50; Vert[51] = 86; Bleu[51] = 119;
	Rouge[52] = 199; Vert[52] = 219; Bleu[52] = 175;
	Rouge[53] = 116; Vert[53] = 37; Bleu[53] = 23;
	Rouge[54] = 193; Vert[54] = 228; Bleu[54] = 66;
	Rouge[55] = 145; Vert[55] = 12; Bleu[55] = 71;
	Rouge[56] = 207; Vert[56] = 161; Bleu[56] = 180;
	Rouge[57] = 92; Vert[57] = 84; Bleu[57] = 9;
	Rouge[58] = 194; Vert[58] = 200; Bleu[58] = 9;
	Rouge[59] = 143; Vert[59] = 33; Bleu[59] = 116;
	Rouge[60] = 217; Vert[60] = 53; Bleu[60] = 81;
	Rouge[61] = 73; Vert[61] = 154; Bleu[61] = 28;
	Rouge[62] = 200; Vert[62] = 134; Bleu[62] = 3;
	Rouge[63] = 111; Vert[63] = 101; Bleu[63] = 157;
	Rouge[64] = 218; Vert[64] = 25; Bleu[64] = 23;
	Rouge[65] = 71; Vert[65] = 176; Bleu[65] = 73;
	Rouge[66] = 208; Vert[66] = 76; Bleu[66] = 7;
	Rouge[67] = 87; Vert[67] = 148; Bleu[67] = 143;
	Rouge[68] = 255; Vert[68] = 207; Bleu[68] = 95;
	Rouge[69] = 72; Vert[69] = 41; Bleu[69] = 73;
	Rouge[70] = 255; Vert[70] = 143; Bleu[70] = 95;
	Rouge[71] = 40; Vert[71] = 83; Bleu[71] = 54;
	Rouge[72] = 255; Vert[72] = 175; Bleu[72] = 31;
	Rouge[73] = 69; Vert[73] = 59; Bleu[73] = 111;
	Rouge[74] = 255; Vert[74] = 111; Bleu[74] = 31;
	Rouge[75] = 37; Vert[75] = 101; Bleu[75] = 92;
	Rouge[76] = 196; Vert[76] = 224; Bleu[76] = 121;
	Rouge[77] = 130; Vert[77] = 24; Bleu[77] = 47;
	Rouge[78] = 212; Vert[78] = 107; Bleu[78] = 130;
	Rouge[79] = 82; Vert[79] = 119; Bleu[79] = 19;
	Rouge[80] = 197; Vert[80] = 167; Bleu[80] = 6;
	Rouge[81] = 127; Vert[81] = 67; Bleu[81] = 137;
	Rouge[82] = 213; Vert[82] = 50; Bleu[82] = 15;
	Rouge[83] = 79; Vert[83] = 162; Bleu[83] = 108;
	Rouge[84] = 143; Vert[84] = 184; Bleu[84] = 159;
	Rouge[85] = 162; Vert[85] = 56; Bleu[85] = 15;
	Rouge[86] = 132; Vert[86] = 202; Bleu[86] = 70;
	Rouge[87] = 181; Vert[87] = 18; Bleu[87] = 47;
	Rouge[88] = 159; Vert[88] = 131; Bleu[88] = 168;
	Rouge[89] = 146; Vert[89] = 109; Bleu[89] = 6;
	Rouge[90] = 133; Vert[90] = 177; Bleu[90] = 19;
	Rouge[91] = 180; Vert[91] = 43; Bleu[91] = 98;
	Rouge[92] = 255; Vert[92] = 159; Bleu[92] = 63;
	Rouge[93] = 55; Vert[93] = 71; Bleu[93] = 82;
	Rouge[94] = 137; Vert[94] = 193; Bleu[94] = 114;
	Rouge[95] = 172; Vert[95] = 37; Bleu[95] = 31;
	Rouge[96] = 169; Vert[96] = 87; Bleu[96] = 133;
	Rouge[97] = 140; Vert[97] = 143; Bleu[97] = 12;
	Rouge[98] = 203; Vert[98] = 171; Bleu[98] = 137;
	Rouge[99] = 255; Vert[99] = 255; Bleu[99] = 255;

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
	
	Triangles.resize(N) ;
	
	for (int i = 0 ; i < N ; i++) {
		Triangles[i].resize(N) ;
		for (int j = 0 ; j < N ; j++) {
			Triangles[i][j] = 0 ;
		}
	}
	
	for (int i = 0 ; i < N ; i++) {
		for (int j = 0 ; j < Graph[i].size() ; j++) {
			for (int k = j+1 ; k < Graph[i].size() ; k++) {
				if (i < Graph[i][j]) {
					if (isneigh(Graph[i][j],Graph[i][k])) {
						Triangles[i][Graph[i][j]]++ ;
						Triangles[Graph[i][j]][i]++ ;
						Triangles[i][Graph[i][k]]++ ;
						Triangles[Graph[i][k]][i]++ ;
						Triangles[Graph[i][k]][Graph[i][k]]++ ;
						Triangles[Graph[i][k]][Graph[i][k]]++ ;

					}
				}
			}
		}
	}
	
	int maxtriangles = 0 ;
	
	for (int i = 0 ; i < N ; i++) {
		for (int j = i + 1 ; j < N ; j++) {
			if ( Triangles[i][j] > maxtriangles ) {
				maxtriangles = Triangles[i][j] ;
			}
		}
	}
	
	cout << "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" << endl ;
	cout << "<gexf xmlns:viz=\"http:///www.gexf.net/1.1draft/viz\" version=\"1.1\" xmlns=\"http://www.gexf.net/1.1draft\">" << endl ;
	cout << "<meta lastmodifieddate=\"2010-03-03+23:44\">" << endl ;
	cout << "<creator>Gephi 0.7</creator>" << endl ;
	cout << "</meta>" << endl ;
	cout << "<graph defaultedgetype=\"undirected\" idtype=\"string\" type=\"static\">" << endl ;
	cout << "<attributes class=\"node\">" << endl ;
	cout << "<attribute id=\"0\" title=\"Comm\" type=\"liststring\"/>" << endl ;
	cout << "</attributes>" << endl ;
	cout << "<nodes>" << endl ;

	for (int i = 0 ; i < N ; i++) {
		if (MyComms[i].size() > 0) {
			cout << "<node id = \"" << i << "\" label=\"" << Nodes[i] << "\">" << endl ;
				cout << "<viz:color r='" << 30 <<"' g='"<< 30 <<"' b='"<< 30 <<"'/>" << endl ;
			cout << "<attvalues>" << endl ;
			cout << "<attvalue for=\"0\" value=\"" ;
			cout << MyComms[i][0] ;
			for (int j = 1 ; j < MyComms[i].size() ; j++) {
				cout << ";" << MyComms[i][j] ;
			}
			cout <<"\" />" << endl ;
			cout << "</attvalues>" << endl ;
			cout << "</node>" << endl ;
		}
	}
	for (int i = 0 ; i < C ; i++ ) {
		double cohe = cohesion(i);
		int nb_couleur = 100 ;
		/*cout << "<node id = \""  << N+i << "\" label=\"" << cohe << "\">" << endl ;
		cout << "<viz:color r='" << Rouge[i%nb_couleur] <<"' g='"<< Vert[i%nb_couleur] <<"' b='"<< Bleu[i%nb_couleur] <<"' a='1' />" << endl ;
		cout << "<viz:size value='0.1'/>" <<endl ;
		cout << "</node>" << endl ;*/
	}
	
	
	cout << "</nodes>" << endl ;
			 
	cout << "<edges>" << endl ;
	int k = 0 ;
	for (int i = 0 ; i < N ; i++) {	
		for (int j = 0 ; j < Graph[i].size() ; j++) {
			if (i < Graph[i][j] && MyComms[i].size() > 0 && MyComms[Graph[i][j]].size() > 0) {
				double w = (2.*(double)Triangles[i][Graph[i][j]])/(double)maxtriangles ;
				w = (w < 0.25) ? 0.25 : w ;
				cout << "<edge id=\"" << k++ << "\" source=\""<< i <<"\" target=\"" << Graph[i][j] << "\" weight=\"" << 1 /*(double)(1.+intercomm(i,Graph[i][j]))/(double)(1.+unioncomm(i,Graph[i][j]))*/ << "\"/>" << endl ;
			}
		}
	}
		
	for (int i = 0 ; i < C ; i++ ) {
		for (int j = 0 ; j < Comm[i].size() ; j++) {
			//cout << "<edge label=\"ficelle\" id=\"" << k++ << "\" source=\""<< N+i <<"\" target=\"" << Comm[i][j] << "\" weight=\"" << (sqrt(Comm[i].size())*2*(1-log(cohesion(i)))) << "\">" << endl ;
			//	cout << "<viz:color r='" << 255 <<"' g='"<< 255 <<"' b='"<< 255 <<"' a='0' />" << endl ;
			//cout << "</edge>" << endl ;
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