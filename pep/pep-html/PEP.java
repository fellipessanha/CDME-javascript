//////////////////////////////////////////////////////////////////////////////
// PEP class
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

public class PEP extends Applet
{
   public double xVal = 0.5;
   
   public double tVal = 1.0;
   
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
   
   protected PvDisplay disp;
   protected PgElementSet pyramid;
   

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

      // Bounding pyramid
      disp.showBndBox(false);

      // Background color
      disp.setBackgroundColor(Color.WHITE);

      // Applet layout, quite simple now
      setLayout(new BorderLayout());
      add(disp, BorderLayout.CENTER);
      
      // Box builder
      pyramid = new PgElementSet(3);
      pyramid.setNumVertices(8);
      pyramid.setNumElements(5);
      
      pyramid.setElement(0, 0, 3, 2, 1);
      pyramid.setElement(1, 0, 4, 3);
      pyramid.setElement(2, 5, 0, 1);
      pyramid.setElement(3, 1, 2, 6);
      pyramid.setElement(4, 3, 7, 2);
      pyramid.setGlobalElementColor(Color.YELLOW);
      pyramid.setGlobalEdgeColor(Color.RED);
      
      updatePyramid();
      
      disp.addGeometry(pyramid);
      
      disp.setEnabledZBuffer(true); 
      
      // Key events
      internalKeyListener = new KeyAdapter() 
      {
          public void keyPressed(KeyEvent keyEvent) 
          {
             if (keyEvent.getKeyCode() == KeyEvent.VK_1)
       	     {
             	 tVal -= 0.05;
             	 if (tVal < 0.0)
             	 {
             		 tVal = 0.0;
             	 }
             	 updatePyramid();
      	     };
        	 
             if (keyEvent.getKeyCode() == KeyEvent.VK_2)
      	     {
            	 tVal += 0.05;
            	 if (tVal > 1.0)
            	 {
            		 tVal = 1.0;
            	 }
            	 updatePyramid();
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
    
    public void updatePyramid()
    {
        double v = xVal*Math.sqrt(2.0)/2.0;
        double w = Math.sqrt(2.0) - xVal*Math.sqrt(2.0)/2.0;
        double a = tVal*(Math.PI - Math.acos(v/w));
        
        pyramid.setVertex(0,  v,  v, 0);
        pyramid.setVertex(1, -v,  v, 0);
        pyramid.setVertex(2, -v, -v, 0);
        pyramid.setVertex(3,  v, -v, 0);
        
        pyramid.setVertex(4,  v + w*Math.cos(a), 0, w*Math.sin(a));
        pyramid.setVertex(6, -v - w*Math.cos(a), 0, w*Math.sin(a));
        
        pyramid.setVertex(5, 0,  v + w*Math.cos(a), w*Math.sin(a));        
        pyramid.setVertex(7, 0, -v - w*Math.cos(a), w*Math.sin(a));        
        
    	pyramid.update(pyramid);
    }
};
