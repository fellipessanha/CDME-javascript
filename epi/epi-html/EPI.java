//////////////////////////////////////////////////////////////////////////////
// EC0 class
//
// Last update: 12/02/2011
//////////////////////////////////////////////////////////////////////////////

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jv.function.PuFunction;
import jv.geom.PgElementSet;
import jv.viewer.PvDisplay;
import jv.viewer.PvViewer;

import jvx.surface.PgDomainDescr;
import jvx.surface.PgParmSurface;

import netscape.javascript.JSObject;
import netscape.javascript.JSException;

public class EPI extends Applet
{
   public double xVal = 4.0;   // Radius of the cone's base
   public double R = 3.0;    // Radius of the sphere
   
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
   
   protected PvDisplay disp;
   protected PgElementSet pyramid;   
   protected PuFunction f_s;
   protected PgDomainDescr d_s;
   protected PgParmSurface sphere;
   protected double transparency = 0.94;
     

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

      // Bounding cone
      disp.showBndBox(false);

      // Background color
      disp.setBackgroundColor(Color.WHITE);

      // Applet layout, quite simple now
      setLayout(new BorderLayout());
      add(disp, BorderLayout.CENTER);
      
      // Pyramid builder
      pyramid = new PgElementSet(3);
      pyramid.setNumVertices(5);
      pyramid.setNumElements(5);
      
      pyramid.setElement(0, 1, 4, 3, 2);
      pyramid.setElement(1, 0, 1, 2);
      pyramid.setElement(2, 0, 3, 2);
      pyramid.setElement(3, 0, 3, 4);
      pyramid.setElement(4, 0, 4, 1); 
      pyramid.setGlobalElementColor(Color.YELLOW);
      pyramid.setGlobalEdgeColor(Color.RED);

      sphere = new PgParmSurface(3);
      sphere.setGlobalElementColor(Color.RED);
      sphere.setGlobalEdgeColor(Color.RED);
      // sphere.setGlobalElementBackColor(Color.RED);
      // sphere.showElementBackColor(true);
      sphere.showEdges(false);
      
      f_s = new PuFunction(2, 3);
      f_s.setName("Sphere Function");
      double S = R + 0.05;
      f_s.setExpression(0, "(" + S + ")*cos(v)*cos(u)");
      f_s.setExpression(1, "(" + S + ")*cos(v)*sin(u)");
      f_s.setExpression(2, "(" + S + ")*sin(v)");  	
  	      
  	  d_s = new PgDomainDescr(2);
      d_s.setName("Sphere Domain");
      d_s.setMaxSize(-Math.PI, -Math.PI/2, Math.PI, Math.PI/2);
      d_s.setSize(-Math.PI, -Math.PI/2, Math.PI, Math.PI/2);
      d_s.setDiscr(60, 50);
      d_s.init();    	
              
      sphere.setDomainDescr(d_s);
      sphere.setFunctionExpr(f_s);
      sphere.compute();                
      sphere.update(sphere);          
      sphere.showBackface(true);
      sphere.showTransparency(true);
      sphere.setTransparency(transparency);
      
      disp.showDepthcue(false);
      
      disp.setLightingModel(1);
      
      updatePyramid();

      disp.addGeometry(sphere);
      disp.addGeometry(pyramid);     
      
      // Key events
      internalKeyListener = new KeyAdapter() 
      {
          public void keyPressed(KeyEvent keyEvent) 
          { 	  
             if (keyEvent.getKeyCode() == KeyEvent.VK_1)
       	     {
                transparency = transparency + 0.02;
                if (transparency > 1.0)
                {
                	transparency = 1.0;
                }
      	     };
        	 
             if (keyEvent.getKeyCode() == KeyEvent.VK_2)
      	     {
            	transparency = transparency - 0.02;
            	if (transparency < 0.0)
            	{
            		transparency = 0.0;
            	}
     	     };     	          	   
     	     
     	     sphere.setTransparency(transparency);
     	     sphere.update(sphere);
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
    	double r = Math.sqrt(xVal*(2.0*R - xVal));
    	double zVal = R - xVal;
    	double aVal = Math.sqrt(2.0)*r/2.0; 

    	pyramid.setVertex(0, 0, 0, R);
    	pyramid.setVertex(1,  aVal,  aVal, zVal);
    	pyramid.setVertex(2, -aVal,  aVal, zVal);
    	pyramid.setVertex(3, -aVal, -aVal, zVal);
    	pyramid.setVertex(4,  aVal, -aVal, zVal);
    	
    	pyramid.update(pyramid);
        
                
        // disp.fit();
        // disp.center();
    }
};
