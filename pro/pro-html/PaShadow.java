///////////////////////////////////////////////////////////////////////////////
// PaShadow Applet class
//
// Last update: 13/01/2008
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
// Native JAVA imports
///////////////////////////////////////////////////////////////////////////////
import java.applet.Applet;
import java.awt.*;

///////////////////////////////////////////////////////////////////////////////
// JavaView imports
///////////////////////////////////////////////////////////////////////////////
import jv.object.*;
import jv.viewer.PvViewer;
import jv.project.PvLightIf;
import jv.project.PvDisplayIf;


///////////////////////////////////////////////////////////////////////////////
// Shadow applet
///////////////////////////////////////////////////////////////////////////////
public class PaShadow extends Applet
{
   public Frame m_frame = null; ///////////////////////////////////////////////
                                // Frame if run standalone, null if run  as
                                // applet.
                                ///////////////////////////////////////////////

   protected PvViewer m_viewer; ///////////////////////////////////////////////
                                // 3D-viewer window for graphics output and
                                // which is embedded into the applet.
                                ///////////////////////////////////////////////

   ////////////////////////////////////////////////////////////////////////////
   // Applet parameters: {"Name", "Type", "Default value", "Description"}
   ////////////////////////////////////////////////////////////////////////////
   protected String [][] m_parm =
   {
      {"Console", "String", "Hide", "Show/Hide VGP-console for debugging"},
      {"Control", "String", "Hide", "Show/Hide control panel"},
      {"Frame", "String", "Hide", "Show/Hide frame around applet"},
      {"Panel",   "String", "Project",
       "Name of initial panel if control panel is showing"}
   };

   ////////////////////////////////////////////////////////////////////////////
   // Interface used by design tools to show properties of applet. This method
   // returns a list of string arrays, each of  length  4  rather  than  3  as
   // suggest by Java. The additional string at third  position  contains  the
   // value of the parameter.
   ////////////////////////////////////////////////////////////////////////////
   public String[][] getParameterInfo()
   {
      return m_parm;
   }; // End of getParameterInfo()


   ////////////////////////////////////////////////////////////////////////////
   // Applet's interface to inform about author, version, and copyright
   ////////////////////////////////////////////////////////////////////////////
   public String getAppletInfo()
   {
      return("Nome: " + this.getClass().getName()+ "\r\n" +
             "Autor: " + "Humberto José Bortolossi" + "\r\n" +
             "Versão: " + "1.00" + "\r\n" +
             "Geometria Espacial" + "\r\n");
   }; // End of getAppletInfo()


   ////////////////////////////////////////////////////////////////////////////
   // Configure and initialize the viewer,  load  system  and  user  projects.
   // One of the user projects must be selected here.
   ////////////////////////////////////////////////////////////////////////////
   public void init()
   {
      // Create viewer for viewing 3d geometries
      m_viewer = new PvViewer(this, m_frame);

      // Get applet parameters from the source html file
      int idiom = Integer.valueOf(m_viewer.getParameter("idiom")).intValue();
      String category = m_viewer.getParameter("category");
      String solid = m_viewer.getParameter("solid");
      double initial_transparency = 
    	  Double.valueOf(m_viewer.getParameter("initial_transparency")).doubleValue();
      boolean enable_choice =
    	  Boolean.valueOf(m_viewer.getParameter("enable_choice")).booleanValue();

      // Create and load a project
      PjShadow m_p = new PjShadow(getCodeBase(), idiom, category, solid,
    		                  initial_transparency,
    		                  enable_choice);
      m_viewer.addProject(m_p);
      m_viewer.selectProject(m_p);

      // Get 3d display from viewer and add it to applet
      setLayout(new BorderLayout());
      PvDisplayIf disp = m_viewer.getDisplay();

      // Enable Z-buffer
      disp.setEnabledZBuffer(true);     

      // Background color
      disp.setBackgroundColor(Color.WHITE);

      // Scale camera such that current geometries fit into display
      disp.fit();
      
      // Remove lights
      disp.setLightingModel(PvLightIf.LIGHT_AMBIENT);
      

      // Get 3d display from viewer and add it to applet
      setLayout(new BorderLayout());
      add((Component)disp, BorderLayout.CENTER);
      add(m_viewer.getPanel(PsViewerIf.PROJECT), BorderLayout.EAST);
      validate();

      m_viewer.showPanel(PsViewerIf.MATERIAL);
   }; // End of init()
        

   ////////////////////////////////////////////////////////////////////////////
   // Standalone application support. The main() method acts as the applet's
   // entry point when it is run as a standalone application. It is  ignored
   // if the applet is run from within an HTML page.
   ////////////////////////////////////////////////////////////////////////////
   public static void main(String args[])
   {
      PaShadow va = new PaShadow();
      // Create toplevel window of application containing the applet
      Frame frame = new jv.object.PsMainFrame(va, args);
      frame.pack();
      va.m_frame = frame;
      va.init();
      va.start();
      frame.setBounds(new Rectangle(220, 5, 800, 550));
      frame.setVisible(true);
   }; // End of main()


   ////////////////////////////////////////////////////////////////////////////
   // Print info while initializing applet and viewer
   ////////////////////////////////////////////////////////////////////////////
   public void paint(Graphics g)
   {
      g.setColor(Color.blue);
      g.drawString("Geometry Browser, Version " +
                   PsConfig.getVersion(), 20, 40);
      g.drawString("Loading Projects ...", 20, 60);
   }; // End of paint()


   ////////////////////////////////////////////////////////////////////////////
   // Does clean-up when applet is destroyed by the browser. Here we just
   // close and dispose all our control windows.
   ////////////////////////////////////////////////////////////////////////////
   public void destroy()
   {
      m_viewer.destroy();
   }; // End of destroy()


   ////////////////////////////////////////////////////////////////////////////
   // Start viewer, e.g. start animation if requested
   ////////////////////////////////////////////////////////////////////////////
   public void start()
   {
      m_viewer.start();
   }; // End of start()


   ////////////////////////////////////////////////////////////////////////////
   // Stop viewer, e.g. stop animation if requested
   ////////////////////////////////////////////////////////////////////////////
   public void stop()
   {
      m_viewer.stop();
   }; // End of stop()
}; // End of PaShadow class

