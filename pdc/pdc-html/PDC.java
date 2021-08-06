//////////////////////////////////////////////////////////////////////////////
// PDC class
//
// Last update: 27/07/2010
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

public class PDC extends Applet
{
   public double xVal = 5.0;
   
   public double aVal = 90.0;
   
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
   
   protected PvDisplay disp;
   protected PgElementSet box;
   

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
      box.setNumVertices(12);
      box.setNumElements(5);
      
      box.setElement(0, 0, 1, 2, 3);
      box.setElement(1, 4, 0, 3, 11);
      box.setElement(2, 5, 6, 1, 0);
      box.setElement(3, 1, 7, 8, 2);
      box.setElement(4, 3, 2, 9, 10); 
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
    	box.setVertex(0, (50 - 2*xVal)/2.0, (30 - 2*xVal)/2.0, 0.0);
    	box.setVertex(4, (50 - 2*xVal)/2.0 + xVal*Math.cos(aVal*Math.PI/180.0), (30 - 2*xVal)/2.0, xVal*Math.sin(aVal*Math.PI/180.0));
    	box.setVertex(5, (50 - 2*xVal)/2.0, (30 - 2*xVal)/2.0 + xVal*Math.cos(aVal*Math.PI/180.0), xVal*Math.sin(aVal*Math.PI/180.0));
    	
    	box.setVertex(1, -box.getVertex(0).getEntry(0),
    			         +box.getVertex(0).getEntry(1),
    			         +box.getVertex(0).getEntry(2));
    	box.setVertex(2, -box.getVertex(0).getEntry(0),
		                 -box.getVertex(0).getEntry(1),
		                 +box.getVertex(0).getEntry(2));
    	box.setVertex(3, +box.getVertex(0).getEntry(0),
                         -box.getVertex(0).getEntry(1),
                         +box.getVertex(0).getEntry(2));
    	
    	box.setVertex(6, -box.getVertex(5).getEntry(0),
                         +box.getVertex(5).getEntry(1),
                         +box.getVertex(5).getEntry(2));
    	box.setVertex(9, -box.getVertex(5).getEntry(0),
                         -box.getVertex(5).getEntry(1),
                         +box.getVertex(5).getEntry(2));
    	box.setVertex(10, +box.getVertex(5).getEntry(0),
                          -box.getVertex(5).getEntry(1),
                          +box.getVertex(5).getEntry(2));

    	box.setVertex(7, -box.getVertex(4).getEntry(0),
                         +box.getVertex(4).getEntry(1),
                         +box.getVertex(4).getEntry(2));
    	box.setVertex(8, -box.getVertex(4).getEntry(0),
				 -box.getVertex(4).getEntry(1),
                         +box.getVertex(4).getEntry(2));
    	box.setVertex(11, +box.getVertex(4).getEntry(0),
                          -box.getVertex(4).getEntry(1),
                          +box.getVertex(4).getEntry(2));    	    	
        box.update(box);  	
    }
};
