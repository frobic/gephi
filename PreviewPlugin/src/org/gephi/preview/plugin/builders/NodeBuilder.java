/*
Copyright 2008-2011 Gephi
Authors : Mathieu Bastian
Website : http://www.gephi.org

This file is part of Gephi.

DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2011 Gephi Consortium. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 3 only ("GPL") or the Common
Development and Distribution License("CDDL") (collectively, the
"License"). You may not use this file except in compliance with the
License. You can obtain a copy of the License at
http://gephi.org/about/legal/license-notice/
or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
specific language governing permissions and limitations under the
License.  When distributing the software, include this License Header
Notice in each file and include the License files at
/cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
License Header, with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

If you wish your version of this file to be governed by only the CDDL
or only the GPL Version 3, indicate your decision by adding
"[Contributor] elects to include this software in this distribution
under the [CDDL or GPL Version 3] license." If you do not indicate a
single choice of license, a recipient has the option to distribute
your version of this file under either the CDDL, the GPL Version 3 or
to extend the choice of license to its licensees as provided above.
However, if you add GPL Version 3 code and therefore, elected the GPL
Version 3 license, then the option applies only if the new code is
made subject to such option by the copyright holder.

Contributor(s):

Portions Copyrighted 2011 Gephi Consortium.
 */
package org.gephi.preview.plugin.builders;

import java.awt.Color;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.EdgeData;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeData;
import org.gephi.preview.api.Item;
import org.gephi.preview.plugin.items.NodeItem;
import org.gephi.preview.spi.ItemBuilder;
import org.gephi.data.attributes.type.StringList ;
import org.openide.util.lookup.ServiceProvider;
import java.lang.String;
import java.util.AbstractList;
import java.util.ArrayList;


/**
 *
 * @author Mathieu Bastian
 */
@ServiceProvider(service = ItemBuilder.class, position = 100)
public class NodeBuilder implements ItemBuilder {

