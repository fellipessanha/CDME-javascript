///////////////////////////////////////////////////////////////////////////////
// PjShadow Interface class
//
// Last update: 13/01/2008
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
// Native JAVA imports
///////////////////////////////////////////////////////////////////////////////
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import java.awt.event.ItemListener;

///////////////////////////////////////////////////////////////////////////////
// JavaView imports
///////////////////////////////////////////////////////////////////////////////
import jv.geom.PgElementSet;
import jv.geom.PgPolygonSet;
import jv.object.PsPanel;
import jv.objectGui.PsTabPanel;
import jv.object.PsUpdateIf;
import jv.project.PjProject_IP;
import jvx.geom.PwCleanMesh;
import jvx.geom.PwClip;
import jvx.geom.PwModel;
import jvx.geom.PwTransform;
import jvx.geom.PwUnfold;


///////////////////////////////////////////////////////////////////////////////
// Info panel for graph tomography applet.
///////////////////////////////////////////////////////////////////////////////
public class PjShadow_IP extends PjProject_IP implements ActionListener, ItemListener
{
   protected PjShadow m_pjShadow;

   protected Choice poly_category;
   protected Choice poly_name;

   protected Panel m_aPanel; // Transparency panel
   protected Panel m_pPanel; // Perspective panel
   protected Checkbox viewElementsCB;
   protected Checkbox viewEdgesCB;
   protected Checkbox viewVerticesCB;
   
   protected Checkbox viewProjectionXY;
   protected Checkbox viewProjectionXZ;
   protected Checkbox viewProjectionYZ;
   
   protected Panel m_rot1Panel;
   protected Panel m_rot2Panel;
   protected Panel m_rot3Panel;

   protected PsTabPanel tabPanel;

   protected int idiom = 0;
   protected String[] title_trans = {"Projeções Ortogonais",
                                     "Orthogonal Projections"};

   protected String[] vis_transparency_trans = {"Transparência:",
                                                "Transparency:"};
   protected String[] vis_perspective_trans = {"Perspectividade:",
                                               "Perspectivity:"};
   
   protected String[] vis_vertices_trans = {"Exibir vértices",
                                            "Show vertices"};
   protected String[] vis_edges_trans = {"Exibir arestas", "Show edges"};
   protected String[] vis_elements_trans = {"Exibir faces", "Show faces"};
   protected String[] vis_projectionXY_trans = {"Exibir projeção no plano xy", "Show projection in the plane xy"};
   protected String[] vis_projectionXZ_trans = {"Exibir projeção no plano xz", "Show projection in the plane xz"};
   protected String[] vis_projectionYZ_trans = {"Exibir projeção no plano yz", "Show projection in the plane yz"};

   protected String[] vis_panel_trans = {"Visualização", "Visualize"};
   protected String[] rot_panel_trans = {"Rotação", "Rotate"};
   protected String[] pro_panel_trans = {"Projeção", "Projection"};
   protected String[] rot1_trans = {"Controle 1:", "Control 1:"};   
   protected String[] rot2_trans = {"Controle 2:", "Control 2:"};
   protected String[] rot3_trans = {"Controle 3:", "Control 3:"};  
   
   protected Button showAllOrthogonalButton;
   protected Button hideAllOrthogonalButton;
   
   protected Button resetRotateControlsButton;

   protected String[] object_trans = {"Objetos de Estudo", "Objects of Study"};
   protected String[] curves_trans = {"Curvas no Espaço", "Space Curves"};
   protected String[] platonic_trans =
                {"Sólidos Platônicos", "Platonic Solids"};
   protected String[] archimedean_trans =
                {"Sólidos Arquimedianos", "Archimedean Solids"};
   protected String[] johnson_trans =
                {"Sólidos de Johnson", "Johnson Solids"};
   protected String[] catalan_trans =
                {"Sólidos de Catalan", "Catalan Solids"};
   protected String[] prisms_trans =
                {"Prismas", "Prisms"};
   protected String[] antiprisms_trans =
                {"Antiprismas", "Antiprisms"};
   protected String[] pyramids_trans =
                {"Pirâmides", "Pyramids"};      
// protected String[] nonregular_trans =
//              {"Poliedros Convexos Não Regulares", "Nonregular Convex Polyhedra"};
   protected String[] toroids_trans =
                {"Toróides", "Toroids"};   
// protected String[] stewart_trans =
//              {"Toróides de Stewart", "Stewart Toroids"};
   protected String[] leonardo_trans =
                {"Cosmogramas de Leonardo", "Leonardo Cosmograms"};
   protected String[] animal_trans =
                {"Animais", "Animals"};
   protected String[] hexahedra_trans =
                {"Hexaedros", "Hexahedra"};         
   protected String[] isohedra_trans =
                {"Isoedros", "Isohedra"};            
   protected String[] triplets_trans = {"Trip-lets", "Trip-lets"};   
   protected String[] show_all_orthogonal_trans = 
                {"Exibir todos os segmentos ortogonais", "Show all orthogonal segments"};
   protected String[] hide_all_orthogonal_trans = 
                {"Esconder todos os segmentos ortogonais", "Hide all orthogonal segments"};
   protected String[] curves_label_trans = {"Exibir curva", "Show curve"};
   protected String[] reset_rotate_controls_trans = {"Reiniciar!", "Reset!"};

   protected Category object;
   protected Category curves;   
   protected Category platonic;
   protected Category archimedean;
   protected Category johnson;
   protected Category catalan;
   protected Category prisms;
   protected Category antiprisms;
   protected Category pyramids;   
// protected Category nonregular;
   protected Category toroids;   
// protected Category stewart;
   protected Category leonardo;
   protected Category animal;
   protected Category hexahedra;
   protected Category isohedra;
   protected Category triplets;
   protected Model model;


   ///////////////////////////////////////////////////////////////////////////
   // Default constructor
   ///////////////////////////////////////////////////////////////////////////
   public PjShadow_IP()
   {
      super();

      if (getClass() == PjShadow_IP.class)
      {
         init();
      };
   }; // End of PjShadow_IP()


   //////////////////////////////////////////////////////////////////////////
   // Setup the main control panel
   //////////////////////////////////////////////////////////////////////////
   public void init()
   {
      super.init();

      // Title
      addTitle(title_trans[idiom]);

      add(new Label(" "));

      poly_category = new Choice();
      add(poly_category);
      poly_category.addItemListener(this);

      poly_name = new Choice();
      add(poly_name);
      poly_name.addItemListener(this);

      add(new Label(" "));

      tabPanel = new PsTabPanel();
      add(tabPanel);
   }; // End of init()


   //////////////////////////////////////////////////////////////////////////
   // Setup parent and add the level slider
   //////////////////////////////////////////////////////////////////////////
   public void setParent(PsUpdateIf parent)
   {
      super.setParent(parent);
      m_pjShadow = (PjShadow) parent;
      idiom = m_pjShadow.idiom;

      // Category and name of the solid
      poly_category.add(object_trans[idiom]);
      poly_category.add(curves_trans[idiom]);      
      poly_category.add(platonic_trans[idiom]);
      poly_category.add(archimedean_trans[idiom]);
      poly_category.add(johnson_trans[idiom]);
      poly_category.add(catalan_trans[idiom]);
      poly_category.add(prisms_trans[idiom]);
      poly_category.add(antiprisms_trans[idiom]);
      poly_category.add(pyramids_trans[idiom]);      
//    poly_category.add(nonregular_trans[idiom]);
      poly_category.add(toroids_trans[idiom]);      
//    poly_category.add(stewart_trans[idiom]);
      poly_category.add(leonardo_trans[idiom]);
      poly_category.add(animal_trans[idiom]);
      poly_category.add(hexahedra_trans[idiom]);
      poly_category.add(isohedra_trans[idiom]);
      poly_category.add(triplets_trans[idiom]);
      
      poly_category.select(m_pjShadow.category);
      buildDataBase();
      buildChoice(m_pjShadow.category);
      poly_name.select(m_pjShadow.solid);
      loadModel(m_pjShadow.category, m_pjShadow.solid);

      poly_category.setEnabled(m_pjShadow.enable_choice);
      poly_name.setEnabled(m_pjShadow.enable_choice);


      // Visualization panel
      PsPanel visPanel = new PsPanel();
      
      visPanel.add(new Label(" "));
      
      viewVerticesCB = new Checkbox(vis_vertices_trans[idiom]);
      viewVerticesCB.addItemListener(this);
      viewVerticesCB.setState(true);
      visPanel.add(viewVerticesCB);

      viewEdgesCB = new Checkbox(vis_edges_trans[idiom]);
      viewEdgesCB.addItemListener(this);
      viewEdgesCB.setState(true);
      visPanel.add(viewEdgesCB);

      viewElementsCB = new Checkbox(vis_elements_trans[idiom]);
      viewElementsCB.addItemListener(this);
      viewElementsCB.setState(true);
      visPanel.add(viewElementsCB);
      
      visPanel.add(new Label(" "));      
      
      visPanel.addLine(1);
      
      m_aPanel = new Panel();
      m_aPanel.setLayout(new GridLayout(1, 1));
      visPanel.add(m_aPanel);
      m_aPanel.removeAll();
      m_pjShadow.transparency.setName(vis_transparency_trans[idiom]);
      m_aPanel.add(m_pjShadow.transparency.newInspector(PsPanel.INFO_EXT));

      m_pPanel = new Panel();
      m_pPanel.setLayout(new GridLayout(1, 1));
      visPanel.add(m_pPanel);
      m_pPanel.removeAll();
      m_pjShadow.perspective.setName(vis_perspective_trans[idiom]);
      m_pPanel.add(m_pjShadow.perspective.newInspector(PsPanel.INFO_EXT));
      
      visPanel.addLine(1);

      tabPanel.addPanel(vis_panel_trans[idiom], visPanel);


      // Rotate panel
      PsPanel rotPanel = new PsPanel();
      
      rotPanel.add(new Label(" "));
      
      
      m_rot1Panel = new Panel();
      m_rot1Panel.setLayout(new GridLayout(1, 1));
      rotPanel.add(m_rot1Panel);
      m_rot1Panel.removeAll();
      m_pjShadow.rot1.setName(rot1_trans[idiom]);
      m_rot1Panel.add(m_pjShadow.rot1.newInspector(PsPanel.INFO_EXT));

      m_rot2Panel = new Panel();
      m_rot2Panel.setLayout(new GridLayout(1, 1));
      rotPanel.add(m_rot2Panel);
      m_rot2Panel.removeAll();
      m_pjShadow.rot2.setName(rot2_trans[idiom]);
      m_rot2Panel.add(m_pjShadow.rot2.newInspector(PsPanel.INFO_EXT));

      m_rot3Panel = new Panel();
      m_rot3Panel.setLayout(new GridLayout(1, 1));
      rotPanel.add(m_rot3Panel);
      m_rot3Panel.removeAll();
      m_pjShadow.rot3.setName(rot3_trans[idiom]);
      m_rot3Panel.add(m_pjShadow.rot3.newInspector(PsPanel.INFO_EXT));
      
      rotPanel.add(new Label(" "));
      
      resetRotateControlsButton = new Button(reset_rotate_controls_trans[idiom]);
      resetRotateControlsButton.addActionListener(this);      
      rotPanel.add(resetRotateControlsButton);      
      
      tabPanel.addPanel(rot_panel_trans[idiom], rotPanel);      
      

      // Projection panel
      PsPanel proPanel = new PsPanel();
      
      proPanel.add(new Label(" "));
      
      viewProjectionXY = new Checkbox(vis_projectionXY_trans[idiom]);
      viewProjectionXY.addItemListener(this);
      viewProjectionXY.setState(true);
      proPanel.add(viewProjectionXY);
      
      viewProjectionXZ = new Checkbox(vis_projectionXZ_trans[idiom]);
      viewProjectionXZ.addItemListener(this);
      viewProjectionXZ.setState(true);
      proPanel.add(viewProjectionXZ);
      
      viewProjectionYZ = new Checkbox(vis_projectionYZ_trans[idiom]);
      viewProjectionYZ.addItemListener(this);
      viewProjectionYZ.setState(true);
      proPanel.add(viewProjectionYZ);
      
      proPanel.add(new Label(" "));

      proPanel.addLine(1);
      
      // Configuring buttons      
      showAllOrthogonalButton = new Button(show_all_orthogonal_trans[idiom]);
      showAllOrthogonalButton.addActionListener(this);      
      proPanel.add(showAllOrthogonalButton);
            
      hideAllOrthogonalButton = new Button(hide_all_orthogonal_trans[idiom]);
      hideAllOrthogonalButton.addActionListener(this);      
      proPanel.add(hideAllOrthogonalButton);
      
      proPanel.add(new Label(" "));
      
      tabPanel.addPanel(pro_panel_trans[idiom], proPanel);
      
      // Validate
      tabPanel.validate();

      super.setParent(parent);
   }; // End of setParent()

