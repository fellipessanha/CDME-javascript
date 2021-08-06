//////////////////////////////////////////////////////////////////////////////
// PCT class
//
// Last update: 08/03/2011
//////////////////////////////////////////////////////////////////////////////

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jv.viewer.PvDisplay;
import jv.viewer.PvViewer;
import jv.geom.PgElementSet;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;

import netscape.javascript.JSObject;
import netscape.javascript.JSException;

public class PCT extends Applet
{
   public double xVal = 2.0;
   
   public double aVal = 90.0;
   
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
   
   protected PvDisplay disp;
   protected PgElementSet box;
   
   protected double boxH = 10;
   protected double boxW = 16;
   

   // This procedure is executed when the applet starts
   public void init()
   {
/*	   
      // JavaScript Live Connect
      // Get references to HTML textfields via JavaScript        
      try
      {
         window = (JSObject) JSObject.getWindow(this); // this = applet
      } 
      catch(JSException jse) 
      {
         jse.printStackTrace();
         System.out.println(jse.getWrappedException().toString());           
         isLiveConnectOK = false;
         System.out.println("JSException ...");
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
      }
 */                   
       
      // Viewer
      PvViewer viewer = new PvViewer(this, null);

      // Display
      disp = new PvDisplay();

      // Axes
      disp.showAxes(false);

      // Bounding box
      disp.showBndBox(false);

      // Background color
      disp.setBackgroundColor(Color.WHITE);

      // Applet layout, quite simple now
      setLayout(new BorderLayout());
      add(disp, BorderLayout.CENTER);
      
      // Box builder
      box = new PgElementSet(3);
      box.setNumVertices(14);
      box.setNumElements(6);
      
      box.setElement(0, 0, 9, 6, 3);
      box.setElement(1, 11, 10, 9, 0);
      box.setElement(2, 1, 0, 3, 2);
      box.setElement(3, 3, 6, 5, 4);
      box.setElement(4, 9, 8, 7, 6);
      box.setElement(5, 13, 12, 10, 11);
      box.setGlobalElementColor(Color.YELLOW);
      box.setGlobalEdgeColor(Color.RED);
      
      updateBox();
      
      disp.addGeometry(box);
      
      // Key events
      internalKeyListener = new KeyAdapter() 
      {
          public void keyPressed(KeyEvent keyEvent) 
          {
             if (keyEvent.getKeyCode() == KeyEvent.VK_1)
       	     {
             	 aVal -= 5.0;
             	 if (aVal < 0.0)
             	 {
             		 aVal = 0.0;
             	 }
             	 updateBox();
      	     };
        	 
             if (keyEvent.getKeyCode() == KeyEvent.VK_2)
      	     {
            	 aVal += 5.0;
            	 if (aVal > 90.0)
            	 {
            		 aVal = 90.0;
            	 }
            	 updateBox();
     	     };     	          	     
          }	   
          	
      };
      disp.addKeyListener(internalKeyListener);
     
      disp.setName("Modelo 3D: clique e arraste!");
      disp.showTitle(true);
      disp.setEnabledAntiAlias(true);

/*      
      if ((window != null))
      {
          // window.eval("jvxAppletOK = true");
      }
 */            
    }; // End of init()
    
    public void updateBox()
    {
    	double bwVal = boxW/2.0 - xVal;
    	double bhVal = boxH - 2.0*xVal;
    	
    	box.setVertex( 0,  bwVal/2.0,   bhVal/2.0, 0.0);
    	box.setVertex( 3, -bwVal/2.0,   bhVal/2.0, 0.0);
    	box.setVertex( 6, -bwVal/2.0,  -bhVal/2.0, 0.0);
    	box.setVertex( 9,  bwVal/2.0,  -bhVal/2.0, 0.0);    	
    	
    	box.setVertex(10,  bwVal/2.0 + xVal*Math.cos(aVal*Math.PI/180.0),
    			          -bhVal/2.0,
    			           xVal*Math.sin(aVal*Math.PI/180.0));
    	box.setVertex(11,  bwVal/2.0 + xVal*Math.cos(aVal*Math.PI/180.0),
		                   bhVal/2.0,
		                   xVal*Math.sin(aVal*Math.PI/180.0));
    	box.setVertex( 4, -box.getVertex(11).getEntry(0),
    			           box.getVertex(11).getEntry(1),
    			           box.getVertex(11).getEntry(2));
    	box.setVertex( 5, -box.getVertex(10).getEntry(0),
		                   box.getVertex(10).getEntry(1),
		                   box.getVertex(10).getEntry(2));

    	box.setVertex( 1,  bwVal/2.0,
		                   bhVal/2.0 + xVal*Math.cos(aVal*Math.PI/180.0),
		                   xVal*Math.sin(aVal*Math.PI/180.0));
        box.setVertex( 2, -bwVal/2.0,
        		           bhVal/2.0 + xVal*Math.cos(aVal*Math.PI/180.0),
                           xVal*Math.sin(aVal*Math.PI/180.0));
    	box.setVertex( 8,  box.getVertex(1).getEntry(0),
		                  -box.getVertex(1).getEntry(1),
		                   box.getVertex(1).getEntry(2));
        box.setVertex( 7,  box.getVertex(2).getEntry(0),
                          -box.getVertex(2).getEntry(1),
                           box.getVertex(2).getEntry(2));
    	
        double Px = xVal*Math.cos(aVal*Math.PI/180.0);
        double Pz = xVal*Math.sin(aVal*Math.PI/180.0);
        double Qx = (xVal + bwVal)*Math.cos(aVal*Math.PI/180.0);
        double Qz = (xVal + bwVal)*Math.sin(aVal*Math.PI/180.0); 
    	box.setVertex(13,  bwVal/2.0 + (Qx - Px)*Math.cos(aVal*Math.PI/180.0) - (Qz - Pz)*Math.sin(aVal*Math.PI/180.0) + Px,
                           bhVal/2.0,
                           (Qx - Px)*Math.sin(aVal*Math.PI/180.0) + (Qz - Pz)*Math.cos(aVal*Math.PI/180.0) + Pz);
        box.setVertex(12,  box.getVertex(13).getEntry(0),
                          -box.getVertex(13).getEntry(1),
                           box.getVertex(13).getEntry(2));

    	
    	box.update(box);  	
    }
};
