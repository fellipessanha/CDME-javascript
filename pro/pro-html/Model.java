///////////////////////////////////////////////////////////////////////////////
// Model class
//
// Last update: 28/01/2008
///////////////////////////////////////////////////////////////////////////////

public class Model 
{
   protected String[] category;
   protected String[] name;
   protected String   filetype;
   protected String   filename;
   protected boolean  showVertices;
   protected boolean  showEdges;
   protected boolean  showElements;
   protected boolean  enableDual;
   protected boolean  enableTruncate;
   protected boolean  improveUnfolding = false;
   protected int      unfoldingTrials = 0;
   protected int      numberOfVertices = 0;
   protected int      numberOfEdges = 0;
   protected int      numberOfElements = 0;
   protected int      eulerCharacteristic = 0;
   protected boolean  isPolygonSet = false;        // Object is a polygon?
   protected boolean  isPointSet = false;          // Object is a point set?   

   public Model(String[] input_category,
                String[] input_name,
                String   input_filetype,
                String   input_filename,
                int input_numberOfVertices,
                int input_numberOfEdges,
                int input_numberOfElements,
		boolean  input_showVertices,
		boolean  input_showEdges,
		boolean  input_showElements,
		boolean  input_enableDual,
		boolean  input_enableTruncate)
   {
      category = input_category;
      name = input_name;
      filetype = input_filetype;
      filename = input_filename;
      showVertices = input_showVertices;
      showEdges = input_showEdges;
      showElements = input_showElements;
      numberOfVertices = input_numberOfVertices;
      numberOfEdges = input_numberOfEdges;
      numberOfElements = input_numberOfElements;
      eulerCharacteristic = numberOfVertices - numberOfEdges + numberOfElements;
      enableDual = input_enableDual;
      enableTruncate = input_enableTruncate;
      
      isPolygonSet = false;
      isPointSet = false;      
   };

   public Model(String[] input_category,
                String[] input_name,
                String   input_filetype,
                String   input_filename,
		boolean  input_showVertices,
		boolean  input_showEdges,
		boolean  input_showElements,
		boolean  input_enableDual,
		boolean  input_enableTruncate)
   {
      category = input_category;
      name = input_name;
      filetype = input_filetype;
      filename = input_filename;
      showVertices = input_showVertices;
      showEdges = input_showEdges;
      showElements = input_showElements;
      numberOfVertices = -1;
      numberOfEdges = -1;
      numberOfElements = -1;
      eulerCharacteristic = numberOfVertices - numberOfEdges + numberOfElements;
      enableDual = input_enableDual;
      enableTruncate = input_enableTruncate;
     
      isPolygonSet = false;
      isPointSet = false;      
   };


   public Model(String[] input_category,
                String[] input_name,
                String   input_filetype,
                String   input_filename,
	            boolean  input_showVertices,
 	            boolean  input_showEdges,
	            boolean  input_showElements,
	            boolean  input_enableDual,
	            boolean  input_enableTruncate,
	            boolean  input_isPolygonSet)
	{
	    category = input_category;
	    name = input_name;
	    filetype = input_filetype;
	    filename = input_filename;
	    showVertices = input_showVertices;
	    showEdges = input_showEdges;
	    showElements = input_showElements;
	    numberOfVertices = -1;
	    numberOfEdges = -1;
	    numberOfElements = -1;
	    eulerCharacteristic = numberOfVertices - numberOfEdges + numberOfElements;
	    enableDual = input_enableDual;
	    enableTruncate = input_enableTruncate;
	 
	    isPolygonSet = input_isPolygonSet;
	    isPointSet = false;	    
   };
   
   public Model(String[] input_category,
           String[] input_name,
           String   input_filetype,
           String   input_filename,
           boolean  input_showVertices,
           boolean  input_showEdges,
           boolean  input_showElements,
           boolean  input_enableDual,
           boolean  input_enableTruncate,
           boolean  input_isPolygonSet,
           boolean  input_isPointSet)
	{
	   category = input_category;
	   name = input_name;
	   filetype = input_filetype;
	   filename = input_filename;
	   showVertices = input_showVertices;
	   showEdges = input_showEdges;
	   showElements = input_showElements;
	   numberOfVertices = -1;
	   numberOfEdges = -1;
	   numberOfElements = -1;
	   eulerCharacteristic = numberOfVertices - numberOfEdges + numberOfElements;
	   enableDual = input_enableDual;
	   enableTruncate = input_enableTruncate;
	
	   isPolygonSet = input_isPolygonSet;
	   isPointSet = input_isPointSet;
	};   
   
   
   public String getCategory(int l)
   {
      return(category[l]);
   };

   public String getName(int l)
   {
      return(name[l]);
   };

   public String getFileType()
   {
      return(filetype);
   };

   public String getFileName()
   {
      return(filename);
   };
   
   public boolean getImproveUnfolding()
   {
      return(improveUnfolding);
   };
   
   public int getUnfoldingTrials()
   {
      return(unfoldingTrials);
   };      

   public boolean isShowingVertices()
   {
      return(showVertices);
   };

   public boolean isShowingEdges()
   {
      return(showEdges);
   };

   public boolean isShowingElements()
   {
      return(showElements);
   };

   public boolean isEnabledDual()
   {
      return(enableDual);
   };

   public boolean isEnabledTruncate()
   {
      return(enableTruncate);
   };
   
   public boolean isPolygonSet()
   {
	   return(isPolygonSet);
   }

   public boolean isPointSet()
   {
	   return(isPointSet);
   }   
   
   public void setImproveUnfolding(boolean b, int t)
   {
      improveUnfolding = b;
      unfoldingTrials = t;
   };
   
   public int getNumberOfVertices()
   {
      return(numberOfVertices);
   };      

   public int getNumberOfEdges()
   {
      return(numberOfEdges);
   };      

   public int getNumberOfElements()
   {
      return(numberOfElements);
   };      

   public int getEulerCharacteristic()
   {
      return(eulerCharacteristic);
   };      
   
}; // End of Model 