    public Item[] getItems(Graph graph, AttributeModel attributeModel) {
		
		int[] Rouge = new int[100] ;
		int[] Vert = new int[100] ;
		int[] Bleu = new int[100] ;
		
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
		
		int NbCommunautes = 0 ;
		
		for (Node n : graph.getNodes()) {
			if (n.getNodeData().getAttributes().getValue("Comm") != null) {
				StringList temp = (StringList) n.getNodeData().getAttributes().getValue("Comm") ;
				for (int _i = 0 ; _i < temp.size() ; _i++) {
					int c = Integer.parseInt(temp.getItem(_i)) ;
					if (c > NbCommunautes) {
						NbCommunautes = c ;
					}
				}
			}
		}
			
		double[] Baryx = new double[NbCommunautes+1] ; 
		double[] Baryy = new double[NbCommunautes+1];
		double[] Baryz = new double[NbCommunautes+1];
		int[] CommSize = new int[NbCommunautes+1];
		ArrayList<ArrayList<Integer>> Communities = new ArrayList<ArrayList<Integer>>();
		
		for (int _i = 0 ; _i <= NbCommunautes ; _i++) {
			Communities.add(new ArrayList<Integer>()) ;
			Baryx[_i] = 0.0f ;
			Baryy[_i] = 0.0f ;
			Baryz[_i] = 0.0f ;
			CommSize[_i] = 0 ;
		}
		int i_n = 0 ;
		
		for (Node n : graph.getNodes()) {
			if (n.getNodeData().getAttributes().getValue("Comm") != null) {
				StringList temp = (StringList) n.getNodeData().getAttributes().getValue("Comm") ;
				for (int _i = 0 ; _i < temp.size() ; _i++) {
					int c = Integer.parseInt(temp.getItem(_i)) ;
					Communities.get(c).add(i_n);
					Baryx[c] = Baryx[c]+n.getNodeData().x() ;
					Baryy[c] = Baryy[c]-n.getNodeData().y() ;
					Baryz[c] = Baryz[c]+n.getNodeData().z() ;
					CommSize[c]++ ;
				}
				i_n++ ;
			}
		}
		
		Node[] nodes = graph.getNodes().toArray();
		
		/*System.err.println(nodes.length);
		
        for (Node n : graph.getNodes()) {
            System.err.println(n.getNodeData().x());
            System.err.println(-n.getNodeData().y());
		}*/
		
		int first ;
		
		float x ;
		float y ;
		System.err.println("###################") ;

		ArrayList<ArrayList<Integer>> ConvexHullList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Float> AreaHull = new ArrayList<Float>() ;
		for (int i = 0 ; i < Communities.size() ; i++) {
			first = 0 ;
			Node noeud = nodes[Communities.get(i).get(0)] ;
			x = noeud.getNodeData().x() ;
			y = noeud.getNodeData().y() ;
			for (int j = 1 ; j < Communities.get(i).size() ; j++) {
				Node n = nodes[Communities.get(i).get(j)] ;
				if (n.getNodeData().x() < x || (n.getNodeData().x() == x && n.getNodeData().y() < y )) {
					x = n.getNodeData().x() ;
					y = n.getNodeData().y() ;
					first = j ;
				}
			}
			//System.err.println(first) ;
			
			ArrayList<Integer> ConvexHull = new ArrayList<Integer>();
			int courant = first ;
			ConvexHull.add(Communities.get(i).get(courant)) ;
			do {
				int recherche = 0 ;
				for (int j = 1 ; j < Communities.get(i).size() ; j++) {
					NodeData p1 = nodes[Communities.get(i).get(courant)].getNodeData() ;
					NodeData p2 = nodes[Communities.get(i).get(recherche)].getNodeData() ;
					NodeData p3 = nodes[Communities.get(i).get(j)].getNodeData() ;
					if (courant == recherche) {
						recherche = j ;
					}
					else if ((p2.x() - p1.x())*(p3.y() - p1.y()) - (p3.x() - p1.x())*(p2.y() - p1.y()) > 0) {
						recherche = j ;
					}
				}
				courant = recherche ;
				ConvexHull.add(Communities.get(i).get(courant)) ;
				

			} while ( courant != first);
			
			
			ConvexHullList.add(ConvexHull) ;
			float Aire = 0f ;
			
			for (int j = 0 ; j < ConvexHull.size()-1 ; j++) {
				NodeData p0 = nodes[ConvexHull.get(j)].getNodeData() ;
				NodeData p1 = nodes[ConvexHull.get(j+1)].getNodeData() ;
				Aire = Aire + p0.x()*p1.y() - p1.x()*p0.y() ;
			}
			AreaHull.add(-Aire) ;
			
			int intersect = 0 ;
			
			for (Node n : graph.getNodes()) {
				boolean inc = true ;
				if (n.getNodeData().getAttributes().getValue("Comm") != null) {
					StringList temp = (StringList) n.getNodeData().getAttributes().getValue("Comm") ;
					for (int _i = 0 ; _i < temp.size() ; _i++) {
						int c = Integer.parseInt(temp.getItem(_i)) ;
						if (c == i) {
							inc = false ;
						}
					}
				
					if (inc) {
						boolean inp = true ;
						for (int j = 0 ; j < ConvexHull.size()-1 ; j++) {
							NodeData p1 = nodes[ConvexHull.get(j)].getNodeData() ;
							NodeData p2 = nodes[ConvexHull.get(j+1)].getNodeData() ;
							NodeData p3 = n.getNodeData() ;
							if ((p2.x() - p1.x())*(p3.y() - p1.y()) - (p3.x() - p1.x())*(p2.y() - p1.y()) > 0) {
								inp = false ;
							}
						}
						if (inp) {
							intersect++ ;
						}
					}
				}
			}
			System.err.println(intersect+" "+Communities.get(i).size()+" "+(float)intersect/(float)Communities.get(i).size()) ;

			
		}
		
		System.err.println("###################") ;
		
		for (int i = 0 ; i < AreaHull.size() ; i++) {
			System.err.println(AreaHull.get(i)+" "+AreaHull.get(i) / Communities.get(i).size()) ;
		}

		
		/*System.err.println(Communities.size()) ;
		for (int _i = 0 ; _i < Communities.size() ; _i++) {
			
			String s = "" ;
			
			for (int _j = 0 ; _j < Communities.get(_i).size() ; _j++) {
				s = s + Communities.get(_i) + " " ;
			}
			System.err.println(s) ;
		}*/
		
        Item[] items = new NodeItem[graph.getNodeCount()];
        int i = 0;
		int d = 0 ;
        for (Node n : graph.getNodes()) {
            NodeItem nodeItem = new NodeItem(n.getNodeData().getRootNode());
            nodeItem.setData(NodeItem.X, n.getNodeData().x());
            nodeItem.setData(NodeItem.Y, -n.getNodeData().y());
            nodeItem.setData(NodeItem.Z, n.getNodeData().z());
            nodeItem.setData(NodeItem.SIZE, n.getNodeData().getSize() * 2f);
			Color[] colortab ;
			int[] Communautes ;
			double[] Angles ;
			double Polynome = 0.0 ;
			int j = 0 ;
			if (n.getNodeData().getAttributes().getValue("Comm") != null) {
				StringList temp = (StringList) n.getNodeData().getAttributes().getValue("Comm") ;
				j = temp.size() ;
				Communautes = new int[j] ;
				Angles = new double[j] ;
				for (int _i = 0 ; _i < temp.size() ; _i++) {
					Communautes[_i] = Integer.parseInt(temp.getItem(_i)) ;
					double sca10 =  ((Baryx[Communautes[_i]]-n.getNodeData().x())/((double) (CommSize[Communautes[_i]]-1))-n.getNodeData().x()) ;
					double sca01 =  ((Baryy[Communautes[_i]]+n.getNodeData().y())/((double) (CommSize[Communautes[_i]]-1))+n.getNodeData().y()) ;
					double size =  Math.sqrt(sca10*sca10 + sca01*sca01) ;
					sca10 = sca10 / size ;
					sca01 = sca01 / size ;
					double ang = Math.acos(sca10) ;
					if (sca01 > 0) {
						ang = 2.0*(Math.PI) -ang ;
					}
					Angles[_i] = ang ;
				}
				colortab= new Color[j];
			}
			else {
				Communautes = new int[0] ;
				Angles = new double[0] ;
				colortab= new Color[1];
			}
			nodeItem.setData(NodeItem.NBCOLOR,j) ;
			int[] bool = new int[j] ;
			int[] ord = new int[j] ;
			for (int _i = 0 ; _i < j ; _i++) {
				bool[_i] = 1 ;
			}
			for (int _i = 0 ; _i < j ; _i++) {
				double min = 7f ;
				int select = 0;
				for (int _j = 0 ; _j < j ; _j++) {
					if ( min > Angles[_j] && bool[_j] == 1) {
						select = _j ;
						min = Angles[_j] ;
					}
				}
				Polynome = Polynome +(Angles[select]-((double)(2*_i + 1)*Math.PI/(double) j)) ;
				if (_i == 0 && j != 0) {
					//nodeItem.setData(NodeItem.ANGLE,(float)(Angles[select]-(Math.PI/(double) j))) ;
				}
				colortab[_i] = new Color(Rouge[Communautes[select]%100],Vert[Communautes[select]%100],Bleu[Communautes[select]%100],255) ;
				bool[select] = 0 ;
				ord[_i] = select ;
			}
			
			nodeItem.setData(NodeItem.ANGLE,(float)(Polynome/((double) j))) ;
			
			/*for (Edge e : graph.getEdges(n)) {
				String verif = "ficelle" ;
				EdgeData ed = e.getEdgeData() ;
				String edLab = ed.getLabel() ;
				if (verif.equals(edLab)) {
					Node n2 = e.getSource() ;
					colortab[j] = new Color((int) (n2.getNodeData().r() * 255),
											(int) (n2.getNodeData().g() * 255),
											(int) (n2.getNodeData().b() * 255),
											(int) (n2.getNodeData().alpha() * 255));
					j++ ;
				}
				else {
					comm = 1 ;
				}
			}*/
			nodeItem.setData(NodeItem.COLORS,colortab) ;
			if (j == 0) {
				nodeItem.setData(NodeItem.X, (float) (Baryx[d]/(double)CommSize[d]));
				nodeItem.setData(NodeItem.Y, (float) (Baryy[d]/(double)CommSize[d]));
				nodeItem.setData(NodeItem.Z, n.getNodeData().z());
				nodeItem.setData(NodeItem.NBCOLOR,1) ;
				colortab[0] = new Color(Rouge[d%100],Vert[d%100],Bleu[d%100],255) ;
				nodeItem.setData(NodeItem.COLORS,colortab) ;
			nodeItem.setData(NodeItem.COLOR, new Color((int) (n.getNodeData().r() * 255),
                    (int) (n.getNodeData().g() * 255),
                    (int) (n.getNodeData().b() * 255),
                    (int) (n.getNodeData().alpha() * 0)));
				nodeItem.setData(NodeItem.SIZE, 0f);
				nodeItem.setData(NodeItem.ANGLE,0f) ;
				d++ ;
			}
			else {
				nodeItem.setData(NodeItem.COLOR, new Color((int) (n.getNodeData().r() * 255),
														   (int) (n.getNodeData().g() * 255),
														   (int) (n.getNodeData().b() * 255),
														   (int) (n.getNodeData().alpha() * 255)));	
			}
            items[i++] = nodeItem;
        }
        return items;
    }

    public String getType() {
        return ItemBuilder.NODE_BUILDER;
    }
}
