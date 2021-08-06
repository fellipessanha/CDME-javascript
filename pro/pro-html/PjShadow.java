///////////////////////////////////////////////////////////////////////////////
// PjShadow class
//
// Last update: 23/01/2008
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
// Native JAVA imports
///////////////////////////////////////////////////////////////////////////////
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

///////////////////////////////////////////////////////////////////////////////
// JavaView imports
///////////////////////////////////////////////////////////////////////////////
import jv.geom.PgElementSet;
import jv.geom.PgPolygon;
import jv.geom.PgPointSet;
import jv.geom.PgPolygonSet;
import jv.loader.PgJvxLoader;
import jv.number.PuDouble;
import jv.object.PsDebug;
import jv.project.PgJvxSrc;
import jv.project.PgGeometryIf;
import jv.project.PjProject;
import jv.project.PvCameraIf;
import jv.project.PvDisplayIf;
import jv.project.PvPickListenerIf;
import jv.project.PvPickEvent;
import jv.vecmath.PdVector;
import jv.vecmath.PiVector;
import jvx.geom.PwCleanMesh;
import jvx.geom.PwTransform;
import jvx.loader.PgOffLoader;



///////////////////////////////////////////////////////////////////////////////
// Shadow applet
///////////////////////////////////////////////////////////////////////////////
public class PjShadow extends PjProject implements PvPickListenerIf {
	protected int idiom;
	protected URL codebase;
	protected URL fullpath;
	protected double initial_transparency;
	protected boolean enable_choice;

	protected PuDouble transparency;
	protected PuDouble perspective;

	protected int numberOfVertices;
	protected int numberOfEdges;
	protected int numberOfElements;

	protected String category;
	protected String solid;
	protected PgPointSet poly;
	protected PgPointSet copy;

	protected boolean showVertices = true;
	protected boolean showEdges = true;
	protected boolean showElements = true;

	protected PvDisplayIf disp;
    protected PvCameraIf cam;

	protected PgPointSet shadowXY;
	protected PgPointSet shadowXZ;
	protected PgPointSet shadowYZ;

	protected PgElementSet planeXY;
	protected PgElementSet planeXZ;
	protected PgElementSet planeYZ;
	protected double xMin;
	protected double xMax;
	protected double yMin;
	protected double yMax;
	protected double zMin;
	protected double zMax;
	protected double delta;
	protected double xO;
	protected double yO;
	protected double zO;

	protected PwTransform m_pwTransform;
	protected PuDouble rot1;
	protected PuDouble rot2;
	protected PuDouble rot3;

	protected PgPolygon orthogonalXY[];
	protected PgPolygon orthogonalXZ[];
	protected PgPolygon orthogonalYZ[];
	protected boolean showOrthogonalXY = true;
	protected boolean showOrthogonalXZ = true;
	protected boolean showOrthogonalYZ = true;
	protected PiVector markedVertices;
	protected long polyNumVertices = 0;
	
    protected PgPolygonSet axes1;
    protected double axes_extra_space = 10.0/100.0;
    
    protected boolean isPolygonSet = false;
    protected boolean isPointSet = false;
    
    protected KeyListener internalKeyListener;    
    

	//////////////////////////////////////////////////////////////////////////
	// Default constructor
	//////////////////////////////////////////////////////////////////////////
	public PjShadow(URL input_codebase, 
			        int input_idiom, 
			        String input_category,
			        String input_solid, 
			        double input_initial_transparency,
			        boolean input_enable_choice) 
	{
		super("Spatial Geometry Applet");

		codebase = input_codebase;
		idiom = input_idiom;
		category = input_category;
		solid = input_solid;
		initial_transparency = input_initial_transparency;
		enable_choice = input_enable_choice;

		if (getClass() == PjShadow.class) 
		{
			init();
		};
	}; // End of PjShadow()