   //////////////////////////////////////////////////////////////////////////
   // Load model
   //////////////////////////////////////////////////////////////////////////
   public void loadModel(String category, String solid)
   {	   
		  if (category.equals(object_trans[idiom]))
		  {
			  for (int i = 0; i < object.getNumberOfModels(); i++)
			  {
				  if (object.getModel(i).getName(idiom).equals(solid))
				  {
					  model = object.getModel(i);
					  break;
				  }
			  }
		  }

		  if (category.equals(curves_trans[idiom]))
		  {
			  for (int i = 0; i < curves.getNumberOfModels(); i++)
			  {
				  if (curves.getModel(i).getName(idiom).equals(solid))
				  {
					  model = curves.getModel(i);
					  break;
				  }
			  }
		  }
		  
	   
		  if (category.equals(platonic_trans[idiom]))
		  {
			  for (int i = 0; i < platonic.getNumberOfModels(); i++)
			  {
				  if (platonic.getModel(i).getName(idiom).equals(solid))
				  {
					  model = platonic.getModel(i);
					  break;
				  }
			  }
		  }

		  if (category.equals(archimedean_trans[idiom]))
		  {
			  for (int i = 0; i < archimedean.getNumberOfModels(); i++)
			  {
				  if (archimedean.getModel(i).getName(idiom).equals(solid))
				  {
					  model = archimedean.getModel(i);
					  break;
				  }
			  }
		  }

		  if (category.equals(animal_trans[idiom]))
		  {
			  for (int i = 0; i < animal.getNumberOfModels(); i++)
			  {
				  if (animal.getModel(i).getName(idiom).equals(solid))
				  {
					  model = animal.getModel(i);
					  break;
				  }
			  }
		  }
		  
		  if (category.equals(hexahedra_trans[idiom]))
		  {
			  for (int i = 0; i < hexahedra.getNumberOfModels(); i++)
			  {
				  if (hexahedra.getModel(i).getName(idiom).equals(solid))
				  {
					  model = hexahedra.getModel(i);
					  break;
				  }
			  }
		  }	  
		  
		  if (category.equals(isohedra_trans[idiom]))
		  {
			  for (int i = 0; i < isohedra.getNumberOfModels(); i++)
			  {
				  if (isohedra.getModel(i).getName(idiom).equals(solid))
				  {
					  model = isohedra.getModel(i);
					  break;
				  }
			  }
		  }	  

		  if (category.equals(johnson_trans[idiom]))
		  {
			  for (int i = 0; i < johnson.getNumberOfModels(); i++)
			  {
				  if (johnson.getModel(i).getName(idiom).equals(solid))
				  {
					  model = johnson.getModel(i);
					  break;
				  }
			  }
		  }

	      if (category.equals(catalan_trans[idiom]))
	          {
	                  for (int i = 0; i < catalan.getNumberOfModels(); i++)
	                  {
	                          if (catalan.getModel(i).getName(idiom).equals(solid))
	                          {
	                                  model = catalan.getModel(i);
	                                  break;
	                          }
	                  }
	          }

	          if (category.equals(prisms_trans[idiom]))
	          {
	                  for (int i = 0; i < prisms.getNumberOfModels(); i++)
	                  {
	                          if (prisms.getModel(i).getName(idiom).equals(solid))
	                          {
	                                  model = prisms.getModel(i);
	                                  break;
	                          }
	                  }
	          }

	          if (category.equals(antiprisms_trans[idiom]))
	         {
	                 for (int i = 0; i < antiprisms.getNumberOfModels(); i++)
	                 {
	                         if (antiprisms.getModel(i).getName(idiom).equals(solid))
	                         {
	                                 model = antiprisms.getModel(i);
	                                 break;
	                         }
	                 }
	         }

	          if (category.equals(pyramids_trans[idiom]))
	          {
	                  for (int i = 0; i < pyramids.getNumberOfModels(); i++)
	                  {
	                          if (pyramids.getModel(i).getName(idiom).equals(solid))
	                          {
	                                  model = pyramids.getModel(i);
	                                  break;
	                          }
	                  }
	          }          
	          
	/*          
	          if (category.equals(nonregular_trans[idiom]))
	          {
	                  for (int i = 0; i < nonregular.getNumberOfModels(); i++)
	                  {
	                          if (nonregular.getModel(i).getName(idiom).equals(solid))
	                          {
	                                  model = nonregular.getModel(i);
	                                  break;
	                          }
	                  }
	          }
	 */          
	          
	         if (category.equals(toroids_trans[idiom]))
	         {
	                  for (int i = 0; i < toroids.getNumberOfModels(); i++)
	                  {
	                          if (toroids.getModel(i).getName(idiom).equals(solid))
	                          {
	                                  model = toroids.getModel(i);
	                                  break;
	                          }
	                  }
	         }          

	/*         
	         if (category.equals(stewart_trans[idiom]))
	         {
	                 for (int i = 0; i < stewart.getNumberOfModels(); i++)
	                 {
	                         if (stewart.getModel(i).getName(idiom).equals(solid))
	                         {
	                                 model = stewart.getModel(i);
	                                 break;
	                         }
	                 }
	         }
	 */         

		  if (category.equals(leonardo_trans[idiom]))
		  {
			  for (int i = 0; i < leonardo.getNumberOfModels(); i++)
			  {
				  if (leonardo.getModel(i).getName(idiom).equals(solid))
				  {
					  model = leonardo.getModel(i);
					  break;
				  }
			  }
		  }

      if (category.equals(triplets_trans[idiom]))
      {
              for (int i = 0; i < triplets.getNumberOfModels(); i++)
              {
                      if (triplets.getModel(i).getName(idiom).equals(solid))
                      {
                              model = triplets.getModel(i);
                              break;
                      }
              }
      }
            
      
      m_pjShadow.showVertices = model.isShowingVertices();
      m_pjShadow.showEdges = model.isShowingEdges();
      m_pjShadow.showElements = model.isShowingElements();
      
      m_pjShadow.loadGeometryFromFile(model);
      
      if (viewElementsCB != null)
      {
         if ((model.isPolygonSet() == true) || (model.isPointSet() == true))
         {
    	     viewElementsCB.setVisible(false);
         }
         else
         {
    	     viewElementsCB.setVisible(true);    	  
         }
         viewElementsCB.setState(model.isShowingElements());
      }

      if (viewEdgesCB != null)
      {
         if (model.isPointSet() == true)
         {
    	     viewEdgesCB.setVisible(false);
         }
         else
         {
    	     viewEdgesCB.setVisible(true);
         }
         viewEdgesCB.setState(model.isShowingEdges());
         
         if (model.getCategory(idiom) == curves_trans[idiom])
         {
        	 viewEdgesCB.setLabel(curves_label_trans[idiom]);
         }
      }
            
      if (viewVerticesCB != null)
      {
         if (model.isPolygonSet() == true)
         {
    	     if (model.isShowingVertices() == false)
    	     {
    	    	 viewVerticesCB.setVisible(false);
    	    	 showAllOrthogonalButton.setVisible(false);
    	    	 hideAllOrthogonalButton.setVisible(false);    	    	 
    	     }
    	     else
    	     {
    	    	 viewVerticesCB.setVisible(true);
    	    	 showAllOrthogonalButton.setVisible(true);
    	    	 hideAllOrthogonalButton.setVisible(true);    	    	     	    	 
    	     }
         }
         else
         {
	    	 viewVerticesCB.setVisible(true);
	    	 showAllOrthogonalButton.setVisible(true);
	    	 hideAllOrthogonalButton.setVisible(true);    	    	     	    	         	 
         }         
         viewVerticesCB.setState(model.isShowingVertices());
      }      
   }

   //////////////////////////////////////////////////////////////////////////
   // Update the panel whenever the parent has changed somewhere else
   //////////////////////////////////////////////////////////////////////////
   public boolean update(Object  event)
   {
      if (m_pjShadow == event)
      {
         // Fill here!

         return(true);
      };

      return(super.update(event));
   }; // End of update()


   //////////////////////////////////////////////////////////////////////////
   // Handle action events invoked from text fields
   //////////////////////////////////////////////////////////////////////////
   public void actionPerformed(ActionEvent event)
   {
      if (m_pjShadow == null)
      {
          return;
      };
         

      Object source = event.getSource();

      if (source == showAllOrthogonalButton)
      {
          m_pjShadow.showAllOrthogonalSegments();
      }   
      
      if (source == hideAllOrthogonalButton)
      {
          m_pjShadow.hideAllOrthogonalSegments();
      }
      
      if (source == resetRotateControlsButton)
      {
          m_pjShadow.rot1.setValue(0.0);
          m_pjShadow.rot2.setValue(0.0);
          m_pjShadow.rot3.setValue(0.0);
          
          m_pjShadow.poly.copy(m_pjShadow.copy);

          m_pjShadow.poly.update(m_pjShadow.poly);
          m_pjShadow.updateShadows();          
      }            
   }; // End of actionPerformed()


