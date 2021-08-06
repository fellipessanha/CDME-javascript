//////////////////////////////////////////////////////////////////////////////
// PCO class
//
// Last update: 27/07/2010
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

public class PCO extends Applet
{
   public double xVal = 3.0*Math.PI/4.0;   
   public double aVal = 1.0;   
   public double r = 7.0;
   public double l = r*(2.0*Math.PI - xVal);
   public double R = l/(2.0*Math.PI);
   public double rho = (1.0 - aVal)*r + aVal*R;
   public double theta = 2.0*Math.PI - l/rho;
   public double h = Math.sqrt(r*r - rho*rho);
   
   protected KeyListener internalKeyListener;   
   
   private JSObject window;
   private JSObject document;
   private boolean isLiveConnectOK = true;
   
   protected PvDisplay disp;
   protected PuFunction f;
   protected PgDomainDescr d;
   protected PgParmSurface cone;   

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
      
      // Cone builder
      cone = new PgParmSurface(3);
      cone.setGlobalElementColor(Color.YELLOW);
      cone.setGlobalEdgeColor(Color.YELLOW);
      // cone.setGlobalElementBackColor(Color.YELLOW);
      // cone.showElementBackColor(true);
      cone.showEdges(false);
      
      disp.showDepthcue(false);
      
      disp.setLightingModel(1);
      
      updateCone();
      
      disp.addGeometry(cone);
      
      // Key events
      internalKeyListener = new KeyAdapter() 
      {
          public void keyPressed(KeyEvent keyEvent) 
          {
             if (keyEvent.getKeyCode() == KeyEvent.VK_1)
       	     {
             	 aVal -= 0.05;
             	 if (aVal < 0.0)
             	 {
             		 aVal = 0.0;
             	 }
             	 updateCone();
      	     };
        	 
             if (keyEvent.getKeyCode() == KeyEvent.VK_2)
      	     {
            	 aVal += 0.05;
            	 if (aVal > 1.0)
            	 {
            		 aVal = 1.0;
            	 }
            	 updateCone();
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
    
    public void updateCone()
    {
        l = r*(2.0*Math.PI - xVal);
    	R = l/(2.0*Math.PI);
    	rho = (1.0 - aVal)*r + aVal*R;
    	theta = 2.0*Math.PI - l/rho;
    	h = Math.sqrt(r*r - rho*rho);

        f = new PuFunction(2, 3);
        f.setName("Functions of the Parametrized Surface");
        f.setExpression(0, "(1 - v)*(" + rho + ")*cos(u)");
        f.setExpression(1, "(1 - v)*(" + rho + ")*sin(u)");
        f.setExpression(2, "-v*(" + h + ") + (" + h + ")/2");  	
    	
    	d = new PgDomainDescr(2);
        d.setName("Domain of the Parametrized Surface");
        d.setMaxSize(theta/2.0, 0.0, 2.0*Math.PI - theta/2, 1.0);
        d.setSize(theta/2.0, 0.0, 2.0*Math.PI - theta/2, 1.0);
        d.setDiscr(150, 2);
        d.init();    	
                
        cone.setDomainDescr(d);
        cone.setFunctionExpr(f);
        cone.compute();        
        
        cone.update(cone);  
        
        cone.showBackface(true);
        
        // disp.fit();
        // disp.center();
    }
};