	// ////////////////////////////////////////////////////////////////////////
	// Initialize variables
	// ////////////////////////////////////////////////////////////////////////
	public void init() 
	{
		poly = new PgElementSet(3);

		planeXY = new PgElementSet(3);
		planeXY.setNumElements(1);
		planeXY.setNumVertices(4);
		planeXZ = new PgElementSet(3);
		planeXZ.setNumElements(1);
		planeXZ.setNumVertices(4);
		planeYZ = new PgElementSet(3);
		planeYZ.setNumElements(1);
		planeYZ.setNumVertices(4);

		shadowXY = new PgElementSet(3);
		shadowXZ = new PgElementSet(3);
		shadowYZ = new PgElementSet(3);

		copy = new PgPointSet(3);

		// Transparency slider
		transparency = new PuDouble("Transparência:", this);
		transparency.setDefBounds(0.0, 1.0, 0.01, 0.05);
		transparency.setDefValue(initial_transparency);
		transparency.init();
		transparency.setEnabled(true);
		transparency.setEnabledConfigButton(false);
		
		// Perspective slider
		perspective = new PuDouble("Perspectiva:", this);
		perspective.setDefBounds(0.0, 1.5, 0.01, 0.05);
		perspective.setDefValue(0.5);
		perspective.init();
		perspective.setEnabled(true);		
		perspective.setEnabledConfigButton(false);

		// Rotation sliders
		rot1 = new PuDouble("Eixo 1:", this);
		rot1.setDefBounds(-360, 360, 1, 5);
		rot1.setDefValue(0);
		rot1.init();
		rot1.setEnabled(true);
		rot1.setEnabledConfigButton(false);

		rot2 = new PuDouble("Eixo 2:", this);
		rot2.setDefBounds(-360, 360, 1, 5);
		rot2.setDefValue(0);
		rot2.init();
		rot2.setEnabled(true);
		rot2.setEnabledConfigButton(false);

		rot3 = new PuDouble("Eixo 3:", this);
		rot3.setDefBounds(-360, 360, 1, 5);
		rot3.setDefValue(0);
		rot3.init();
		rot3.setEnabled(true);
		rot3.setEnabledConfigButton(false);
		
		// Axes
	    axes1 = new PgPolygonSet(3); // This is a 3D object
        axes1.setNumVertices(6);
        axes1.setNumPolygons(3);       
        axes1.getVertex(0).setName("");
        axes1.getVertex(1).setName("");
        axes1.getVertex(2).setName("");
        axes1.getVertex(3).setName("x");
        axes1.getVertex(4).setName("y");
        axes1.getVertex(5).setName("z");
        axes1.showVertexLabels(true);
        axes1.setPolygon(0, 0, 3);
        axes1.setPolygon(1, 1, 4);
        axes1.setPolygon(2, 2, 5);
        axes1.setGlobalPolygonColor(Color.BLUE);
        axes1.setGlobalVertexColor(Color.BLUE);
        axes1.setPolygonSize(0, 0.5);
        axes1.setPolygonSize(1, 0.5);
        axes1.setPolygonSize(2, 0.5);
        axes1.showPolygonSizes(true);
        axes1.showPolygonEndArrow(true);		
	}; // End of init()

