//////////////////////////////////////////////////////////////////////////////
// ECI class
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

public class ECI extends Applet
{
   public double xVal = 1;   // Radius of the cylinder's base
   public double R = 3.0;    // Radius of the sphere
   public double hd2 = Math.sqrt(R*R - xVal*xVal);
   
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
   
   protected PvDisplay disp;
   protected PuFunction f_t;
   protected PuFunction f_l;
   protected PuFunction f_b;
   protected PuFunction f_s;
   protected PgDomainDescr d_t;
   protected PgDomainDescr d_l;
   protected PgDomainDescr d_b;
   protected PgDomainDescr d_s;
   protected PgParmSurface cylinder_t; // Top
   protected PgParmSurface cylinder_l; // Lateral surface   
   protected PgParmSurface cylinder_b; // Bottom
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
      
      // Cylinder builder
      cylinder_t = new PgParmSurface(3);
      cylinder_t.setGlobalElementColor(Color.YELLOW);
      cylinder_t.setGlobalEdgeColor(Color.YELLOW);
      // cylinder_t.setGlobalElementBackColor(Color.YELLOW);
      // cylinder_t.showElementBackColor(true);
      cylinder_t.showEdges(false);
      
      cylinder_l = new PgParmSurface(3);
      cylinder_l.setGlobalElementColor(Color.YELLOW);
      cylinder_l.setGlobalEdgeColor(Color.YELLOW);
      // cylinder_l.setGlobalElementBackColor(Color.YELLOW);
      // cylinder_l.showElementBackColor(true);
      cylinder_l.showEdges(false);
      
      cylinder_b = new PgParmSurface(3);
      cylinder_b.setGlobalElementColor(Color.YELLOW);
      cylinder_b.setGlobalEdgeColor(Color.YELLOW);
      // cylinder_b.setGlobalElementBackColor(Color.YELLOW);
      // cylinder_b.showElementBackColor(true);
      cylinder_b.showEdges(false);      

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
      
      updateCylinder();

      disp.addGeometry(sphere);
      disp.addGeometry(cylinder_t);
      disp.addGeometry(cylinder_l);
      disp.addGeometry(cylinder_b);      
      
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
    
    public void updateCylinder()
    {
    	double r = xVal;
    	hd2 = Math.sqrt(R*R - xVal*xVal);    	

    	// Top
        f_t = new PuFunction(2, 3);
        f_t.setName("Top Function");
        f_t.setExpression(0, "v*(" + r + ")*cos(u)");
        f_t.setExpression(1, "v*(" + r + ")*sin(u)");
        f_t.setExpression(2, "" + hd2);  	
    	
    	d_t = new PgDomainDescr(2);
        d_t.setName("Top Domain");
        d_t.setMaxSize(0.0, 0.0, 2.0*Math.PI, 1.0);
        d_t.setSize(0.0, 0.0, 2.0*Math.PI, 1.0);
        d_t.setDiscr(150, 2);
        d_t.init();    	
                
        cylinder_t.setDomainDescr(d_t);
        cylinder_t.setFunctionExpr(f_t);
        cylinder_t.compute();                
        cylinder_t.update(cylinder_t);          
        cylinder_t.showBackface(true);
        
        // Lateral
        f_l = new PuFunction(2, 3);
        f_l.setName("Lateral Function");
        f_l.setExpression(0, "(" + r + ")*cos(u)");
        f_l.setExpression(1, "(" + r + ")*sin(u)");
        f_l.setExpression(2, "v");  	
    	
    	d_l = new PgDomainDescr(2);
        d_l.setName("Lateral Domain");
        d_l.setMaxSize(0.0, -hd2, 2.0*Math.PI, hd2);
        d_l.setSize(0.0, -hd2, 2.0*Math.PI, hd2);
        d_l.setDiscr(150, 2);
        d_l.init();    	
                
        cylinder_l.setDomainDescr(d_l);
        cylinder_l.setFunctionExpr(f_l);
        cylinder_l.compute();                
        cylinder_l.update(cylinder_l);          
        cylinder_l.showBackface(true);
        
        // Bottom
        f_b = new PuFunction(2, 3);
        f_b.setName("Bottom Function");
        f_b.setExpression(0, "v*(" + r + ")*cos(u)");
        f_b.setExpression(1, "v*(" + r + ")*sin(u)");
        f_b.setExpression(2, "-(" + hd2 + ")");  	
    	
    	d_b = new PgDomainDescr(2);
        d_b.setName("Bottom Domain");
        d_b.setMaxSize(0.0, 0.0, 2.0*Math.PI, 1.0);
        d_b.setSize(0.0, 0.0, 2.0*Math.PI, 1.0);
        d_b.setDiscr(150, 2);
        d_b.init();    	
                
        cylinder_b.setDomainDescr(d_b);
        cylinder_b.setFunctionExpr(f_b);
        cylinder_b.compute();                
        cylinder_b.update(cylinder_b);          
        cylinder_b.showBackface(true); 
                
        // disp.fit();
        // disp.center();
    }
};