   //////////////////////////////////////////////////////////////////////////
   // Handle action events invoked from itens
   //////////////////////////////////////////////////////////////////////////
   public void itemStateChanged(ItemEvent event)
   {
       if (m_pjShadow == null)
       {
          return;
       };

       Object source = event.getSource();

       //////////////////////////////////////////////////////////////////////
       // Names
       //////////////////////////////////////////////////////////////////////
       if (source == poly_category)
       {        
          String category = poly_category.getSelectedItem();
          buildChoice(category);
          poly_name.select(0);
          loadModel(category, poly_name.getSelectedItem());

          // Apply current settings
          if (this.viewVerticesCB.getState() == false)
          {
              m_pjShadow.poly.showVertices(false);
              m_pjShadow.poly.update(m_pjShadow.poly);
              m_pjShadow.copy.showVertices(false);
              m_pjShadow.copy.update(m_pjShadow.copy);                           
          }
          if (this.viewEdgesCB.getState() == false)
          {
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.poly;
	        	  p.showEdges(false);				  
			  }
              m_pjShadow.poly.update(m_pjShadow.poly);
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.copy;
	        	  p.showEdges(false);				  
			  }              
              m_pjShadow.copy.update(m_pjShadow.copy);              
          }
          if (this.viewElementsCB.getState() == false)
          {
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.poly;
	        	  p.showElements(false);				  
			  }
              m_pjShadow.poly.update(m_pjShadow.poly);
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.copy;
	        	  p.showElements(false);				  
			  }
              m_pjShadow.copy.update(m_pjShadow.copy);              
          }

          m_pjShadow.disp.fit();
       }

       if (source == poly_name)
       {
          loadModel(poly_category.getSelectedItem(),
                    poly_name.getSelectedItem());

          // Apply current settings
          if (this.viewVerticesCB.getState() == false)
          {
              m_pjShadow.poly.showVertices(false);
              m_pjShadow.poly.update(m_pjShadow.poly);
              m_pjShadow.copy.showVertices(false);
              m_pjShadow.copy.update(m_pjShadow.copy);              
          }
          if (this.viewEdgesCB.getState() == false)
          {
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.poly;
	        	  p.showEdges(false);				  
			  }
              m_pjShadow.poly.update(m_pjShadow.poly);
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.copy;
	        	  p.showEdges(false);				  
			  }              
              m_pjShadow.copy.update(m_pjShadow.copy);              
          }
          if (this.viewElementsCB.getState() == false)
          {
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.poly;
	        	  p.showElements(false);				  
			  }
              m_pjShadow.poly.update(m_pjShadow.poly);
			  if (m_pjShadow.isPolygonSet == false)
			  {
				  PgElementSet p = (PgElementSet) m_pjShadow.copy;
	        	  p.showElements(false);				  
			  }
              m_pjShadow.copy.update(m_pjShadow.copy);              
          }
          
          m_pjShadow.disp.fit();
       }

       //////////////////////////////////////////////////////////////////////
       // Visualization panel
       //////////////////////////////////////////////////////////////////////
       if (source == viewElementsCB)
       {
          if (viewElementsCB.getState() == true)
          {
			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.poly;
				 p.showElements(true);
			 }
             m_pjShadow.poly.update(m_pjShadow.poly);
			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.copy;
				 p.showElements(true);
			 }
             m_pjShadow.copy.update(m_pjShadow.copy);             
          }
          else
          {
 			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.poly;
				 p.showElements(false);
			 }
             m_pjShadow.poly.update(m_pjShadow.poly);
			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.copy;
				 p.showElements(false);
			 }
             m_pjShadow.copy.update(m_pjShadow.copy);             
          };
          return;
       }; // End of if (source == viewElementsCB)

       if (source == viewEdgesCB)
       {
          if (viewEdgesCB.getState() == true)
          {
 			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.poly;
				 p.showEdges(true);
			 }
 			 else
 			 {
				 PgPolygonSet p = (PgPolygonSet) m_pjShadow.poly;
				 p.showPolygons(true);				 
 			 }
             m_pjShadow.poly.update(m_pjShadow.poly);
			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.copy;
				 p.showEdges(true);
			 }
 			 else
 			 {
				 PgPolygonSet p = (PgPolygonSet) m_pjShadow.copy;
				 p.showPolygons(true);				 
 			 }			 
             m_pjShadow.copy.update(m_pjShadow.copy);             
          }
          else
          {
  			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.poly;
				 p.showEdges(false);
			 }
 			 else
 			 {
				 PgPolygonSet p = (PgPolygonSet) m_pjShadow.poly;
				 p.showPolygons(false);				 
 			 }  			 
             m_pjShadow.poly.update(m_pjShadow.poly);
			 if (m_pjShadow.isPolygonSet == false)
			 {        	  
				 PgElementSet p = (PgElementSet) m_pjShadow.copy;
				 p.showEdges(false);
			 }
 			 else
 			 {
				 PgPolygonSet p = (PgPolygonSet) m_pjShadow.copy;
				 p.showPolygons(false);				 
 			 }			
             m_pjShadow.copy.update(m_pjShadow.copy);             
          };
          return;
       }; // End of if (source == viewEdgesCB)

       if (source == viewVerticesCB)
       {
          if (viewVerticesCB.getState() == true)
          {
             m_pjShadow.poly.showVertices(true);
             m_pjShadow.poly.update(m_pjShadow.poly);
             m_pjShadow.copy.showVertices(true);
             m_pjShadow.copy.update(m_pjShadow.copy); 
             m_pjShadow.shadowXY.showVertices(true);
             m_pjShadow.shadowXY.update(m_pjShadow.shadowXY);
             m_pjShadow.shadowXZ.showVertices(true);
             m_pjShadow.shadowXZ.update(m_pjShadow.shadowXZ);             
             m_pjShadow.shadowYZ.showVertices(true);
             m_pjShadow.shadowYZ.update(m_pjShadow.shadowYZ);             
          }
          else
          {
             m_pjShadow.poly.showVertices(false);
             m_pjShadow.poly.update(m_pjShadow.poly);
             m_pjShadow.copy.showVertices(false);
             m_pjShadow.copy.update(m_pjShadow.copy);        
             m_pjShadow.shadowXY.showVertices(false);
             m_pjShadow.shadowXY.update(m_pjShadow.shadowXY);
             m_pjShadow.shadowXZ.showVertices(false);
             m_pjShadow.shadowXZ.update(m_pjShadow.shadowXZ);             
             m_pjShadow.shadowYZ.showVertices(false);
             m_pjShadow.shadowYZ.update(m_pjShadow.shadowYZ);             
          };
          return;
       }; // End of if (source == viewVerticesCB)
       
       
       if (source == viewProjectionXY)
       {
          if (viewProjectionXY.getState() == true)
          {
             m_pjShadow.addGeometry(m_pjShadow.shadowXY); 
             m_pjShadow.showOrthogonalSegmentsXY(true);
          }
          else
          {
              m_pjShadow.removeGeometry(m_pjShadow.shadowXY);
              m_pjShadow.showOrthogonalSegmentsXY(false);
         };
         return;
       }; // End of if (source == viewProjection1)


       if (source == viewProjectionXZ)
       {
          if (viewProjectionXZ.getState() == true)
          {
             m_pjShadow.addGeometry(m_pjShadow.shadowXZ);             
             m_pjShadow.showOrthogonalSegmentsXZ(true);
          }
          else
          {
              m_pjShadow.removeGeometry(m_pjShadow.shadowXZ);
              m_pjShadow.showOrthogonalSegmentsXZ(false);
         };
         return;
       }; // End of if (source == viewProjection2)

       
       if (source == viewProjectionYZ)
       {
          if (viewProjectionYZ.getState() == true)
          {
             m_pjShadow.addGeometry(m_pjShadow.shadowYZ);
             m_pjShadow.showOrthogonalSegmentsYZ(true);
          }
          else
          {
              m_pjShadow.removeGeometry(m_pjShadow.shadowYZ);
              m_pjShadow.showOrthogonalSegmentsYZ(false);
         };
         return;
       }; // End of if (source == viewProjection2)       
    }; // End of itemStateChanged()



    //////////////////////////////////////////////////////////////////////////
    // Build choice of models within a given category
    //////////////////////////////////////////////////////////////////////////
    protected void buildChoice(String c)
    {
    	poly_name.removeAll();
       	if (c.equals(object_trans[idiom]))
    	{
    		for (int i = 0; i < object.getNumberOfModels(); i++)
    		{
    		   poly_name.add(object.getModel(i).getName(idiom));
    		}
    	}
       	
       	if (c.equals(curves_trans[idiom]))
    	{
    		for (int i = 0; i < curves.getNumberOfModels(); i++)
    		{
    		   poly_name.add(curves.getModel(i).getName(idiom));
    		}
    	}       	
    	
    	if (c.equals(platonic_trans[idiom]))
    	{
    		for (int i = 0; i < platonic.getNumberOfModels(); i++)
    		{
    		   poly_name.add(platonic.getModel(i).getName(idiom));
    		}
    	}

    	if (c.equals(archimedean_trans[idiom]))
    	{
    		for (int i = 0; i < archimedean.getNumberOfModels(); i++)
    		{
    		   poly_name.add(archimedean.getModel(i).getName(idiom));
    		}
    	}

    	if (c.equals(johnson_trans[idiom]))
    	{
    		for (int i = 0; i < johnson.getNumberOfModels(); i++)
    		{
    		   poly_name.add(johnson.getModel(i).getName(idiom));
    		}
    	}

            if (c.equals(catalan_trans[idiom]))
        {
                for (int i = 0; i < catalan.getNumberOfModels(); i++)
                {
                   poly_name.add(catalan.getModel(i).getName(idiom));
                }
        }

        if (c.equals(prisms_trans[idiom]))
        {
                for (int i = 0; i < prisms.getNumberOfModels(); i++)
                {
                   poly_name.add(prisms.getModel(i).getName(idiom));
                }
        }

        if (c.equals(antiprisms_trans[idiom]))
        {
                for (int i = 0; i < antiprisms.getNumberOfModels(); i++)
                {
                   poly_name.add(antiprisms.getModel(i).getName(idiom));
                }
        }

        if (c.equals(pyramids_trans[idiom]))
        {
                for (int i = 0; i < pyramids.getNumberOfModels(); i++)
                {
                   poly_name.add(pyramids.getModel(i).getName(idiom));
                }
        }        
        
/*        
        if (c.equals(nonregular_trans[idiom]))
        {
                for (int i = 0; i < nonregular.getNumberOfModels(); i++)
                {
                   poly_name.add(nonregular.getModel(i).getName(idiom));
                }
        }
 */                

        if (c.equals(toroids_trans[idiom]))
        {
                for (int i = 0; i < toroids.getNumberOfModels(); i++)
                {
                   poly_name.add(toroids.getModel(i).getName(idiom));
                }
        }        

/*        
        if (c.equals(stewart_trans[idiom]))
        {
                for (int i = 0; i < stewart.getNumberOfModels(); i++)
                {
                   poly_name.add(stewart.getModel(i).getName(idiom));
                }
        }
 */        

    	if (c.equals(leonardo_trans[idiom]))
    	{
    		for (int i = 0; i < leonardo.getNumberOfModels(); i++)
    		{
    		   poly_name.add(leonardo.getModel(i).getName(idiom));
    		}
    	}

    	if (c.equals(animal_trans[idiom]))
    	{
    		for (int i = 0; i < animal.getNumberOfModels(); i++)
    		{
    		   poly_name.add(animal.getModel(i).getName(idiom));
    		}
    	}
    	
    	if (c.equals(hexahedra_trans[idiom]))
    	{
    		for (int i = 0; i < hexahedra.getNumberOfModels(); i++)
    		{
    		   poly_name.add(hexahedra.getModel(i).getName(idiom));
    		}
    	}
    	
    	if (c.equals(isohedra_trans[idiom]))
    	{
    		for (int i = 0; i < isohedra.getNumberOfModels(); i++)
    		{
    		   poly_name.add(isohedra.getModel(i).getName(idiom));
    		}
    	}        
        if (c.equals(triplets_trans[idiom]))
        {
                for (int i = 0; i < triplets.getNumberOfModels(); i++)
                {
                   poly_name.add(triplets.getModel(i).getName(idiom));
                }
        }        
    }


    //////////////////////////////////////////////////////////////////////////
    // Build database of solids
    //////////////////////////////////////////////////////////////////////////
    protected void buildDataBase()
    {
        int i;
        
        // OBJECT
        i = 0;
        object = new Category(object_trans, 5);

        object.addModel(i, new Model(object_trans,
                new String[]
                {"Ponto",
                 "Point"},
                "jvx",
                "object-point",
                true, true, false,
                false,
                false,
                false, true));
        i++;
        
        object.addModel(i, new Model(object_trans,
                new String[]
                {"Segmento de Reta",
                 "Line Segment"},
                "jvx",
                "object-segment-1",
                true, true, false,
                false,
                false,
                true));
        i++;
        
        object.addModel(i, new Model(object_trans,
                new String[]
                {"Caminho Poligonal 1",
                 "Polygonal Path 1"},
                "jvx",
                "object-segment-2",
                true, true, false,
                false,
                false,
                true));
        i++;        

        object.addModel(i, new Model(object_trans,
                new String[]
                {"Caminho Poligonal 2",
                 "Polygonal Path 2"},
                "jvx",
                "object-segment-3",
                true, true, false,
                false,
                false,
                true));
        i++;     
        
        object.addModel(i, new Model(object_trans,
                new String[]
                {"Quadrado",
                 "Square"},
                "off",
                "object-square",
	          4, 4, 1,
                true, true, true,
                true,
                true));
        i++;

        
        // CURVES
        i = 0;
        curves = new Category(curves_trans, 15);

        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Hélice Circular",
                 "Circular Helix"},
                "jvx",
                "curves-helix",
                false, true, false,
                false,
                false,
                true));
        i++;

        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Curva Interessante 1",
                 "Interesting Curve 1"},
                "jvx",
                "curves-interesting-01",
                false, true, false,
                false,
                false,
                true));
        i++;


        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Curva Interessante 2",
                 "Interesting Curve 2"},
                "jvx",
                "curves-interesting-02",
                false, true, false,
                false,
                false,
                true));
        i++;        
        
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Costura da Bola de Tênis",
                 "Seam of a Tennis Ball"},
                "jvx",
                "curves-seam-of-a-tennis-ball",
                false, true, false,
                false,
                false,
                true));
        i++;

        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Curva de Viviani",
                 "Viviani's Curve"},
                "jvx",
                "curves-viviani",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Curva Loxodrômica da Esfera",
                 "Sphere Loxodrome"},
                "jvx",
                "curves-loxodrome",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Espiral Cônica de Pappus",
                 "Conic Spiral of Pappus"},
                "jvx",
                "curves-conic-spiral-of-pappus",
                false, true, false,
                false,
                false,
                true));
        i++;

        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 3.1",
                 "Knot 3.1"},
                "jvx",
                "curves-knot-00-03-01",
                false, true, false,
                false,
                false,
                true));
        i++;
        

        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 4.1",
                 "Knot 4.1"},
                "jvx",
                "curves-knot-00-04-01",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 5.1",
                 "Knot 5.1"},
                "jvx",
                "curves-knot-00-05-01",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 5.2",
                 "Knot 5.2"},
                "jvx",
                "curves-knot-00-05-02",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 9.1",
                 "Knot 9.1"},
                "jvx",
                "curves-knot-00-09-01",
                false, true, false,
                false,
                false,
                true));
        i++;

        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 2.2.1",
                 "Knot 2.2.1"},
                "jvx",
                "curves-knot-02-02-01",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 4.2.1",
                 "Knot 4.2.1"},
                "jvx",
                "curves-knot-04-02-01",
                false, true, false,
                false,
                false,
                true));
        i++;
        
        
        curves.addModel(i, new Model(curves_trans,
                new String[]
                {"Nó 5.2.1",
                 "Knot 5.2.1"},
                "jvx",
                "curves-knot-05-02-01",
                false, true, false,
                false,
                false,
                true));
        i++;

        
        // PLATONIC
        i = 0;
        platonic = new Category(platonic_trans, 5);

        platonic.addModel(i, new Model(platonic_trans,
                new String[]
                {"Tetraedro",
                 "Tetrahedron"},
                "off",
                "platonic-tetrahedron",
	          4, 6, 4,
                true, true, true,
                true,
                true));
        i++;

        platonic.addModel(i, new Model(platonic_trans,
                new String[]
                {"Cubo",
                 "Cube"},
                "off",
                "platonic-cube",
	          8, 12, 6,
                true, true, true,
                true,
                true));
        i++;

        platonic.addModel(i, new Model(platonic_trans,
                new String[]
                {"Octaedro",
                 "Octahedron"},
                "off",
                "platonic-octahedron",
	          6, 12, 8,
                true, true, true,
                true,
                true));
        i++;

        platonic.addModel(i, new Model(platonic_trans,
                new String[]
                {"Dodecaedro",
                 "Dodecahedron"},
                "off",
                "platonic-dodecahedron",
	          20, 30, 12,
                true, true, true,
                true,
                true));
        i++;

        platonic.addModel(i, new Model(platonic_trans,
                new String[]
                {"Icosaedro",
                 "Icosahedron"},
                "off",
                "platonic-icosahedron",
	          12, 30, 20,
                true, true, true,
                true,
                true));
        i++;

        // ARCHIMEDEAN
        i = 0;
        archimedean = new Category(archimedean_trans, 13);

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Tetraedro Truncado",
                 "Truncated Tetrahedron"},
                "off",
                "archimedean-truncated-tetrahedron",
	          12, 18, 8,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Cuboctaedro",
                 "Cuboctahedron"},
                "off",
                "archimedean-cuboctahedron",
	          12, 24, 14,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Octaedro Truncado",
                 "Truncated Octahedron"},
                "off",
                "archimedean-truncated-octahedron",
	          24, 36, 14,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Cubo Truncado",
                 "Truncated Cube"},
                "off",
                "archimedean-truncated-cube",
	          24, 36, 14,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Pequeno Rombicuboctaedro",
                 "Small Rhombicuboctahedron"},
                "off",
                "archimedean-rhombicuboctahedron",
	          24, 48, 26,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Grande Rombicuboctaedro",
                 "Great Rhombicuboctahedron"},
                "off",
                "archimedean-truncated-cuboctahedron",
	          48, 72, 26,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Cubo Achatado",
                 "Snub Cube"},
                "off",
                "archimedean-snub-cube",
	          24, 60, 38,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Icosidodecaedro",
                 "Icosidodecahedron"},
                "off",
                "archimedean-icosidodecahedron",
	          30, 60, 32,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Icosaedro Truncado",
                 "Truncated Icosahedron"},
                "off",
                "archimedean-truncated-icosahedron",
	          60, 90, 32,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Dodecaedro Truncado",
                 "Truncated Dodecahedron"},
                "off",
                "archimedean-truncated-dodecahedron",
	          60, 90, 32,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Pequeno Rombicosidodecaedro",
                 "Small Rhombicosidodecahedron"},
                "off",
                "archimedean-rhombicosidodecahedron",
	          60, 120, 62,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Grande Rombicosidodecaedro",
                 "Great Rhombicosidodecahedron"},
                "off",
                "archimedean-truncated-icosidodecahedron",
	          120, 180, 62,
                true, true, true,
                true,
                true));
        i++;

        archimedean.addModel(i, new Model(archimedean_trans,
                new String[]
                {"Dodecaedro Achatado",
                 "Snub Dodecahedron"},
                "off",
                "archimedean-snub-dodecahedron",
	          60, 150, 92,
                true, true, true,
                true,
                true));
        i++;

        // JOHNSON
        i = 0;
        johnson = new Category(johnson_trans, 92);

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Quadrada (J1)",
                 "Square Pyramid (J1)"},
                "off",
                "johnson-j01-square-pyramid",
	          5, 8, 5,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Pentagonal (J2)",
                 "Pentagonal Pyramid (J2)"},
                "off",
                "johnson-j02-pentagonal-pyramid",
	          6, 10, 6,
                true, true, true,
                true,
                true));
        i++;

       johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Triangular (J3)",
                 "Triangular Cupola (J3)"},
                "off",
                "johnson-j03-triangular-cupola",
	          9, 15, 8,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Quadrada (J4)",
                 "Square Cupola (J4)"},
                "off",
                "johnson-j04-square-cupola",
	          12, 20, 10,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Pentagonal (J5)",
                 "Pentagonal Cupola (J5)"},
                "off",
                "johnson-j05-pentagonal-cupola",
	          15, 25, 12,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rotunda Pentagonal (J6)",
                 "Pentagonal Rotunda (J6)"},
                "off",
                "johnson-j06-pentagonal-rotunda",
	          20, 35, 17,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Triangular Alongada (J7)",
                 "Elongated Triangular Pyramid (J7)"},
                "off",
                "johnson-j07-elongated-triangular-pyramid",
	          7, 12, 7,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Quadrada Alongada (J8)",
                 "Elongated Square Pyramid (J8)"},
                "off",
                "johnson-j08-elongated-square-pyramid",
	          9, 16, 9,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Pentagonal Alongada (J9)",
                 "Elongated Pentagonal Pyramid (J9)"},
                "off",
                "johnson-j09-elongated-pentagonal-pyramid",
	          11, 20, 11,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Quadrada Giroalongada (J10)",
                 "Gyroelongated Square Pyramid (J10)"},
                "off",
                "johnson-j10-gyroelongated-square-pyramid",
	          9, 20, 13,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Pirâmide Pentagonal Giroalongada (J11)",
                 "Gyroelongated Pentagonal Pyramid (J11)"},
                "off",
                "johnson-j11-gyroelongated-pentagonal-pyramid",
	          11, 25, 16,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dipirâmide Triangular (J12)",
                 "Triangular Dipyramid (J12)"},
                "off",
                "johnson-j12-triangular-dipyramid",
	          5, 9, 6,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dipirâmide Pentagonal (J13)",
                 "Pentagonal Dipyramid (J13)"},
                "off",
                "johnson-j13-pentagonal-dipyramid",
	          7, 15, 10,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dipirâmide Triangular Alongada (J14)",
                 "Elongated Triangular Dipyramid (J14)"},
                "off",
                "johnson-j14-elongated-triangular-dipyramid",
	          8, 15, 9,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dipirâmide Quadrada Alongada (J15)",
                 "Elongated Square Dipyramid (J15)"},
                "off",
                "johnson-j15-elongated-square-dipyramid",
	          10, 20, 12,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dipirâmide Pentagonal Alongada (J16)",
                 "Elongated Pentagonal Dipyramid (J16)"},
                "off",
                "johnson-j16-elongated-pentagonal-dipyramid",
	          12, 25, 15,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dipirâmide Quadrada Giroalongada (J17)",
                 "Gyroelongated Square Dipyramid (J17)"},
                "off",
                "johnson-j17-gyroelongated-square-dipyramid",
	          10, 24, 16,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Triangular Alongada (J18)",
                 "Elongated Triangular Cupola (J18)"},
                "off",
                "johnson-j18-elongated-triangular-cupola",
	          15, 27, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Quadrada Alongada (J19)",
                 "Elongated Square Cupola (J19)"},
                "off",
                "johnson-j19-elongated-square-cupola",
	          20, 36, 18,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Pentagonal Alongada (J20)",
                 "Elongated Pentagonal Cupola (J20)"},
                "off",
                "johnson-j20-elongated-pentagonal-cupola",
	          25, 45, 22,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rotunda Pentagonal Alongada (J21)",
                 "Elongated Pentagonal Rotunda (J21)"},
                "off",
                "johnson-j21-elongated-pentagonal-rotunda",
	          30, 55, 27,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Triangular Giroalongada (J22)",
                 "Gyroelongated Triangular Cupola (J22)"},
                "off",
                "johnson-j22-gyroelongated-triangular-cupola",
	          15, 33, 20,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Quadrada Giroalongada (J23)",
                 "Gyroelongated Square Cupola (J23)"},
                "off",
                "johnson-j23-gyroelongated-square-cupola",
	          20, 44, 26,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cúpula Pentagonal Giroalongada (J24)",
                 "Gyroelongated Pentagonal Cupola (J24)"},
                "off",
                "johnson-j24-gyroelongated-pentagonal-cupola",
	          25, 55, 32,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rotunda Pentagonal Giroalongada (J25)",
                 "Gyroelongated Pentagonal Rotunda (J25)"},
                "off",
                "johnson-j25-gyroelongated-pentagonal-rotunda",
	          30, 65, 37,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Gyrobifastigium (J26)",
                 "Gyrobifastigium (J26)"},
                "off",
                "johnson-j26-gyrobifastigium",
	          8, 14, 8,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobicúpula Triangular (J27)",
                 "Triangular Orthobicupola (J27)"},
                "off",
                "johnson-j27-triangular-orthobicupola",
	          12, 24, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobicúpula Quadrada (J28)",
                 "Square Orthobicupola (J28)"},
                "off",
                "johnson-j28-square-orthobicupola",
	          16, 32, 18,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girobicúpula Quadrada (J29)",
                 "Square Gyrobicupola (J29)"},
                "off",
                "johnson-j29-square-gyrobicupola",
	          16, 32, 18,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobicúpula Pentagonal (J30)",
                 "Pentagonal Orthobicupola (J30)"},
                "off",
                "johnson-j30-pentagonal-orthobicupola",
	          20, 40, 22,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girobicúpula Pentagonal (J31)",
                 "Pentagonal Gyrobicupola (J31)"},
                "off",
                "johnson-j31-pentagonal-gyrobicupola",
	          20, 40, 22,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortocupularrotunda Pentagonal (J32)",
                 "Pentagonal Orthocupolarotunda (J32)"},
                "off",
                "johnson-j32-pentagonal-orthocupolarotunda",
	          25, 50, 27,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girocupularrotunda Pentagonal (J33)",
                 "Pentagonal Gyrocupolarotunda (J33)"},
                "off",
                "johnson-j33-pentagonal-gyrocupolarotunda",
	          25, 50, 27,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobirrotunda Pentagonal (J34)",
                 "Pentagonal Orthobirotunda (J34)"},
                "off",
                "johnson-j34-pentagonal-orthobirotunda",
	          30, 60, 32,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobicúpula Triangular Alongada (J35)",
                 "Elongated Triangular Orthobicupola (J35)"},
                "off",
                "johnson-j35-elongated-triangular-orthobicupola",
	          18, 36, 20,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girobicúpula Triangular Alongada (J36)",
                 "Elongated Triangular Gyrobicupola (J36)"},
                "off",
                "johnson-j36-elongated-triangular-gyrobicupola",
	          18, 36, 20,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girobicúpula Quadrada Alongada (J37)",
                 "Elongated Square Gyrobicupola (J37)"},
                "off",
                "johnson-j37-elongated-square-gyrobicupola",
	          24, 48, 26,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobicúpula Pentagonal Alongada (J38)",
                 "Elongated Pentagonal Orthobicupola (J38)"},
                "off",
                "johnson-j38-elongated-pentagonal-orthobicupola",
	          30, 60, 32,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girobicúpula Pentagonal Alongada (J39)",
                 "Elongated Pentagonal Gyrobicupola (J39)"},
                "off",
                "johnson-j39-elongated-pentagonal-gyrobicupola",
	          30, 60, 32,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortocupularrotunda Pentagonal Alongada (J40)",
                 "Elongated Pentagonal Orthocupolarotunda (J40)"},
                "off",
                "johnson-j40-elongated-pentagonal-orthocupolarotunda",
	          35, 70, 37,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girocupularrotunda Pentagonal Alongada (J41)",
                 "Elongated Pentagonal Gyrocupolarotunda (J41)"},
                "off",
                "johnson-j41-elongated-pentagonal-gyrocupolarotunda",
	          35, 70, 37,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Ortobirrotunda Pentagonal Alongada (J42)",
                 "Elongated Pentagonal Orthobirotunda (J42)"},
                "off",
                "johnson-j42-elongated-pentagonal-orthobirotunda",
	          40, 80, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Girobirrotunda Pentagonal Alongada (J43)",
                 "Elongated Pentagonal Gyrobirotunda (J43)"},
                "off",
                "johnson-j43-elongated-pentagonal-gyrobirotunda",
	          40, 80, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Bicúpula Triangular Giroalongada (J44)",
                 "Gyroelongated Triangular Bicupola (J44)"},
                "off",
                "johnson-j44-gyroelongated-triangular-bicupola",
	          18, 42, 26,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Bicúpula Quadrada Giroalongada (J45)",
                 "Gyroelongated Square Bicupola (J45)"},
                "off",
                "johnson-j45-gyroelongated-square-bicupola",
	          24, 56, 34,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Bicúpula Pentagonal Giroalongada (J46)",
                 "Gyroelongated Pentagonal Bicupola (J46)"},
                "off",
                "johnson-j46-gyroelongated-pentagonal-bicupola",
	          30, 70, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cupularrotunda Pentagonal Giroalongada (J47)",
                 "Gyroelongated Pentagonal Cupolarotunda (J47)"},
                "off",
                "johnson-j47-gyroelongated-pentagonal-cupolarotunda",
	          35, 80, 47,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Birrotunda Pentagonal Giroalongada (J48)",
                 "Gyroelongated Pentagonal Birotunda (J48)"},
                "off",
                "johnson-j48-gyroelongated-pentagonal-birotunda",
	          40, 90, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Triangular Aumentado (J49)",
                 "Augmented Triangular Prism (J49)"},
                "off",
                "johnson-j49-augmented-triangular-prism",
	          7, 13, 8,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Triangular Biaumentado (J50)",
                 "Biaugmented Triangular Prism (J50)"},
                "off",
                "johnson-j50-biaugmented-triangular-prism",
	          8, 17, 11,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Triangular Triaumentado (J51)",
                 "Triaugmented Triangular Prism (J51)"},
                "off",
                "johnson-j51-triaugmented-triangular-prism",
	          9, 21, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Pentagonal Aumentado (J52)",
                 "Augmented Pentagonal Prism (J52)"},
                "off",
                "johnson-j52-augmented-pentagonal-prism",
	          11, 19, 10,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Pentagonal Biaumentado (J53)",
                 "Biaugmented Pentagonal Prism (J53)"},
                "off",
                "johnson-j53-biaugmented-pentagonal-prism",
	          12, 23, 13,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Hexagonal Aumentado (J54)",
                 "Augmented Hexagonal Prism (J54)"},
                "off",
                "johnson-j54-augmented-hexagonal-prism",
	          13, 22, 11,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Hexagonal Parabiaumentado (J55)",
                 "Parabiaugmented Hexagonal Prism (J55)"},
                "off",
                "johnson-j55-parabiaugmented-hexagonal-prism",
	          14, 26, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Hexagonal Metabiaumentado (J56)",
                 "Metabiaugmented Hexagonal Prism (J56)"},
                "off",
                "johnson-j56-metabiaugmented-hexagonal-prism",
	          14, 26, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Prisma Hexagonal Triaumentado (J57)",
                 "Triaugmented Hexagonal Prism (J57)"},
                "off",
                "johnson-j57-triaugmented-hexagonal-prism",
	          15, 30, 17,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Aumentado (J58)",
                 "Augmented Dodecahedron (J58)"},
                "off",
                "johnson-j58-augmented-dodecahedron",
	          21, 35, 16,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Parabiaumentado (J59)",
                 "Parabiaugmented Dodecahedron (J59)"},
                "off",
                "johnson-j59-parabiaugmented-dodecahedron",
	          22, 40, 20,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Metabiaumentado (J60)",
                 "Metabiaugmented Dodecahedron (J60)"},
                "off",
                "johnson-j60-metabiaugmented-dodecahedron",
	          22, 40, 20,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Triaumentado (J61)",
                 "Triaugmented Dodecahedron (J61)"},
                "off",
                "johnson-j61-triaugmented-dodecahedron",
	          23, 45, 24,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Icosaedro Metabidiminuído (J62)",
                 "Metabidiminished Icosahedron (J62)"},
                "off",
                "johnson-j62-metabidiminished-icosahedron",
	          10, 20, 12,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Icosaedro Tridiminuído (J63)",
                 "Tridiminished Icosahedron (J63)"},
                "off",
                "johnson-j63-tridiminished-icosahedron",
	          9, 15, 8,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Icosaedro Tridiminuído Aumentado (J64)",
                 "Augmented Tridiminished Icosahedron (J64)"},
                "off",
                "johnson-j64-augmented-tridiminished-icosahedron",
	          10, 18, 10,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Tetraedro Truncado Aumentado (J65)",
                 "Augmented Truncated Tetrahedron (J65)"},
                "off",
                "johnson-j65-augmented-truncated-tetrahedron",
	          15, 27, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cubo Truncado Aumentado (J66)",
                 "Augmented Truncated Cube (J66)"},
                "off",
                "johnson-j66-augmented-truncated-cube",
	          28, 48, 22,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Cubo Truncado Biaumentado (J67)",
                 "Biaugmented Truncated Cube (J67)"},
                "off",
                "johnson-j67-biaugmented-truncated-cube",
	          32, 60, 30,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Truncado Aumentado (J68)",
                 "Augmented Truncated Dodecahedron (J68)"},
                "off",
                "johnson-j68-augmented-truncated-dodecahedron",
	          65, 105, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Truncado Parabiaumentado (J69)",
                 "Parabiaugmented Truncated Dodecahedron (J69)"},
                "off",
                "johnson-j69-parabiaugmented-truncated-dodecahedron",
	          70, 120, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Truncado Metabiaumentado (J70)",
                 "Metabiaugmented Truncated Dodecahedron (J70)"},
                "off",
                "johnson-j70-metabiaugmented-truncated-dodecahedron",
	          70, 120, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Dodecaedro Truncado Triaumentado (J71)",
                 "Triaugmented Truncated Dodecahedron (J71)"},
                "off",
                "johnson-j71-triaugmented-truncated-dodecahedron",
	          75, 135, 62,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Giro-Rombicosidodecaedro (J72)",
                 "Gyrate-Rhombicosidodecahedron (J72)"},
                "off",
                "johnson-j72-gyrate-rhombicosidodecahedron",
	          60, 120, 62,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Parabigiro-Rombicosidodecaedro (J73)",
                 "Parabigyrate-Rhombicosidodecahedron (J73)"},
                "off",
                "johnson-j73-parabigyrate-rhombicosidodecahedron",
	          60, 120, 62,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Metabigiro-Rombicosidodecaedro (J74)",
                 "Metabigyrate-Rhombicosedodecahedron (J74)"},
                "off",
                "johnson-j74-metabigyrate-rhombicosidodecahedron",
	          60, 120, 62,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Trigiro-Rombicosidodecaedro (J75)",
                 "Trigyrate-Rhombicosidodecahedron (J75)"},
                "off",
                "johnson-j75-trigyrate-rhombicosidodecahedron",
	          60, 120, 62,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Diminuído (J76)",
                 "Diminished Rhombicosidodecahedron (J76)"},
                "off",
                "johnson-j76-diminished-rhombicosidodecahedron",
	          55, 105, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Paragirodiminuído (J77)",
                 "Paragyrate Diminished Rhombicosidodecahedron (J77)"},
                "off",
                "johnson-j77-paragyrate-diminished-rhombicosidodecahedron",
	          55, 105, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Metagirodiminuído (J78)",
                 "Metagyrate Diminished Rhombicosidodecahedron (J78)"},
                "off",
                "johnson-j78-metagyrate-diminished-rhombicosidodecahedron",
	          55, 105, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Bigirodiminuído (J79)",
                 "Bigyrate Diminished Rhombicosidodecahedron (J79)"},
                "off",
                "johnson-j79-bigyrate-diminished-rhombicosidodecahedron",
	          55, 105, 52,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Parabidiminuído (J80)",
                 "Parabidiminished Rhombicosidodecahedron (J80)"},
                "off",
                "johnson-j80-parabidiminished-rhombicosidodecahedron",
	          50, 90, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Metabidiminuído (J81)",
                 "Metabidiminished Rhombicosidodecahedron (J81)"},
                "off",
                "johnson-j81-metabidiminished-rhombicosidodecahedron",
	          50, 90, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Girobidiminuído (J82)",
                 "Gyrate Bidiminished Rhombicosidodecahedron (J82)"},
                "off",
                "johnson-j82-gyrate-bidiminished-rhombicosidodecahedron",
	          50, 90, 42,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Rombicosidodecaedro Tridiminuído (J83)",
                 "Tridiminished Rhombicosidodecahedron (J83)"},
                "off",
                "johnson-j83-tridiminished-rhombicosidodecahedron",
	          45, 75, 32,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Disfenóide Achatado (J84)",
                 "Snub-Disphenoid (J84)"},
                "off",
                "johnson-j84-snub-disphenoid",
	          8, 18, 12,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Antiprisma Quadrado Achatado (J85)",
                 "Snub Square Antiprism (J85)"},
                "off",
                "johnson-j85-snub-square-antiprism",
	          16, 40, 26,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Esfenocorona (J86)",
                 "Sphenocorona (J86)"},
                "off",
                "johnson-j86-sphenocorona",
	          10, 22, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Esfenocorona Aumentada (J87)",
                 "Augmented Sphenocorona (J87)"},
                "off",
                "johnson-j87-augmented-sphenocorona",
	          11, 26, 17,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Esfenomegacorona (J88)",
                 "Sphenomegacorona (J88)"},
                "off",
                "johnson-j88-sphenomegacorona",
	          12, 28, 18,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Hebesfenomegacorona (J89)",
                 "Hebesphenomegacorona (J89)"},
                "off",
                "johnson-j89-hebesphenomegacorona",
	          14, 33, 21,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Disfenocingulum (J90)",
                 "Disphenocingulum (J90)"},
                "off",
                "johnson-j90-disphenocingulum",
	          16, 38, 24,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Bilunabirrotunda (J91)",
                 "Bilunabirotunda (J91)"},
                "off",
                "johnson-j91-bilunabirotunda",
	          14, 26, 14,
                true, true, true,
                true,
                true));
        i++;

        johnson.addModel(i, new Model(johnson_trans,
                new String[]
                {"Hebesfenorrotunda Triangular (J92)",
                 "Triangular Hebesphenorotunda (J92)"},
                "off",
                "johnson-j92-triangular-hebesphenorotunda",
	          18, 36, 20,
                true, true, true,
                true,
                true));
        i++;

        // CATALAN
        i = 0;
        catalan = new Category(catalan_trans, 13);

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Tetraedro Triakis",
                 "Triakistetrahedron"},
                "off",
                "catalan-triakistetrahedron",
	          8, 18, 12,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Dodecaedro Rômbico",
                 "Rhombic Dodecahedron"},
                "off",
                "catalan-rhombic-dodecahedron",
	          14, 24, 12,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Hexaedro Tetrakis",
                 "Tetrakishexahedron"},
                "off",
                "catalan-tetrakishexahedron",
	          14, 36, 24,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Octaedro Triakis",
                 "Triakisoctahedron"},
                "off",
                "catalan-triakisoctahedron",
	          14, 36, 24,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Icositetraedro Deltoidal",
                 "Strombic Icositetrahedron"},
                "off",
                "catalan-strombic-icositetrahedron",
	          26, 48, 24,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Dodecaedro Disdiakis",
                 "Disdyakis Dodecahedron"},
                "off",
                "catalan-disdyakis-dodecahedron",
	          26, 72, 48,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Icositetraedro Pentagonal",
                 "Pentagonal Icositetrahedron"},
                "off",
                "catalan-pentagonal-icositetrahedron",
	          38, 60, 24,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Triacontaedro Rômbico",
                 "Rhombic Triacontahedron"},
                "off",
                "catalan-rhombic-triacontahedron",
	          32, 60, 30,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Dodecaedro Pentakis",
                 "Pentakis Dodecahedron"},
                "off",
                "catalan-pentakis-dodecahedron",
	          32, 90, 60,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Icosaedro Triakis",
                 "Triakis Icosahedron"},
                "off",
                "catalan-triakis-icosahedron",
	          32, 90, 60,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Hexecontaedro Deltoidal",
                 "Strombic Hexecontahedron"},
                "off",
                "catalan-strombic-hexecontahedron",
	          62, 120, 60,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Triacontaedro Disdiakis",
                 "Disdyakis Triacontahedron"},
                "off",
                "catalan-disdyakis-triacontahedron",
	          62, 180, 120,
                true, true, true,
                true,
                true));
        i++;

        catalan.addModel(i, new Model(catalan_trans,
                new String[]
                {"Hexecontaedro Pentagonal",
                 "Pentagonal Hexecontahedron"},
                "off",
                "catalan-pentagonal-hexecontahedron",
	          92, 150, 60,
                true, true, true,
                true,
                true));
        i++;

        // PRISMS
        i = 0;
        prisms = new Category(prisms_trans, 30);        
        
       // Regular 
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Triangular",
                "Regular with Triangular Base"},
               "off",
               "prisms-regular-triangular",
	          6, 9, 5,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Triangular",
                "Right with Triangular Base"},
               "off",
               "prisms-right-triangular",
	          6, 9, 5,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Triangular",
                "Oblique with Triangular Base"},
               "off",
               "prisms-oblique-triangular",
	          6, 9, 5,
               true, true, true,
               true,
               true));
       i++;
              
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Quadrangular",
                "Regular with Quadrangular Base"},
               "off",
               "prisms-regular-quadrangular",
	          8, 12, 6,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Quadrangular",
                "Right with Quadrangular Base"},
               "off",
               "prisms-right-quadrangular",
	          8, 12, 6,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Quadrangular",
                "Oblique with Quadrangular Base"},
               "off",
               "prisms-oblique-quadrangular",
	          8, 12, 6,
               true, true, true,
               true,
               true));
       i++;       
             
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Pentagonal",
                "Regular with Pentagonal Base"},
               "off",
               "prisms-regular-pentagonal",
	          10, 15, 7,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Pentagonal",
                "Right with Pentagonal Base"},
               "off",
               "prisms-right-pentagonal",
	          10, 15, 7,
               true, true, true,
               true,
               true));
       i++;       

       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Pentagonal",
                "Oblique with Pentagonal Base"},
               "off",
               "prisms-oblique-pentagonal",
	          10, 15, 7,
               true, true, true,
               true,
               true));
       i++;

       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Hexagonal",
                "Regular with Hexagonal Base"},
               "off",
               "prisms-regular-hexagonal",
	          12, 18, 8,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Hexagonal",
                "Right with Hexagonal Base"},
               "off",
               "prisms-right-hexagonal",
	          12, 18, 8,
               true, true, true,
               true,
               true));
       i++;       
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Hexagonal",
                "Oblique with Hexagonal Base"},
               "off",
               "prisms-oblique-hexagonal",
	          12, 18, 8,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Heptagonal",
                "Regular with Heptagonal Base"},
               "off",
               "prisms-regular-heptagonal",
	          14, 21, 9,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Heptagonal",
                "Right with Heptagonal Base"},
               "off",
               "prisms-right-heptagonal",
	          14, 21, 9,
               true, true, true,
               true,
               true));
       i++;       
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Heptagonal",
                "Oblique with Heptagonal Base"},
               "off",
               "prisms-oblique-heptagonal",
	          14, 21, 9,
               true, true, true,
               true,
               true));
       i++;       

       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Octagonal",
                "Regular with Octagonal Base"},
               "off",
               "prisms-regular-octagonal",
	          16, 24, 10,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Octagonal",
                "Right with Octagonal Base"},
               "off",
               "prisms-right-octagonal",
	          16, 24, 10,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Octagonal",
                "Oblique with Octagonal Base"},
               "off",
               "prisms-oblique-octagonal",
	          16, 24, 10,
               true, true, true,
               true,
               true));
       i++;       
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Eneagonal",
                "Regular with Enneagonal Base"},
               "off",
               "prisms-regular-enneagonal",
	          18, 27, 11,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Eneagonal",
                "Right with Enneagonal Base"},
               "off",
               "prisms-right-enneagonal",
	          18, 27, 11,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Eneagonal",
                "Oblique with Enneagonal Base"},
               "off",
               "prisms-oblique-enneagonal",
	          18, 27, 11,
               true, true, true,
               true,
               true));
       i++;       

       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Decagonal",
                "Regular with Decagonal Base"},
               "off",
               "prisms-regular-decagonal",
	          20, 30, 12,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Decagonal",
                "Right with Decagonal Base"},
               "off",
               "prisms-right-decagonal",
	          20, 30, 12,
               true, true, true,
               true,
               true));
       i++;       
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Decagonal",
                "Oblique with Decagonal Base"},
               "off",
               "prisms-oblique-decagonal",
	          20, 30, 12,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Hendecagonal",
                "Regular with Hendecagonal Base"},
               "off",
               "prisms-regular-hendecagonal",
	          22, 33, 13,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Hendecagonal",
                "Right with Hendecagonal Base"},
               "off",
               "prisms-right-hendecagonal",
	          22, 33, 13,
               true, true, true,
               true,
               true));
       i++;       
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Hendecagonal",
                "Oblique with Hendecagonal Base"},
               "off",
               "prisms-oblique-hendecagonal",
	          22, 33, 13,
               true, true, true,
               true,
               true));
       i++;       
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Regular de Base Dodecagonal",
                "Regular with Dodecagonal Base"},
               "off",
               "prisms-regular-dodecagonal",
	          24, 36, 14,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Reto de Base Dodecagonal",
                "Right with Dodecagonal Base"},
               "off",
               "prisms-right-dodecagonal",
	          24, 36, 14,
               true, true, true,
               true,
               true));
       i++;
       
       prisms.addModel(i, new Model(prisms_trans,
               new String[]
               {"Oblíquo de Base Dodecagonal",
                "Oblique with Dodecagonal Base"},
               "off",
               "prisms-oblique-dodecagonal",
	          24, 36, 14,
               true, true, true,
               true,
               true));
       i++;       
              

       // ANTIPRISMS
       i = 0;
      antiprisms = new Category(antiprisms_trans, 10);

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Triangular",
               "Regular with Triangular Base"},
              "off",
              "antiprisms-regular-octahedron",
	          6, 12, 8,
              true, true, true,
              true,
              true));
      i++;      
      
      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Quadrangular",
               "Regular with Quadrangular Base"},
              "off",
              "antiprisms-regular-quadrangular",
	          8, 16, 10,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Pentagonal",
               "Regular with Pentagonal Base"},
              "off",
              "antiprisms-regular-pentagonal",
	          10, 20, 12,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Hexagonal",
               "Regular with Hexagonal Base"},
              "off",
              "antiprisms-regular-hexagonal",
	          12, 24, 14,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Heptagonal",
               "Regular with Heptagonal Base"},
              "off",
              "antiprisms-regular-heptagonal",
	          14, 28, 16,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Octagonal",
               "Regular with Octagonal Base"},
              "off",
              "antiprisms-regular-octagonal",
	          16, 32, 18,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Eneagonal",
               "Regular with Enneagonal Base"},
              "off",
              "antiprisms-regular-enneagonal",
	          18, 36, 20,
              true, true, true,
              true,
              true));
      i++;      
      
      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Decagonal",
               "Regular with Decagonal Base"},
              "off",
              "antiprisms-regular-decagonal",
	          20, 40, 22,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Hendecagonal",
               "Regular with Hendecagonal Base"},
              "off",
              "antiprisms-regular-hendecagonal",
	          22, 44, 24,
              true, true, true,
              true,
              true));
      i++;

      antiprisms.addModel(i, new Model(antiprisms_trans,
              new String[]
              {"Regular de Base Dodecagonal",
               "Regular with Dodecagonal Base"},
              "off",
              "antiprisms-regular-dodecagonal",
	          24, 48, 26,
              true, true, true,
              true,
              true));
      i++;      

      // PYRAMIDS
      i = 0;
      pyramids = new Category(pyramids_trans, 30);

      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Triangular",
	           "Regular with Triangular Base"},
	          "off",
	          "pyramids-regular-triangular",
	          4, 6, 4,
	          true, true, true,
	          true,
	          true));
      i++;

      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Triangular",
	           "Right with Triangular Base"},
	          "off",
	          "pyramids-right-triangular",
	          4, 6, 4,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Triangular",
	           "Oblique with Triangular Base"},
	          "off",
	          "pyramids-oblique-triangular",
	          4, 6, 4,
	          true, true, true,
	          true,
	          true));
      i++;
            
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Quadrangular",
	           "Regular with Quadrangular Base"},
	          "off",
	          "pyramids-regular-quadrangular",
	          5, 8, 5,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Quadrangular",
	           "Right with Quadrangular Base"},
	          "off",
	          "pyramids-right-quadrangular",
	          5, 8, 5,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Quadrangular",
	           "Oblique with Quadrangular Base"},
	          "off",
	          "pyramids-oblique-quadrangular",
	          5, 8, 5,
	          true, true, true,
	          true,
	          true));
      i++;      

      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Pentagonal",
	           "Regular with Pentagonal Base"},
	          "off",
	          "pyramids-regular-pentagonal",
	          6, 10, 6,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Pentagonal",
	           "Right with Pentagonal Base"},
	          "off",
	          "pyramids-right-pentagonal",
	          6, 10, 6,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Pentagonal",
	           "Oblique with Pentagonal Base"},
	          "off",
	          "pyramids-oblique-pentagonal",
	          6, 10, 6,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Hexagonal",
	           "Regular with Hexagonal Base"},
	          "off",
	          "pyramids-regular-hexagonal",
	          7, 12, 7,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Hexagonal",
	           "Right with Hexagonal Base"},
	          "off",
	          "pyramids-right-hexagonal",
	          7, 12, 7,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Hexagonal",
	           "Oblique with Hexagonal Base"},
	          "off",
	          "pyramids-oblique-hexagonal",
	          7, 12, 7,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Heptagonal",
	           "Regular with Heptagonal Base"},
	          "off",
	          "pyramids-regular-heptagonal",
	          8, 14, 8,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Heptagonal",
	           "Right with Heptagonal Base"},
	          "off",
	          "pyramids-right-heptagonal",
	          8, 14, 8,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Heptagonal",
	           "Oblique with Heptagonal Base"},
	          "off",
	          "pyramids-oblique-heptagonal",
	          8, 14, 8,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Octagonal",
	           "Regular with Octagonal Base"},
	          "off",
	          "pyramids-regular-octagonal",
	          9, 16, 9,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Octagonal",
	           "Right with Octagonal Base"},
	          "off",
	          "pyramids-right-octagonal",
	          9, 16, 9,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Octagonal",
	           "Oblique with Octagonal Base"},
	          "off",
	          "pyramids-oblique-octagonal",
	          9, 16, 9,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Eneagonal",
	           "Regular with Enneagonal Base"},
	          "off",
	          "pyramids-regular-enneagonal",
	          10, 18, 10,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Eneagonal",
	           "Right with Enneagonal Base"},
	          "off",
	          "pyramids-regular-enneagonal",
	          10, 18, 10,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Eneagonal",
	           "Oblique with Enneagonal Base"},
	          "off",
	          "pyramids-oblique-enneagonal",
	          10, 18, 10,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Decagonal",
	           "Regular with Decagonal Base"},
	          "off",
	          "pyramids-regular-decagonal",
	          11, 20, 11,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Decagonal",
	           "Right with Decagonal Base"},
	          "off",
	          "pyramids-right-decagonal",
	          11, 20, 11,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Decagonal",
	           "Oblique with Decagonal Base"},
	          "off",
	          "pyramids-oblique-decagonal",
	          11, 20, 11,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Hendecagonal",
	           "Regular with Hendecagonal Base"},
	          "off",
	          "pyramids-regular-hendecagonal",
	          12, 22, 12,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Hendecagonal",
	           "Right with Hendecagonal Base"},
	          "off",
	          "pyramids-right-hendecagonal",
	          12, 22, 12,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Hendecagonal",
	           "Oblique with Hendecagonal Base"},
	          "off",
	          "pyramids-oblique-hendecagonal",
	          12, 22, 12,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Regular de Base Dodecagonal",
	           "Regular with Dodecagonal Base"},
	          "off",
	          "pyramids-regular-dodecagonal",
	          13, 24, 13,
	          true, true, true,
	          true,
	          true));
      i++;
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Reta de Base Dodecagonal",
	           "Right with Dodecagonal Base"},
	          "off",
	          "pyramids-right-dodecagonal",
	          13, 24, 13,
	          true, true, true,
	          true,
	          true));
      i++;      
      
      pyramids.addModel(i, new Model(pyramids_trans,
	          new String[]
	          {"Oblíqua de Base Dodecagonal",
	           "Oblique with Dodecagonal Base"},
	          "off",
	          "pyramids-oblique-dodecagonal",
	          13, 24, 13,
	          true, true, true,
	          true,
	          true));
      i++;      
                 