	// ////////////////////////////////////////////////////////////////////////
	// Load geometry file
	// ////////////////////////////////////////////////////////////////////////
	   public void loadGeometryFromFile(Model object) 
	   {					
		String filename = object.getFileName();
		String filetype = object.getFileType();
		
		isPolygonSet = object.isPolygonSet();
		isPointSet = object.isPointSet();
		  
		String pathname = codebase.toExternalForm() + filename + "." + filetype;
		try 
		{
			fullpath = new URL(pathname);
			java.io.InputStream r = fullpath.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(r));
			if (in == null) 
			{
				PsDebug.warning("could not open file  " + pathname + "...");
				System.out.println("could not open file  " + pathname + "...");
			} 
			else 
			{
				if (polyNumVertices != 0) 
				{
					for (int i = 0; i < polyNumVertices; i++) 
					{
						if (markedVertices.getEntry(i) == 1) 
						{
							if (showOrthogonalXY == true) 
							{
								removeGeometry(orthogonalXY[i]);
							}
							if (showOrthogonalXZ == true) 
							{
								removeGeometry(orthogonalXZ[i]);
							}
							if (showOrthogonalYZ == true) 
							{
								removeGeometry(orthogonalYZ[i]);
							}
						}
					}
				}

				// Loading polyhedron
				PgJvxSrc[] geometries;
				if (filetype.equals("off")) 
				{
					PgOffLoader offloader = new PgOffLoader();
					offloader.load(in);
					geometries = offloader.getGeometries();
					if (isPolygonSet == false)
					{
					   poly = new PgElementSet(3);
					   poly.setJvx(geometries[0]);
					   copy = new PgElementSet(3);					   
					   shadowXY = new PgElementSet(3);
					   shadowXZ = new PgElementSet(3);
					   shadowYZ = new PgElementSet(3);					   
					}
					else
					{
					   poly = new PgPolygonSet(3);
					   poly.setJvx(geometries[0]);
					   copy = new PgPolygonSet(3);					   
					   shadowXY = new PgPolygonSet(3);
					   shadowXZ = new PgPolygonSet(3);
					   shadowYZ = new PgPolygonSet(3);					   
					}
				}
				if (filetype.equals("jvx")) 
				{
					PgJvxLoader jvxloader = new PgJvxLoader();
					jvxloader.load(in);
					geometries = jvxloader.getGeometries();
					if (isPolygonSet == false)
					{
					   poly = new PgElementSet(3);
					   poly.setJvx(geometries[0]);
					   copy = new PgElementSet(3);
					   shadowXY = new PgElementSet(3);
					   shadowXZ = new PgElementSet(3);
					   shadowYZ = new PgElementSet(3);
					}
					else
					{
					   poly = new PgPolygonSet(3);
					   poly.setJvx(geometries[0]);
					   copy = new PgPolygonSet(3);
					   shadowXY = new PgPolygonSet(3);
					   shadowXZ = new PgPolygonSet(3);
					   shadowYZ = new PgPolygonSet(3);
					}
				}

				// Configuring the polyhedron
				poly.setTransparency(transparency.getValue());
				poly.showVertices(showVertices);
				if (transparency.getValue() > 0.05) 
				{
					poly.showTransparency(true);
				} 
				else 
				{
					poly.showTransparency(false);
				}
				
				if (isPolygonSet == false)
				{
				   PgElementSet p = (PgElementSet) poly;
				   p.showElementColors(true);
				   p.showElements(showElements);
				   p.showEdges(showEdges);				
				   p.setGlobalEdgeColor(Color.BLACK);
				}
				else
				{					   
				   PgPolygonSet p = (PgPolygonSet) poly;
				   p.showPolygonSizes(true);
				   for (int i = 0; i < p.getNumPolygons(); i++)
				   {
					   p.setPolygonColor(i, Color.BLACK);
					   p.setPolygonSize(i, 0.05);
				   }
				}
				poly.showVertexOutline(true);
				poly.setGlobalVertexColor(Color.WHITE);
				poly.showVertexColors(true);								


				if (isPolygonSet == false)
				{			
				   PwCleanMesh.identifyVertices(poly, 1E-010D);					
				   PgElementSet p = (PgElementSet) poly;					
				   p.makeNeighbour();
			 	   p.close();
				}

				polyNumVertices = poly.getNumVertices();

				xMin = (poly.getBounds())[0].getEntry(0);
				yMin = (poly.getBounds())[0].getEntry(1);
				zMin = (poly.getBounds())[0].getEntry(2);
				xMax = (poly.getBounds())[1].getEntry(0);
				yMax = (poly.getBounds())[1].getEntry(1);
				zMax = (poly.getBounds())[1].getEntry(2);

				double deltaX = xMax - xMin;
				double deltaY = yMax - yMin;
				double deltaZ = zMax - zMin;

				delta = deltaZ;
				if (delta < deltaX) 
				{
					delta = deltaX;
				}
				if (delta < deltaY) 
				{
					delta = deltaY;
				}
				delta = 1.1 * Math.sqrt(3.0) * delta;
				
				if (delta == 0.0)
				{
					delta = 1.0;
				}

				xO = xMin - (delta - deltaX) / 2.0;
				yO = yMin - (delta - deltaY) / 2.0;
				zO = zMin - (delta - deltaZ) / 2.0;

				// Plane XY
				planeXY.setVertex(0, xO, yO, zO);
				planeXY.setVertex(1, xO, yO + delta, zO);
				planeXY.setVertex(2, xO + delta, yO + delta, zO);
				planeXY.setVertex(3, xO + delta, yO, zO);
				planeXY.setElement(0, 0, 1, 2, 3);
				planeXY.showElementColors(true);
				planeXY.showElements(true);
				planeXY.showEdges(true);
				planeXY.setGlobalElementColor(Color.LIGHT_GRAY);
				planeXY.setTransparency(0.7);
				planeXY.showTransparency(true);

				// Plane XZ
				planeXZ.setVertex(0, xO, yO, zO);
				planeXZ.setVertex(1, xO + delta, yO, zO);
				planeXZ.setVertex(2, xO + delta, yO, zO + delta);
				planeXZ.setVertex(3, xO, yO, zO + delta);
				planeXZ.setElement(0, 0, 1, 2, 3);
				planeXZ.showElementColors(true);
				planeXZ.showElements(true);
				planeXZ.showEdges(true);
				planeXZ.setGlobalElementColor(Color.LIGHT_GRAY);
				planeXZ.setTransparency(0.7);
				planeXZ.showTransparency(true);

				// Plane YZ
				planeYZ.setVertex(0, xO, yO, zO);
				planeYZ.setVertex(1, xO, yO + delta, zO);
				planeYZ.setVertex(2, xO, yO + delta, zO + delta);
				planeYZ.setVertex(3, xO, yO, zO + delta);
				planeYZ.setElement(0, 0, 1, 2, 3);
				planeYZ.showElementColors(true);
				planeYZ.showElements(true);
				planeYZ.showEdges(true);
				planeYZ.setGlobalElementColor(Color.LIGHT_GRAY);
				planeYZ.setTransparency(0.7);
				planeYZ.showTransparency(true);
				
				// Axes
			    axes1.setVertex(0, new PdVector(xO, yO, zO));
			    axes1.setVertex(1, new PdVector(xO, yO, zO));
			    axes1.setVertex(2, new PdVector(xO, yO, zO));
			    axes1.setVertex(3, new PdVector(xO + (1.0 + axes_extra_space)*delta, yO, zO));
			    axes1.setVertex(4, new PdVector(xO, yO + (1.0 + axes_extra_space)*delta, zO));
			    axes1.setVertex(5, new PdVector(xO, yO, zO + (1.0 + axes_extra_space)*delta));
		        axes1.getVertex(0).setName("");
		        axes1.getVertex(1).setName("");
		        axes1.getVertex(2).setName("");
		        axes1.getVertex(3).setName("x");
		        axes1.getVertex(4).setName("y");
		        axes1.getVertex(5).setName("z");
                axes1.showVertexLabels(true);
                axes1.setPolygon(0, 0, 3);
                axes1.setPolygon(1, 1, 4);
                axes1.setPolygon(2, 2, 5);
                axes1.setGlobalPolygonColor(Color.BLUE);
                axes1.setGlobalVertexColor(Color.BLUE);
                axes1.setGlobalVertexSize(0.0);
                axes1.setPolygonSize(0, 0.5);
                axes1.setPolygonSize(1, 0.5);
                axes1.setPolygonSize(2, 0.5);
                axes1.showPolygonSizes(true);
                axes1.showPolygonEndArrow(true);
                axes1.update(axes1);				
				
				// Shadows
				shadowXY.copy(poly);
				
				if (isPolygonSet == false)
				{				
				   PgElementSet p = (PgElementSet) shadowXY;									
				   p.showElements(false);
				}
				
				shadowXY.showVertices(false);
				shadowXZ.copy(poly);
				
				if (isPolygonSet == false)
				{				
				   PgElementSet p = (PgElementSet) shadowXZ;									
				   p.showElements(false);
				}
				
				shadowXZ.showVertices(false);
				shadowYZ.copy(poly);
				
				if (isPolygonSet == false)
				{				
				   PgElementSet p = (PgElementSet) shadowYZ;									
				   p.showElements(false);
				}
								
				shadowYZ.showVertices(false);
				
				for (int i = 0; i < poly.getNumVertices(); i++) 
				{
					double xOld = (poly.getVertices())[i].getEntry(0);
					double yOld = (poly.getVertices())[i].getEntry(1);
					double zOld = (poly.getVertices())[i].getEntry(2);

					shadowXY.setVertex(i, new PdVector(xOld, yOld, zO));
					shadowXZ.setVertex(i, new PdVector(xOld, yO, zOld));
					shadowYZ.setVertex(i, new PdVector(xO, yOld, zOld));
				};

				// if ((isPolygonSet == true) || (isPointSet == true))
				{				
				   if (object.isShowingVertices() == true)
				   {
                      shadowXY.showVertices(true);
                      shadowXZ.showVertices(true);
                      shadowYZ.showVertices(true);
				   }
				   else
				   {
	                  shadowXY.showVertices(false);
	                  shadowXZ.showVertices(false);
	                  shadowYZ.showVertices(false);					   
				   }
				};
				
				if (isPolygonSet == true)
				{
				   PgPolygonSet p = (PgPolygonSet) shadowXY;
				   p.showPolygonSizes(true);
				   for (int i = 0; i < p.getNumPolygons(); i++)
				   {
					   p.setPolygonColor(i, Color.BLACK);
					   p.setPolygonSize(i, 0.05);
				   }
				}
				
				if (isPolygonSet == true)
				{
				   PgPolygonSet p = (PgPolygonSet) shadowXZ;
				   p.showPolygonSizes(true);
				   for (int i = 0; i < p.getNumPolygons(); i++)
				   {
					   p.setPolygonColor(i, Color.BLACK);
					   p.setPolygonSize(i, 0.05);
				   }
				}
				
				if (isPolygonSet == true)
				{
				   PgPolygonSet p = (PgPolygonSet) shadowYZ;
				   p.showPolygonSizes(true);
				   for (int i = 0; i < p.getNumPolygons(); i++)
				   {
					   p.setPolygonColor(i, Color.BLACK);
					   p.setPolygonSize(i, 0.05);
				   }
				}
				
				if (disp != null)
				{
					disp.removeGeometries();
					disp.addGeometry(poly);
					if (showOrthogonalXY == true)
					{
					   disp.addGeometry(shadowXY);
					}
					if (showOrthogonalXZ == true)
					{					
					   disp.addGeometry(shadowXZ);
					}
					if (showOrthogonalYZ == true)
					{
					   disp.addGeometry(shadowYZ);
					}
					disp.addGeometry(planeXY);
					disp.addGeometry(planeXZ);
					disp.addGeometry(planeYZ);
					disp.addGeometry(axes1);					
				}

				poly.update(poly); // Don't forget to update!
				
				planeXY.update(planeXY);
				planeXZ.update(planeXZ);
				planeYZ.update(planeYZ);
				if (showOrthogonalXY == true)
				{
				   shadowXY.update(shadowXY);
				}
				if (showOrthogonalXZ == true)
				{
				   shadowXZ.update(shadowXZ);
				}
				if (showOrthogonalYZ == true)
				{
				   shadowYZ.update(shadowYZ);
				}
				

				// Update sliders
				rot1.setValue(0.0);
				rot2.setValue(0.0);
				rot3.setValue(0.0);

				
				// Copy of poly
				copy.copy(poly);

				// Orthogonal segments
				orthogonalXY = new PgPolygon[poly.getNumVertices()];
				orthogonalXZ = new PgPolygon[poly.getNumVertices()];
				orthogonalYZ = new PgPolygon[poly.getNumVertices()];

				for (int i = 0; i < poly.getNumVertices(); i++) 
				{
					orthogonalXY[i] = new PgPolygon(3);
					orthogonalXY[i].setNumVertices(2);
					orthogonalXY[i].setGlobalPolygonColor(Color.ORANGE);
					orthogonalXY[i].showVertices(false);

					orthogonalXZ[i] = new PgPolygon(3);
					orthogonalXZ[i].setNumVertices(2);
					orthogonalXZ[i].setGlobalPolygonColor(Color.ORANGE);
					orthogonalXZ[i].showVertices(false);

					orthogonalYZ[i] = new PgPolygon(3);
					orthogonalYZ[i].setNumVertices(2);
					orthogonalYZ[i].setGlobalPolygonColor(Color.ORANGE);
					orthogonalYZ[i].showVertices(false);

					double xOld = (poly.getVertices())[i].getEntry(0);
					double yOld = (poly.getVertices())[i].getEntry(1);
					double zOld = (poly.getVertices())[i].getEntry(2);

					orthogonalXY[i].setVertex(0, (poly.getVertices())[i]);
					orthogonalXY[i].setVertex(1, new PdVector(xOld, yOld, zO));
					orthogonalXY[i].update(orthogonalXY[i]);

					orthogonalXZ[i].setVertex(0, (poly.getVertices())[i]);
					orthogonalXZ[i].setVertex(1, new PdVector(xOld, yO, zOld));
					orthogonalXZ[i].update(orthogonalXZ[i]);

					orthogonalYZ[i].setVertex(0, (poly.getVertices())[i]);
					orthogonalYZ[i].setVertex(1, new PdVector(xO, yOld, zOld));
					orthogonalYZ[i].update(orthogonalYZ[i]);
				};

				markedVertices = new PiVector(poly.getNumVertices());
				for (int i = 0; i < poly.getNumVertices(); i++) 
				{
					markedVertices.setEntry(i, 0);
				}
			}
		} 
		catch (MalformedURLException exp) 
		{
			System.out.println(exp.toString());
		} 
		catch (IOException exp) 
		{
			System.out.println(exp.toString());
		}
	}; // End of loadOffFile()

	// ////////////////////////////////////////////////////////////////////////
	// Update shadows
	// ////////////////////////////////////////////////////////////////////////
	protected void updateShadows() 
	{
		for (int i = 0; i < poly.getNumVertices(); i++) 
		{
			double xOld = (poly.getVertices())[i].getEntry(0);
			double yOld = (poly.getVertices())[i].getEntry(1);
			double zOld = (poly.getVertices())[i].getEntry(2);

			shadowXY.setVertex(i, new PdVector(xOld, yOld, zO));
			shadowXZ.setVertex(i, new PdVector(xOld, yO, zOld));
			shadowYZ.setVertex(i, new PdVector(xO, yOld, zOld));

			orthogonalXY[i].setVertex(0, (poly.getVertices())[i]);
			orthogonalXY[i].setVertex(1, new PdVector(xOld, yOld, zO));
			orthogonalXY[i].update(orthogonalXY[i]);

			orthogonalXZ[i].setVertex(0, (poly.getVertices())[i]);
			orthogonalXZ[i].setVertex(1, new PdVector(xOld, yO, zOld));
			orthogonalXZ[i].update(orthogonalXZ[i]);

			orthogonalYZ[i].setVertex(0, (poly.getVertices())[i]);
			orthogonalYZ[i].setVertex(1, new PdVector(xO, yOld, zOld));
			orthogonalYZ[i].update(orthogonalYZ[i]);
		};
		shadowXY.update(shadowXY);
		shadowXZ.update(shadowXZ);
		shadowYZ.update(shadowYZ);
	}; // End of updateShadows()

	// ////////////////////////////////////////////////////////////////////////
	// Things to do when the class is started
	// ////////////////////////////////////////////////////////////////////////
	public void start() 
	{
		super.start();

		// Necessary to apply PwWorkshop operations
		if (isPolygonSet == false)
		{				
		   PgElementSet p = (PgElementSet) poly;									
  		   PwCleanMesh.identifyVertices(p, 1E-010D);
		   p.makeNeighbour();
		}
		

		addGeometry(poly);	
		addGeometry(planeXY);
		addGeometry(planeXZ);
		addGeometry(planeYZ);
		addGeometry(axes1);
		addGeometry(shadowXY);
		addGeometry(shadowXZ);
		addGeometry(shadowYZ);
		selectGeometry(poly);

		update(this);

		disp = getDisplay(); // The PwUnfold needs a reference to the
		                     // display in order to work properly.

		PdVector d1 = new PdVector(1, 1, 1);
		PdVector d2 = new PdVector(3);
		d2.copy(d1);
		d2.multScalar(-1);
		cam = disp.getCamera();
		cam.setPosition(d1);
		cam.setViewDir(d2);
		cam.setRoll(0);
		cam.update(cam);
		disp.fit();		
		
        // Key events
        internalKeyListener = new KeyAdapter() 
        {
            public void keyPressed(KeyEvent keyEvent) 
            {
          	   if (keyEvent.getKeyCode() == KeyEvent.VK_1)
        	   {        	  
	              if (poly.isShowingVertexLabels() == true)
	        	  {
	        	     poly.showVertexLabels(false);
	        	  }
	        	  else
	        	  {
	         	     poly.showVertexLabels(true);
	        	  }
	              poly.update(poly);
	              
	              if (copy.isShowingVertexLabels() == true)
	        	  {
	        	     copy.showVertexLabels(false);
	        	  }
	        	  else
	        	  {
	         	     copy.showVertexLabels(true);
	        	  }
	              copy.update(copy);	              
        	  };
        	  
            }
        };
        disp.addKeyListener(internalKeyListener);		
	}; // End of start()

	// ///////////////////////////////////////////////////////////////////////
	// Update the class whenever a child has changed. Method is usually
	// invoked from the children.
	// ///////////////////////////////////////////////////////////////////////
	public boolean update(Object event) 
	{
		if (event == null)
		{
			return (true);
		};

		// Solid transparency
		if (event == transparency) 
		{
			poly.setTransparency(transparency.getValue());
			copy.setTransparency(transparency.getValue());

			if (transparency.getValue() > 0.05)
			{
				poly.showTransparency(true);
				copy.showTransparency(true);
			} 
			else 
			{
				poly.showTransparency(false);
				copy.showTransparency(false);
			}

			poly.update(poly);

			return (true);
		};
		
		// Perspective
		if (event == perspective) 
		{
            cam.setFieldOfView(perspective.getValue());
            cam.update(cam);
			return (true);
		};
		

		// Rotation
		if (event == rot1 || event == rot2 || event == rot3) 
		{
			double angle1 = rot1.getValue() * Math.PI / 180.0;
			double angle2 = rot2.getValue() * Math.PI / 180.0;
			double angle3 = rot3.getValue() * Math.PI / 180.0;

			poly.copy(copy);

			PwTransform.rotate(poly, angle1, 0);
			PwTransform.rotate(poly, angle2, 1);
			PwTransform.rotate(poly, angle3, 2);

			poly.update(poly);
			updateShadows();

			return (true);
		};

		return (super.update(event));
	}; // End of update()

	// ///////////////////////////////////////////////////////////////////////
	// showOrthogonalSegmentsXY()
	// ///////////////////////////////////////////////////////////////////////
	protected void showOrthogonalSegmentsXY(boolean b) 
	{
		showOrthogonalXY = b;
		for (int i = 0; i < poly.getNumVertices(); i++) 
		{
			if (b == true) {
				if (markedVertices.getEntry(i) == 1) 
				{
					addGeometry(orthogonalXY[i]);
				}
			} 
			else 
			{
				if (markedVertices.getEntry(i) == 1) 
				{
					removeGeometry(orthogonalXY[i]);
				}
			}
		}
	}; // End of showOrthogonalSegmentsXY()

	// ///////////////////////////////////////////////////////////////////////
	// showOrthogonalSegmentsXZ()
	// ///////////////////////////////////////////////////////////////////////
	protected void showOrthogonalSegmentsXZ(boolean b) 
	{
		showOrthogonalXZ = b;
		for (int i = 0; i < poly.getNumVertices(); i++) 
		{
			if (b == true) 
			{
				if (markedVertices.getEntry(i) == 1) 
				{
					addGeometry(orthogonalXZ[i]);
				}
			} 
			else 
			{
				if (markedVertices.getEntry(i) == 1) 
				{
					removeGeometry(orthogonalXZ[i]);
				}
			}

		}
	}; // End of showOrthogonalSegmentsXZ()

	// ///////////////////////////////////////////////////////////////////////
	// showOrthogonalSegmentsYZ()
	// ///////////////////////////////////////////////////////////////////////
	protected void showOrthogonalSegmentsYZ(boolean b) 
	{
		showOrthogonalYZ = b;
		for (int i = 0; i < poly.getNumVertices(); i++) 
		{
			if (b == true) 
			{
				if (markedVertices.getEntry(i) == 1) 
				{
					addGeometry(orthogonalYZ[i]);
				}
			} 
			else 
			{
				if (markedVertices.getEntry(i) == 1) 
				{
					removeGeometry(orthogonalYZ[i]);
				}
			}
		}
	}; // End of showOrthogonalSegmentsYZ()

	// ///////////////////////////////////////////////////////////////////////
	// showAllOrthogonalSegments()
	// ///////////////////////////////////////////////////////////////////////
	protected void showAllOrthogonalSegments() 
	{
		for (int i = 0; i < poly.getNumVertices(); i++) 
		{
			if (markedVertices.getEntry(i) == 0) 
			{
				markedVertices.setEntry(i, 1);
				if (showOrthogonalXY == true) 
				{
					addGeometry(orthogonalXY[i]);
				};
				if (showOrthogonalXZ == true) 
				{
					addGeometry(orthogonalXZ[i]);
				};
				if (showOrthogonalYZ == true) 
				{
					addGeometry(orthogonalYZ[i]);
				};
			}
		}
	}; // End of showAllOrthogonalSegments()

	// ///////////////////////////////////////////////////////////////////////
	// hideAllOrthogonalSegments()
	// ///////////////////////////////////////////////////////////////////////
	protected void hideAllOrthogonalSegments() 
	{
		for (int i = 0; i < poly.getNumVertices(); i++) 
		{
			if (markedVertices.getEntry(i) == 1) 
			{
				markedVertices.setEntry(i, 0);
				if (showOrthogonalXY == true) 
				{
					removeGeometry(orthogonalXY[i]);
				};
				if (showOrthogonalXZ == true) 
				{
					removeGeometry(orthogonalXZ[i]);
				};
				if (showOrthogonalYZ == true) 
				{
					removeGeometry(orthogonalYZ[i]);
				};
			}
		}
	}; // End of hideAllOrthogonalSegments()

	/***************************************************************************
	 * ************************************************************************** *
	 * Begin of PvPickListenerIf Implementation
	 * **************************************************************************
	 **************************************************************************/

	// ////////////////////////////////////////////////////////////////////////
	// The name of a listeners allows the display to issue verbal debug
	// messages.
	// ////////////////////////////////////////////////////////////////////////
	public String getName() 
	{
		return ("PjShadow");
	}; // End of getName()

	// ////////////////////////////////////////////////////////////////////////
	// Get a location in the display with 2d display and 3d world coordinates.
	// Method is called when display is in mode PvDisplayIf.MODE_DISPLAY_PICK.
	// ////////////////////////////////////////////////////////////////////////
	public void pickDisplay(PvPickEvent pos) 
	{
		// PsDebug.message("pickDisplay() called ...");
		// System.out.println("pickDisplay() called ...");
	}; // End of pickDisplay()

	// ////////////////////////////////////////////////////////////////////////
	// Drag a location in the display with 2d display and 3d world coordinates.
	// Method is called when display is in mode PvDisplayIf.MODE_DISPLAY_PICK.
	// ////////////////////////////////////////////////////////////////////////
	public void dragDisplay(PvPickEvent pos) 
	{
		// PsDebug.message("pickDisplay() called ...");
		// System.out.println("pickDisplay() called ...");
	}; // End of dragDisplay()

	// ////////////////////////////////////////////////////////////////////////
	// Pick an arbitrary point on a geometry, point may lie inside an element.
	// Method is called when display is in mode PvDisplayIf.MODE_INITIAL_PICK
	// or if temporarily the i-key is pressed, and any pixel in the display is
	// picked.
	// ////////////////////////////////////////////////////////////////////////
	public void pickInitial(PvPickEvent pos) 
	{
		// PsDebug.message("pickInitial() called ...");
		// System.out.println("pickInitial() called ...");
	}; // End of pickInitial()

	// ////////////////////////////////////////////////////////////////////////
	// Drag an arbitrary point along a geometry, point may lie inside an
	// element. Method is called when display is in mode
	// PvDisplayIf.MODE_INITIAL_PICK or if temporarily the i-key is pressed,
	// and any pixel in the display is dragged.
	// ////////////////////////////////////////////////////////////////////////
	public void dragInitial(PvPickEvent pos) 
	{
		// PsDebug.message("dragInitial() called ...");
		// System.out.println("dragInitial() called ...");
	}; // End of dragInitial()

	// ////////////////////////////////////////////////////////////////////////
	// Get a picked vertex of a geometry.
	// Method is called when display is in mode PvDisplayIf.MODE_PICK
	// or if temporarily the p-key is pressed, and a vertex picked.
	// ////////////////////////////////////////////////////////////////////////
	public void pickVertex(PgGeometryIf geom, int index, PdVector vertex) 
	{
		// PsDebug.message("pickVertex() called ...");
		// System.out.println("pickVertex() called ...");
		if (markedVertices.getEntry(index) == 0) 
		{
			markedVertices.setEntry(index, 1);
			if (showOrthogonalXY == true) 
			{
				addGeometry(orthogonalXY[index]);
			}
			if (showOrthogonalXZ == true) 
			{
				addGeometry(orthogonalXZ[index]);
			}
			if (showOrthogonalYZ == true) 
			{
				addGeometry(orthogonalYZ[index]);
			}
		} 
		else 
		{
			markedVertices.setEntry(index, 0);
			if (showOrthogonalXY == true) 
			{
				removeGeometry(orthogonalXY[index]);
			}
			if (showOrthogonalXZ == true) 
			{
				removeGeometry(orthogonalXZ[index]);
			}
			if (showOrthogonalYZ == true) 
			{
				removeGeometry(orthogonalYZ[index]);
			}
		}
	}; // End of dragInitial();

	// ////////////////////////////////////////////////////////////////////////
	// Method is called when display is in mode PvDisplayIf.MODE_PICK
	// or if temporarily the p-key is pressed, and a vertex dragged.
	// ////////////////////////////////////////////////////////////////////////
	public void dragVertex(PgGeometryIf a_geom, int index, PdVector vertex) 
	{
		// PsDebug.message("dragVertex() called ...");
		// System.out.println("dragVertex() called ...");
		a_geom.setVertex(index, copy.getVertex(index));
	}; // End of dragVertex()

	// ////////////////////////////////////////////////////////////////////////
	// Mark a set of vertices of a geometry within a given bounding box.
	// Method is called when display is in mode PvDisplayIf.MODE_MARK
	// or if temporarily the m-key is pressed, and a rectangle is drawn.
	// ////////////////////////////////////////////////////////////////////////
	public void markVertices(PvPickEvent markBox) 
	{
		// PsDebug.message("markVertices() called ...");
		// System.out.println("markVertices() called ...");
	}; // End of markVertices()

	// ////////////////////////////////////////////////////////////////////////
	// Unmark a set of vertices of a geometry within a given bounding box.
	// Method is called when display is in mode PvDisplayIf.MODE_UNMARK
	// or if temporarily the u-key is pressed, and a rectangle is drawn.
	// ////////////////////////////////////////////////////////////////////////
	public void unmarkVertices(PvPickEvent markBox) 
	{
		// PsDebug.message("unmarkVertices() called ...");
		// System.out.println("unmarkVertices() called ...");
	}; // End of unmarkVertices()

	/***************************************************************************
	 * ************************************************************************** *
	 * Begin of PvPickListenerIf Implementation
	 * **************************************************************************
	 **************************************************************************/
}; // End of PjShadow classs
