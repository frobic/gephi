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
import java.util.Arrays;


/**
 *
 * @author Mathieu Bastian
 */
@ServiceProvider(service = ItemBuilder.class, position = 100)
public class NodeBuilder implements ItemBuilder {

    public Item[] getItems(Graph graph, AttributeModel attributeModel) {
		
		
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
		ArrayList<ArrayList<Integer>> CommGraph = new ArrayList<ArrayList<Integer>>();
		
		for (int _i = 0 ; _i <= NbCommunautes ; _i++) {
			Communities.add(new ArrayList<Integer>()) ;
			CommGraph.add(new ArrayList<Integer>()) ;
			Baryx[_i] = 0.0f ;
			Baryy[_i] = 0.0f ;
			Baryz[_i] = 0.0f ;
			CommSize[_i] = 0 ;
		}
		int i_n = 0 ;
		
		int K =(int) Math.ceil(Math.pow(NbCommunautes+5,1./3.)) ;
		ArrayList<Double[]> StockCouleur = new ArrayList<Double[]>() ;
		ArrayList<Double[]> CommCouleurRYB = new ArrayList<Double[]>() ;
		for (int i = 0 ; i < K ; i++) {
			for (int i2 = 0 ; i2 < K ; i2++) {
				for (int i3 = 0 ; i3 < K ; i3++) {
					Double[] couleur = {((double)i/(double)K),((double)i2/(double)K),((double)i3/(double)K)} ; 
					StockCouleur.add(couleur) ;
				}
			}
		}
		
		
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
		
		for (int i = 0 ; i <= NbCommunautes ; i++) {
			CommCouleurRYB.add(StockCouleur.get(0)) ;
		}
		
		StockCouleur.remove(0) ;
		
		for (int i = 0 ; i <= NbCommunautes ; i++) {
			
			ArrayList<Double[]> BeFarTo = new ArrayList<Double[]>() ;
			BeFarTo.add(CommCouleurRYB.get(i)) ;
			
			for (int j = 0 ; j < Communities.get(i).size() ; j++) {
				Node n = nodes[Communities.get(i).get(j)] ;
				if (n.getNodeData().getAttributes().getValue("Comm") != null) {
					StringList temp = (StringList) n.getNodeData().getAttributes().getValue("Comm") ;
					for (int _i = 0 ; _i < temp.size() ; _i++) {
						
						int c = Integer.parseInt(temp.getItem(_i)) ;
						
						boolean addthis = true ;
						
						for (int ii = 0 ; ii < BeFarTo.size() ; ii++) {
							if (java.util.Arrays.equals(CommCouleurRYB.get(c),BeFarTo.get(ii))) {
								addthis = false ;
							}
						}
						
						if (addthis) {
							BeFarTo.add(CommCouleurRYB.get(c)) ;
						}
						
					}
				}
			}
			
			
			int best = 0 ;
			Double[] CouleurVue = StockCouleur.get(0) ;
			Double[] CouleurVueCab = RYBToCab(CouleurVue[0],CouleurVue[1],CouleurVue[2]) ;
			double bestc ;
			double courant = 0. ;
			
			for (int ii = 0 ; ii < BeFarTo.size() ; ii++) {
				Double[] temp = BeFarTo.get(ii) ;
				Double[] Temp2 = RYBToCab(temp[0],temp[1],temp[2]) ;
				courant = courant + Delta(CouleurVueCab,Temp2);
			}
			
			bestc = courant ;
			
			
			
			for (int j = 1 ; j < StockCouleur.size() ; j++) {
				courant = 0. ;
				CouleurVue = StockCouleur.get(j) ;
				CouleurVueCab = RYBToCab(CouleurVue[0],CouleurVue[1],CouleurVue[2]) ;
				for (int ii = 0 ; ii < BeFarTo.size() ; ii++) {
					Double[] temp = BeFarTo.get(ii) ;
					Double[] Temp2 = RYBToCab(temp[0],temp[1],temp[2]) ;
					courant = courant + Delta(CouleurVueCab,Temp2);
				}
				if (courant > bestc) {
					best = j ;
					bestc = courant ;
				}
			}
			
			CommCouleurRYB.remove(i) ;
			CommCouleurRYB.add(i,StockCouleur.get(best)) ;
			StockCouleur.remove(best) ;
			
		}
		System.err.println(Arrays.toString(RGBToCab(1,0,0))) ;

		System.err.println(Arrays.toString(RGBToCab(0,1,0))) ;
		System.err.println(Arrays.toString(RGBToCab(0,0,1))) ;

		
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
				Double[] Couleur = CommCouleurRYB.get(Communautes[select]) ;
				colortab[_i] = new Color((int)Math.floor(255*RYBToR(Couleur[0],Couleur[1],Couleur[2])),(int)Math.floor(255*RYBToG(Couleur[0],Couleur[1],Couleur[2])),(int)Math.floor(255*RYBToB(Couleur[0],Couleur[1],Couleur[2])),255) ;
				
				//System.err.println(Couleur[0]+" "+Couleur[1]+" "+Couleur[2]) ;
				
				//colortab[_i] = new Color(Rouge[Communautes[select]%100],Vert[Communautes[select]%100],Bleu[Communautes[select]%100],255) ;

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
				Double[] Couleur = CommCouleurRYB.get(d) ;
				colortab[0] = new Color((int)Math.floor(255*RYBToR(Couleur[0],Couleur[1],Couleur[2])),(int)Math.floor(255*RYBToG(Couleur[0],Couleur[1],Couleur[2])),(int)Math.floor(255*RYBToB(Couleur[0],Couleur[1],Couleur[2])),255) ;
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
	
	public Double[] RYBToRGB(double r, double y, double b) {
		Double[] retour = { 1 * (1 - r) * (1 - b) * (1 - y) + 1 * r * (1 - b) * (1 - y) + 0.163 * (1 - r) * b * (1 - y) + 0.5 * r * b * (1 - y) + 1 * (1 - r) * (1 - b) * y + 1 * r * (1 - b) * y + 0 * (1 - r) * b * y + 0.2 * r * b * y,1 * (1 - r) * (1 - b) * (1 - y) + 0 * r * (1 - b) * (1 - y) + 0.373 * (1 - r) * b * (1 - y) + 0 * r * b * (1 - y) + 1 * (1 - r) * (1 - b) * y + 0.5 * r * (1 - b) * y + 0.66 * (1 - r) * b * y + 0.094 * r * b * y,1 * (1 - r) * (1 - b) * (1 - y) + 0 * r * (1 - b) * (1 - y) + 0.6 * (1 - r) * b * (1 - y) + 0.5 * r * b * (1 - y) + 0 * (1 - r) * (1 - b) * y + 0 * r * (1 - b) * y + 0.2 * (1 - r) * b * y + 0 * r * b * y } ;
		return retour ;
	}
	
	public Double[] RGBToXYZ(double r, double g, double b) {
		double var_R = r ;
		double var_G = g ;
		double var_B = b ;
		
		if (var_R > 0.04045) {
			var_R = Math.pow((var_R+0.055)/1.055,2.4);
		}
		else {
			var_R = var_R / 12.92 ;
		}
		
		if (var_G > 0.04045) {
			var_G = Math.pow((var_G+0.055)/1.055,2.4);
		}
		else {
			var_G = var_G / 12.92 ;
		}
		
		if (var_B > 0.04045) {
			var_B = Math.pow((var_B+0.055)/1.055,2.4);
		}
		else {
			var_B = var_B / 12.92 ;
		}
		
		var_R = var_R * 100. ;
		var_G = var_G * 100. ;
		var_B = var_B * 100. ;
		
		Double[] retour = {var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805,var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722,var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505} ;
		
		return retour ;
	}
	
	public Double[] XYZToCab(double x , double y , double z) {
		double var_X = x/95.047 ;
		double var_Y = y/100.000 ;
		double var_Z = z/108.883 ;
		
		if (var_X > 0.008856) {
			var_X = Math.pow(var_X,1./3.);
		}
		else {
			var_X = ( 7.787 * var_X ) + ( 16. / 116. ) ;
		}
		
		if (var_Y > 0.008856) {
			var_Y = Math.pow(var_Y,1./3.);
		}
		else {
			var_Y = ( 7.787 * var_Y ) + ( 16. / 116. ) ;
		}
		
		if (var_Z > 0.008856) {
			var_Z = Math.pow(var_Z,1./3.);
		}
		else {
			var_Z = ( 7.787 * var_Z ) + ( 16. / 116. ) ;
		}
		
		Double[] retour = {( 116. * var_Y ) - 16.,500. * ( var_X - var_Y ),200. * ( var_Y - var_Z )} ;
		
		return retour ;
	}
	
	public Double[] RYBToCab(double r, double y, double b) {
		Double[] RGB =  RYBToRGB(r,y,b) ;
		Double[] XYZ = RGBToXYZ(RGB[0],RGB[1],RGB[2]) ;
		Double[] Cab = XYZToCab(XYZ[0],XYZ[1],XYZ[2]) ;
		
		return Cab ;
	}
	
	public Double[] RGBToCab(double r, double g, double b) {
		Double[] XYZ = RGBToXYZ(r,g,b) ;
		Double[] Cab = XYZToCab(XYZ[0],XYZ[1],XYZ[2]) ;
		
		return Cab ;
	}
	
	public double Delta(Double[] C1, Double[] C2) {
		
		double DeltaL = C1[0] - C2[0] ;
		double C1s = Math.sqrt(C1[1]*C1[1]+C1[2]*C1[2]) ;
		double C2s = Math.sqrt(C2[1]*C2[1]+C2[2]*C2[2]) ;
		double DeltaC = C1s - C2s ;
		double Deltaa = C1[1] - C2[1] ;
		double Deltab = C1[2] - C2[2] ;
		double DeltaH = Math.sqrt(Deltaa*Deltaa + Deltab*Deltab - DeltaC*DeltaC) ;
		
		return Math.sqrt(DeltaL*DeltaL+ (DeltaC/(1+0.045*C1s))*(DeltaC/(1+0.045*C1s))+ (DeltaH/(1+0.015*C1s))*(DeltaH/(1+0.015*C1s))) ;
		//return Math.sqrt((C1[0]-C2[0])*(C1[0]-C2[0])+ (C1[1]-C2[1])*(C1[1]-C2[1])+ (C1[2]-C2[2])*(C1[2]-C2[2])) ;
		
	}
	
	public double RYBToR(double r, double y, double b) {
		return 1 * (1 - r) * (1 - b) * (1 - y) + 1 * r * (1 - b) * (1 - y) + 0.163 * (1 - r) * b * (1 - y) + 0.5 * r * b * (1 - y) + 1 * (1 - r) * (1 - b) * y + 1 * r * (1 - b) * y + 0 * (1 - r) * b * y + 0.2 * r * b * y ;
    }
	public double RYBToG(double r, double y, double b) {
		return 1 * (1 - r) * (1 - b) * (1 - y) + 0 * r * (1 - b) * (1 - y) + 0.373 * (1 - r) * b * (1 - y) + 0 * r * b * (1 - y) + 1 * (1 - r) * (1 - b) * y + 0.5 * r * (1 - b) * y + 0.66 * (1 - r) * b * y + 0.094 * r * b * y ;
    }
	public double RYBToB(double r, double y, double b) {
		return 1 * (1 - r) * (1 - b) * (1 - y) + 0 * r * (1 - b) * (1 - y) + 0.6 * (1 - r) * b * (1 - y) + 0.5 * r * b * (1 - y) + 0 * (1 - r) * (1 - b) * y + 0 * r * (1 - b) * y + 0.2 * (1 - r) * b * y + 0 * r * b * y ;
    }
	
}