/*
      // NONREGULAR
      i = 0;
      nonregular = new Category(nonregular_trans, 10);
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Prisma Reto de Base Triangular",
	           "NonRegular Triangular Right Prism"},
	          "off",
	          "non-regular-triangular-right-prism",
	          6, 9, 5,
	          true, true, true,
	          true,
	          true));
      i++;      

      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Prisma Oblíquo de Base Triangular",
	           "NonRegular Triangular Oblique Prism"},
	          "off",
	          "non-regular-triangular-oblique-prism",
	          6, 9, 5,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Prisma Reto de Base Quadrada",
	           "NonRegular Right Square Prism"},
	          "off",
	          "non-regular-right-square-prism",
	          8, 12, 6,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Prisma Oblíquo de Base Quadrada",
	           "NonRegular Oblique Square Prism"},
	          "off",
	          "non-regular-oblique-square-prism",
	          8, 12, 6,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Pirâmide Oblíqua de Base Quadrada",
	           "NonRegular Oblique Square Pyramid"},
	          "off",
	          "non-regular-oblique-square-pyramid",
	          5, 8, 5,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Romboedro Oblíquo",
	           "NonRegular Oblique Rhombohedron"},
	          "off",
	          "non-regular-oblique-rhombohedron",
	          8, 12, 6,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Paralelepípedo Reto Retângulo",
	           "NonRegular Right Rectangle Parallelepiped"},
	          "off",
	          "non-regular-right-rectangle-parallelepiped",
	          8, 12, 6,
	          true, true, true,
	          true,
	          true));
      i++;

      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Paralelepípedo Oblíquo",
	           "NonRegular Oblique Parallelepiped"},
	          "off",
	          "non-regular-oblique-parallelepiped",
	          8, 12, 6,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Paralelepípedo Reto",
	           "NonRegular Right Parallelepiped"},
	          "off",
	          "non-regular-right-parallelepiped",
	          8, 12, 6,
	          true, true, true,
	          true,
	          true));
      i++;
      
      nonregular.addModel(i, new Model(nonregular_trans,
	          new String[]
	          {"Pirâmide Não-Regular",
	           "NonRegular Pyramid"},
	          "off",
	          "non-regular-pyramid",
	          5, 8, 5,
	          true, true, true,
	          true,
	          true));
      i++;
 */      
        
      // TOROIDS
      i = 0;
      toroids = new Category(toroids_trans, 44);

      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 1 Buraco Triangular",
               "Toroid with 1 Triangular Hole"},
              "off",
              "toroid-triangular-genus-01",
	          9, 18, 9,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 1 Buraco Quadrado",
               "Toroid with 1 Square Hole"},
              "off",
              "toroid-quadrangular-genus-01",
	          12, 24, 12,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 1 Buraco Pentagonal",
               "Toroid with 1 Pentagonal Hole"},
              "off",
              "toroid-pentagonal-genus-01",
	          15, 30, 15,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 1 Buraco Hexagonal",
               "Toroid with 1 Hexagonal Hole"},
              "off",
              "toroid-hexagonal-genus-01",
	          18, 36, 18,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 1 Buraco Heptagonal",
               "Toroid with 1 Heptagonal Hole"},
              "off",
              "toroid-heptagonal-genus-01",
	          21, 42, 21,
              true, true, true,
              false,
              true));
      i++;      
            
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 2 Buraco Triangulares",
               "Toroid with 2 Triangular Holes"},
              "off",
              "toroid-triangular-genus-02",
	          14, 32, 16,
              true, true, true,
              false,
              true));
      i++;            
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 2 Buracos Quadrados",
               "Toroid with 2 Square Holes"},
              "off",
              "toroid-quadrangular-genus-02",
	          20, 44, 22,
              true, true, true,
              false,
              true));
      i++;      

      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 3 Buracos Quadrados",
               "Toroid with 3 Square Holes"},
              "off",
              "toroid-quadrangular-genus-03",
	          28, 64, 32,
              true, true, true,
              false,
              true));
      i++;            
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 4 Buracos Quadrados",
               "Toroid with 4 Square Holes"},
              "off",
              "toroid-quadrangular-genus-04",
	          36, 84, 42,
              true, true, true,
              false,
              true));
      i++;      
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 5 Buracos Quadrados",
               "Toroid with 5 Square Holes"},
              "off",
              "toroid-quadrangular-genus-05",
	          44, 104, 52,
              true, true, true,
              false,
              true));
      i++;      
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 6 Buracos Quadrados",
               "Toroid with 6 Square Holes"},
              "off",
              "toroid-quadrangular-genus-06",
	          52, 124, 62,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Toróide com 7 Buracos Quadrados",
               "Toroid with 7 Square Holes"},
              "off",
              "toroid-quadrangular-genus-07",
	          60, 144, 72,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 1 Alça",
               "Polyhedron with 1 Handle"},
              "off",
              "toroid-handle-genus-01",
	          48, 78, 30,
              true, true, true,
              false,
              true));
      i++;                  
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 2 Alças",
               "Polyhedron with 2 Handles"},
              "off",
              "toroid-handle-genus-02",
	          54, 93, 37,
              true, true, true,
              false,
              true));
      i++;      
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 3 Alças",
               "Polyhedron with 3 Handles"},
              "off",
              "toroid-handle-genus-03",
	          60, 108, 44,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 4 Alças",
               "Polyhedron with 4 Handles"},
              "off",
              "toroid-handle-genus-04",
	          66, 123, 51,
              true, true, true,
              false,
              true));
      i++;
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 5 Alças",
               "Polyhedron with 5 Handles"},
              "off",
              "toroid-handle-genus-05",
	          72, 138, 58,
              true, true, true,
              false,
              true));
      i++;      
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 6 Alças",
               "Polyhedron with 6 Handles"},
              "off",
              "toroid-handle-genus-06",
	          78, 153, 65,
              true, true, true,
              false,
              true));
      i++;      
      
      toroids.addModel(i, new Model(toroids_trans,
              new String[]
              {"Poliedro com 7 Alças",
               "Polyhedron with 7 Handles"},
              "off",
              "toroid-handle-genus-07",
	          84, 168, 72,
              true, true, true,
              false,
              true));
      i++;      
      
      // STEWART
      //  i = 0;
      //  stewart = new Category(stewart_trans, 25);
        
      toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cúpula Triangular Perfurada Alongada",
                 "Drilled Elongated Triangular Cupola"},
                "off",
                "stewart-drilled-elongated-triangular-cupola",
	          18, 39, 21,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Bicúpula Triangular Perfurada",
                 "Drilled Triangular Bicupola"},
                "off",
                "stewart-drilled-triangular-bicupola",
	          15, 39, 24,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cubo Truncado Perfurado",
                 "Drilled Truncated Cube"},
                "off",
                "stewart-drilled-truncated-cube",
	          32, 64, 32,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Bicúpula Quadrada Perfurada",
                 "Drilled Square Bicupola"},
                "off",
                "stewart-drilled-square-bicupola",
	          20, 48, 28,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Octaedro Truncado Perfurado",
                 "Single-drilled Truncated Octahedron"},
                "off",
                "stewart-single-drilled-truncated-octahedron",
	          30, 54, 24,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Rotunda Pentagonal Perfurada",
                 "Drilled Pentagonal Rotunda"},
                "off",
                "stewart-drilled-pentagonal-rotunda",
	          25, 60, 35,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cubo Truncado Biaumentado Perfurado",
                 "Drilled Biaugmented Truncated Cube"},
                "off",
                "stewart-drilled-biaugmented-truncated-cube",
	          48, 104, 56,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Octaedro Truncado Tetra Perfurado",
                 "Four-Drilled Truncated Octahedron"},
                "off",
                "stewart-four-drilled-truncated-octahedron",
	            30, 72, 38,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Anel Octaédrico com 8 Elos",
                 "Eight-Octahedron Ring"},
                "off",
                "stewart-eight-octahedron-ring",
	          24, 72, 48,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado Quadrado-Perfurado",
                 "Square-Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-square-drilled-truncated-cuboctahedron",
	          72, 168, 76,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado Hexágono-Perfurado",
                 "Hexagon-Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-hexagon-drilled-truncated-cuboctahedron",
	          72, 168, 84,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado Octógono-Perfurado",
                 "Octagon-Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-octagon-drilled-truncated-cuboctahedron",
	          72, 168, 88,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado Tetra-Quadrado-Perfurado",
                 "Four-Square-Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-four-square-drilled-truncated-cuboctahedron",
	          72, 136, 60,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado Biquadrado-Perfurado",
                 "Two-Square-Double-Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-two-square-double-drilled-truncated-cuboctahedron",
	          72, 132, 58,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado com Dois Buracos Perfurados",
                 "Two-Hole Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-two-hole-drilled-truncated-cuboctahedron",
	          72, 144, 62,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Truncado Duplamente Perfurado",
                 "Double Drilled Truncated Cuboctahedron"},
                "off",
                "stewart-double-drilled-truncated-cuboctahedron",
	          80, 192, 90,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Variação do Cuboctaedro Truncado Perfurado",
                 "Drilled Truncated Cuboctahedron Variation"},
                "off",
                "stewart-drilled-truncated-cuboctahedron-variation",
	          60, 128, 68,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Cuboctaedro Dissecado por Expansão em Prisma",
                 "Prism-Expanded Dissected Cuboctahedron"},
                "off",
                "stewart-prism-expanded-dissected-cuboctahedron",
	          62, 168, 86,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Rombicosidodecaedro Perfurado",
                 "Drilled Rhombicosidodecaedro"},
                "off",
                "stewart-drilled-rhombicosidodecahedron",
	          80, 168, 80,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Dodecaedro Truncado Perfurado",
                 "Drilled Truncated Dodecahedron"},
                "off",
                "stewart-drilled-truncated-dodecahedron",
	          140, 420, 260,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Dodecaedro Truncado Parcialmente Perfurado",
                 "Partially Drilled Truncated Dodecahedron"},
                "off",
                "stewart-partially-drilled-truncated-dodecahedron",
	          115, 290, 165,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Toro Cortado",
                 "Torus Slice"},
                "off",
                "stewart-torus-slice",
	            50, 120, 70,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Icosidodecaedro Truncado Rotunda-Perfurado",
                 "Rotunda-Drilled Truncated Icosidodecahedron"},
                "off",
                "stewart-rotunda-drilled-truncated-icosidodecahedron",
	          240, 540, 280,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Icosidodecaedro Truncado Cúpula-Perfurado",
                 "Cupola-Drilled Truncated Icosidodecahedron"},
                "off",
                "stewart-cupola-drilled-truncated-icosidodecahedron",
	          240, 600, 340,
                true, true, true,
                false,
                true));
        i++;

        toroids.addModel(i, new Model(toroids_trans,
                new String[]
                {"Gênero 87",
                 "Genus 87"},
                "off",
                "stewart-genus-87-new-record",
	          1238, 3708, 2298,
                true, true, true,
                false,
                true));
        i++;

        // LEONARDO
        i = 0;
        leonardo = new Category(leonardo_trans, 5);

        leonardo.addModel(i, new Model(leonardo_trans,
                new String[]
                {"Cosmograma 1",
                 "Cosmogram 1"},
                "off",
                "leonardo-cosmogram-1",
	            280, 720, 360,
                false, true, true,
                false,
                true));
        leonardo.getModel(i).setImproveUnfolding(true, 6);
        i++;

        leonardo.addModel(i, new Model(leonardo_trans,
                new String[]
                {"Cosmograma 2",
                 "Cosmogram 2"},
                "off",
                "leonardo-cosmogram-2",
	            280, 720, 360,
                false, true, true,
                false,
                true));
        leonardo.getModel(i).setImproveUnfolding(true, 6);
        i++;

        leonardo.addModel(i, new Model(leonardo_trans,
                new String[]
                {"Cosmograma 3",
                 "Cosmogram 3"},
                "off",
                "leonardo-cosmogram-3",
	            280, 720, 360,
                false, true, true,
                false,
                true));
        leonardo.getModel(i).setImproveUnfolding(true, 6);
        i++;

        leonardo.addModel(i, new Model(leonardo_trans,
                new String[]
                {"Cosmograma 4",
                 "Cosmogram 4"},
                "off",
                "leonardo-cosmogram-4",
	            280, 720, 360,
                false, true, true,
                false,
                true));
        leonardo.getModel(i).setImproveUnfolding(true, 6);
        i++;
        
        leonardo.addModel(i, new Model(leonardo_trans,
                new String[]
                {"Cosmograma 5",
                 "Cosmogram 5"},
                "off",
                "leonardo-cosmogram-5",
	            280, 720, 360,
                false, true, true,
                false,
                true));
        leonardo.getModel(i).setImproveUnfolding(true, 6);
        i++;        

        // ANIMAL
        i = 0;
        animal = new Category(animal_trans, 4);

        animal.addModel(i, new Model(animal_trans,
               new String[]
               {"Cavalo",
                "Horse"},
               "off",
               "animal-horse",
	           173, 513, 342,
               true, true, true,
               false,
               true));
        animal.getModel(i).setImproveUnfolding(true, 6);
        i++;

        animal.addModel(i, new Model(animal_trans,
                 new String[]
                 {"Coelho",
                  "Bunny"},
                 "off",
                 "animal-bunny",
	             157, 465, 310,
                 true, true, true,
                 false,
                 true));
        animal.getModel(i).setImproveUnfolding(true, 6);
        i++;

        animal.addModel(i, new Model(animal_trans,
               new String[]
               {"Gatinho",
                "Kitten"},
               "off",
               "animal-kitten",
	           175, 525, 350,
               true, true, true,
               false,
               true));
        animal.getModel(i).setImproveUnfolding(true, 6);
        i++;
          
        animal.addModel(i, new Model(animal_trans,
               new String[]
               {"Dromedário",
                "Dromedary"},
               "off",
                "animal-dromedary",
	            443, 1329, 886,
                true, true, true,
                false,
                true));
        animal.getModel(i).setImproveUnfolding(true, 5);
        i++;
        
        
        // HEXAHEDRA
        i = 0;
        hexahedra = new Category(hexahedra_trans, 13);
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
    	          new String[]
    	          {"Hexaedro 3, 3, 3, 3, 3, 3",
    	           "Hexahedron 3, 3, 3, 3, 3, 3"},
    	          "off",
    	          "hexahedra-333333",
    	          5, 9, 6,
    	          true, true, true,
    	          true,
    	          true));
        i++;
          
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	              new String[]
  	              {"Hexaedro 5, 3, 3, 3, 3, 3",
  	               "Hexahedron 5, 3, 3, 3, 3, 3"},
  	               "off",
  	              "hexahedra-533333",
  	              6, 10, 6,
  	              true, true, true,
  	              true,
  	              true));
        i++;
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	              new String[]
  	              {"Hexaedro 4, 4, 3, 3, 3, 3",
  	               "Hexahedron 4, 4, 3, 3, 3, 3"},
  	              "off",
  	              "hexahedra-443333",
  	              6, 10, 6,
  	              true, true, true,
  	              true,
  	              true));
        i++;
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
    	          new String[]
    	          {"Hexaedro 5, 4, 4, 3, 3, 3",
    	           "Hexahedron 5, 4, 4, 3, 3, 3"},
    	          "off",
    	          "hexahedra-544333",
    	          7, 11, 6,
    	          true, true, true,
    	          true,
    	          true));
        i++;        
          
        hexahedra.addModel(i, new Model(hexahedra_trans,
   	             new String[]
    	         {"Hexaedro 4, 4, 4, 4, 3, 3",
    	          "Hexahedron 4, 4, 4, 4, 3, 3"},
    	         "off",
    	         "hexahedra-444433",
    	         7, 11, 6,
    	         true, true, true,
    	         true,
    	         true));
        i++;
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	             new String[]
   	             {"Hexaedro 5, 5, 4, 4, 3, 3",
   	              "Hexahedron 5, 5, 4, 4, 3, 3"},
   	              "off",
   	             "hexahedra-554433",
   	             8, 12, 6,
   	             true, true, true,
   	             true,
   	             true));
        i++;        
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
 	             new String[]
  	             {"Hexaedro 4, 4, 4, 4, 4, 4 (Cubo)",
  	              "Hexahedron 4, 4, 4, 4, 4, 4 (Cube)"},
  	              "off",
  	             "hexahedra-444444",
  	             8, 12, 6,
  	             true, true, true,
  	             true,
  	             true));
        i++;        
         
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	          new String[]
  	          {"Hexaedro 4, 4, 4, 4, 4, 4 (Prisma Reto de Base Quadrada)",
  	           "Hexahedron 4, 4, 4, 4, 4, 4 (NonRegular Right Square Prism)"},
  	          "off",
  	          "hexahedra-right-square-prism",
  	          8, 12, 6,
  	          true, true, true,
  	          true,
  	          true));
        i++;
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	          new String[]
  	          {"Hexaedro 4, 4, 4, 4, 4, 4 (Prisma Oblíquo de Base Quadrada)",
  	           "Hexahedron 4, 4, 4, 4, 4, 4 (NonRegular Oblique Square Prism)"},
  	          "off",
  	          "hexahedra-oblique-square-prism",
  	          8, 12, 6,
  	          true, true, true,
  	          true,
  	          true));
        i++;        
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	          new String[]
  	          {"Hexaedro 4, 4, 4, 4, 4, 4 (Romboedro Oblíquo)",
  	           "Hexahedron 4, 4, 4, 4, 4, 4 (NonRegular Oblique Rhombohedron)"},
  	          "off",
  	          "hexahedra-oblique-rhombohedron",
  	          8, 12, 6,
  	          true, true, true,
  	          true,
  	          true));
        i++;
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	          new String[]
  	          {"Hexaedro 4, 4, 4, 4, 4, 4 (Paralelepípedo Reto Retângulo)",
  	           "Hexahedron 4, 4, 4, 4, 4, 4 (NonRegular Right Rectangle Parallelepiped)"},
  	          "off",
  	          "hexahedra-right-rectangle-parallelepiped",
  	          8, 12, 6,
  	          true, true, true,
  	          true,
  	          true));
        i++;

        hexahedra.addModel(i, new Model(hexahedra_trans,
  	          new String[]
  	          {"Hexaedro 4, 4, 4, 4, 4, 4 (Paralelepípedo Oblíquo)",
  	           "Hexahedron 4, 4, 4, 4, 4, 4 (NonRegular Oblique Parallelepiped)"},
  	          "off",
  	          "hexahedra-oblique-parallelepiped",
  	          8, 12, 6,
  	          true, true, true,
  	          true,
  	          true));
        i++;
        
        hexahedra.addModel(i, new Model(hexahedra_trans,
  	          new String[]
  	          {"Hexaedro 4, 4, 4, 4, 4, 4 (Paralelepípedo Reto)",
  	           "Hexahedron 4, 4, 4, 4, 4, 4 (NonRegular Right Parallelepiped)"},
  	          "off",
  	          "hexahedra-right-parallelepiped",
  	          8, 12, 6,
  	          true, true, true,
  	          true,
  	          true));
        i++;        
        
        
        // ISOHEDRA
        i = 0;
        isohedra = new Category(isohedra_trans, 30);
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
               {"Cubo",
                 "Cube"},
                "off",
                "isohedra-cube",
   	            8, 12, 6,
                true, true, true,
                true,
                true));            
        i++;
         
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Disdiakis",
                 "Disdyakis Dodecahedron"},
                "off",
                "isohedra-disdyakis-dodecahedron",
    	        26, 72, 48,
                true, true, true,
                true,
                true));            
        i++;
            
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Hexecontaedro Deltoidal",
                 "Deltoidal Hexecontahedron"},
                "off",
                "isohedra-deltoidal-hexecontahedron",
     	        62, 120, 60,
                true, true, true,
                true,
                true));            
        i++;
             
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
               {"Icositetraedro Deltoidal",
                "Deltoidal Icositetrahedron"},
               "off",
               "isohedra-deltoidal-icositetrahedron",
      	       26, 48, 24,
               true, true, true,
               true,
               true));            
        i++;
              
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Triacontaedro Disdiakis",
                 "Disdyakis Triacontahedron"},
                "off",
                "isohedra-disdyakis-triacontahedron",
       	        62, 180, 120,
                true, true, true,
                true,
                true));            
         i++;
               
         isohedra.addModel(i, new Model(isohedra_trans,
                 new String[]
                 {"Dodecaedro",
                  "Dodecahedron"},
                 "off",
                 "isohedra-dodecahedron",
                 20, 30, 12,
                 true, true, true,
                 true,
                 true));            
        i++;                
           
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Dyakis",
                 "Dyakis Dodecahedron"},
                "off",
                "isohedra-dyakis-dodecahedron",
                26, 48, 24,
                true, true, true,
                true,
                true));            
        i++;
                 
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Tetraedro Hexakis",
                 "Hexakis Tetrahedron"},
                "off",
                "isohedra-hexakis-tetrahedron",
                14, 36, 24,
                true, true, true,
                true,
                true));            
        i++;
                  
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Icosaedro",
                 "Icosahedron"},
                "off",
                "isohedra-icosahedron",
                12, 30, 20,
                true, true, true,
                true,
                true));            
        i++;
                   
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Tetraedro Isósceles",
                 "Isosceles Tetrahedron"},
                "off",
                "isohedra-isosceles-tetrahedron",
                4, 6, 4,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Pentagonal Octaedral",        		
                 "Octahedral Pentagonal Dodecahedron"},
                "off",
                "isohedra-octahedral-pentagonal-dodecahedron",
                20, 30, 12,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Octaedro",        		
                 "Octahedron"},
                "off",
                "isohedra-octahedron",
                6, 12, 8,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Hexecontaedro Pentagonal",        		
                 "Pentagonal Hexecontahedron"},
                "off",
                "isohedra-pentagonal-hexecontahedron",
                92, 150, 60,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Icositetraedro Pentagonal",        		
                 "Pentagonal Icositetrahedron"},
                "off",
                "isohedra-pentagonal-icositetrahedron",
                38, 60, 24,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Pentakis",        		
                 "Pentakis Dodecahedron"},
                "off",
                "isohedra-pentakis-dodecahedron",
                32, 90, 60,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Rômbico",        		
                 "Rhombic Dodecahedron"},
                "off",
                "isohedra-rhombic-dodecahedron",
                14, 24, 12,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Triacontaedro Rômbico",        		
                 "Rhombic Triacontahedron"},
                "off",
                "isohedra-rhombic-triacontahedron",
                32, 60, 30,
                true, true, true,
                true,
                true));            
        i++;     
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Tetraedro Escaleno",        		
                 "Scalene Tetrahedron"},
                "off",
                "isohedra-scalene-tetrahedron",
                4, 6, 4,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Pentagonal Tetragonal",        		
                 "Tetragonal Pentagonal Dodecahedron"},
                "off",
                "isohedra-tetragonal-pentagonal-dodecahedron",
                20, 30, 12,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Tetraedro",        		
                 "Tetrahedron"},
                "off",
                "isohedra-tetrahedron",
                4, 6, 4,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Hexaedro Tetrakis",        		
                 "Tetrakis Hexahedron"},
                "off",
                "isohedra-tetrakis-hexahedron",
                14, 36, 24,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Diedro Trapezoidal Básico",        		
                 "Trapezoidal Dihedron (Basic)"},
                "off",
                "isohedra-trapezoidal-dihedron",
                8, 12, 6,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Diedro Trapezoidal Assimétrico",        		
                 "Trapezoidal Dihedron (Skewed)"},
                "off",
                "isohedra-trapezoidal-dihedron-skewed",
                8, 12, 6,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Dodecaedro Trapezoidal",        		
                 "Trapezoidal Dodecahedron"},
                "off",
                "isohedra-trapezoidal-dodecahedron",
                14, 24, 12,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Icosaedro Triakis",        		
                 "Triakis Icosahedron"},
                "off",
                "isohedra-triakis-icosahedron",
                32, 90, 60,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Octaedro Triakis",        		
                 "Triakis Octahedron"},
                "off",
                "isohedra-triakis-octahedron",
                14, 36, 24,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Tetraedro Triakis",        		
                 "Triakis Tetrahedron"},
                "off",
                "isohedra-triakis-tetrahedron",
                8, 18, 12,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Diedro Triangular",        		
                 "Triangular Dihedron"},
                "off",
                "isohedra-triangular-dihedron",
                10, 24, 16,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Diedro Triangular Assimétrico Dentro-Fora",        		
                 "Triangular Dihedron Skewed In-Out"},
                "off",
                "isohedra-triangular-dihedron-skewed-in-out",
                10, 24, 16,
                true, true, true,
                true,
                true));            
        i++;
        
        isohedra.addModel(i, new Model(isohedra_trans,
                new String[]
                {"Diedro Triangular Assimétrico Para Cima-Para Baixo",        		
                 "Triangular Dihedron Skewed Up-Down"},
                "off",
                "isohedra-triangular-dihedron-skewed-up-down",
                6, 12, 8,
                true, true, true,
                true,
                true));            
        i++;

        // Triplets              
        i = 0;
        triplets = new Category(triplets_trans,42 );
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"ASA",
                 "ASA"},
                "off",
                "triplet-asa",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"AVO",
                 "AVO"},
                "off",
                "triplet-avo",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"BEM",
                 "BEM"},
                "off",
                "triplet-bem",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"BOM",
                 "BOM"},
                "off",
                "triplet-bom",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"CEM",
                 "CEM"},
                "off",
                "triplet-cem",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"COR",
                 "COR"},
                "off",
                "triplet-cor",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"DIA",
                 "DIA"},
                "off",
                "triplet-dia",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"DNA",
                 "DNA"},
                "off",
                "triplet-dna",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"DEZ",
                 "DEZ"},
                "off",
                "triplet-dez",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"ELO",
                 "ELO"},
                "off",
                "triplet-elo",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"FAX",
                 "FAX"},
                "off",
                "triplet-fax",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"FIM",
                 "FIM"},
                "off",
                "triplet-fim",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"GOL",
                 "GOL"},
                "off",
                "triplet-gol",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"LAR",
                 "LAR"},
                "off",
                "triplet-lar",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"LEI",
                 "LEI"},
                "off",
                "triplet-lei",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"LER",
                 "LER"},
                "off",
                "triplet-ler",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"LOG",
                 "LOG"},
                "off",
                "triplet-log",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"LUA",
                 "LUA"},
                "off",
                "triplet-lua",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"LUZ",
                 "LUZ"},
                "off",
                "triplet-luz",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"MAR",
                 "MAR"},
                "off",
                "triplet-mar",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"MDC",
                 "MDC"},
                "off",
                "triplet-mdc",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"MEC",
                 "MEC"},
                "off",
                "triplet-br-mec",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"MEL",
                 "MEL"},
                "off",
                "triplet-mel",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"MIL",
                 "MIL"},
                "off",
                "triplet-mil",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"MMC",
                 "MMC"},
                "off",
                "triplet-mmc",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"NAU",
                 "NAU"},
                "off",
                "triplet-nau",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"OCA",
                 "OCA"},
                "off",
                "triplet-oca",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"PAI",
                 "PAI"},
                "off",
                "triplet-pai",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"PAR",
                 "PAR"},
                "off",
                "triplet-par",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"PAZ",
                 "PAZ"},
                "off",
                "triplet-paz",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"PUC",
                 "PUC"},
                "off",
                "triplet-puc",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"REI",
                 "REI"},
                "off",
                "triplet-rei",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"RIM",
                 "RIM"},
                "off",
                "triplet-rim",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"RIO",
                 "RIO"},
                "off",
                "triplet-rio",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"RIR",
                 "RIR"},
                "off",
                "triplet-rir",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"RUA",
                 "RUA"},
                "off",
                "triplet-rua",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"SOL",
                 "SOL"},
                "off",
                "triplet-sol",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"SOM",
                 "SOM"},
                "off",
                "triplet-som",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"SOS",
                 "SOS"},
                "off",
                "triplet-sos",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"TIA",
                 "TIA"},
                "off",
                "triplet-tia",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"UVA",
                 "UVA"},
                "off",
                "triplet-uva",
                false, true, true,
                true,
                true));
        i++;
        
        triplets.addModel(i, new Model(triplets_trans,
                new String[]
                {"WEB",
                 "WEB"},
                "off",
                "triplet-web",
                false, true, true,
                true,
                true));
        i++;
    }
}; // End of PjShadow_IP class
