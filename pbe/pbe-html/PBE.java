//////////////////////////////////////////////////////////////////////////////
// PBE class
//
// Last update: 16/02/2011
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

public class PBE extends Applet
{
   public double xVal = 0.7;
   public double aVal = 0.6;
     
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
      
   protected PvDisplay disp;
   protected PgElementSet trough;
   protected PgElementSet water_level;
   
   protected double l = 1.0;
   protected double d = 7.0;
   

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
      
      // Trough builder
      trough = new PgElementSet(3);
      trough.setNumVertices(8);
      trough.setNumElements(5);
      
      trough.setElement(0, 0, 3, 2, 1);
      trough.setElement(1, 0, 1, 5, 4);
      trough.setElement(2, 2, 1, 5, 6);
      trough.setElement(3, 2, 3, 7, 6);
      trough.setElement(4, 3, 0, 4, 7); 
      trough.setGlobalElementColor(Color.YELLOW);
      trough.setGlobalEdgeColor(Color.RED);
           
      
      // Water level
      water_level = new PgElementSet(3);
      water_level.setNumVertices(4);
      water_level.setNumElements(1);
      
      water_level.setElement(0, 0, 3, 2, 1);
      water_level.setGlobalElementColor(Color.CYAN);
      water_level.setGlobalEdgeColor(Color.CYAN);
      water_level.showTransparency(true);
      water_level.setTransparency(0.2);      
      
      updateTrough();
      
      disp.addGeometry(trough);
      disp.addGeometry(water_level);
      
      // Key events
      internalKeyListener = new KeyAdapter() 
      {
          public void keyPressed(KeyEvent keyEvent) 
          {
             if (keyEvent.getKeyCode() == KeyEvent.VK_1)
       	     {
                aVal = aVal - 0.05;
                if (aVal < 0.0)
                {
            	   aVal = 0.0;
                }

      	     };
        	 
             if (keyEvent.getKeyCode() == KeyEvent.VK_2)
      	     {
                aVal = aVal + 0.05;
                if (aVal > 1.0)
                {
                	aVal = 1.0;
                }
     	     };
     	     
          	 double c = Math.cos(xVal)*l;
        	 double s = Math.sin(xVal)*l;
        	 
             water_level.setVertex(0,  d/2.0,  l/2.0*(1 - aVal) + (l/2.0 + s)*aVal, aVal*c);
             water_level.setVertex(1, -d/2.0,  l/2.0*(1 - aVal) + (l/2.0 + s)*aVal, aVal*c);
             water_level.setVertex(2, -d/2.0, -l/2.0*(1 - aVal) - (l/2.0 + s)*aVal, aVal*c);
             water_level.setVertex(3,  d/2.0, -l/2.0*(1 - aVal) - (l/2.0 + s)*aVal, aVal*c);

             if (aVal <= 0.01)
             {
                water_level.setTransparency(1.0);                
             }
             else
             {
                water_level.setTransparency(0.2);
             }
             
             water_level.update(water_level);
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
    
    public void updateTrough()
    {
    	double c = Math.cos(xVal)*l;
    	double s = Math.sin(xVal)*l;
    	
        trough.setVertex(0,  d/2.0,  l/2.0, 0.0);
        trough.setVertex(1, -d/2.0,  l/2.0, 0.0);
        trough.setVertex(2, -d/2.0, -l/2.0, 0.0);
        trough.setVertex(3,  d/2.0, -l/2.0, 0.0);
        
        trough.setVertex(4,  d/2.0,  l/2.0 + s, c);
        trough.setVertex(5, -d/2.0,  l/2.0 + s, c);
        trough.setVertex(6, -d/2.0, -l/2.0 - s, c);
        trough.setVertex(7,  d/2.0, -l/2.0 - s, c);
        trough.update(trough);  	

        water_level.setVertex(0,  d/2.0,  l/2.0*(1 - aVal) + (l/2.0 + s)*aVal, aVal*c);
        water_level.setVertex(1, -d/2.0,  l/2.0*(1 - aVal) + (l/2.0 + s)*aVal, aVal*c);
        water_level.setVertex(2, -d/2.0, -l/2.0*(1 - aVal) - (l/2.0 + s)*aVal, aVal*c);
        water_level.setVertex(3,  d/2.0, -l/2.0*(1 - aVal) - (l/2.0 + s)*aVal, aVal*c);
    	
        water_level.update(water_level);  	
        
    }
};
