///////////////////////////////////////////////////////////////////////////////
// Quaternion class
//
// Last update: 15/07/2006
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
// JavaView imports
///////////////////////////////////////////////////////////////////////////////
import jv.vecmath.PdVector;

public class Quaternion
{
    protected double x;            // 1 component
    protected double y;            // i component
    protected double z;            // j component
    protected double w;            // k component
    
    // Default constructor
    public Quaternion()
    {
       x = y = z = w = 0.0;
    }; // End of Quaternion()
    
    // Constructor from components
    public Quaternion(double input_x, 
                      double input_y, 
                      double input_z, 
                      double input_w)
    {
       x = input_x;
       y = input_y;
       z = input_z;
       w = input_w;
    }; // End of Quaternion()
    
    // Constructor from quaternion
    public Quaternion(Quaternion q)
    {
       x = q.getComponent(1);
       y = q.getComponent(2);
       z = q.getComponent(3);
       w = q.getComponent(4);
    }; // End of Quaternion()    
    
    // Constructor from real and vector parts
    public Quaternion(double real_part, 
                      PdVector vector_part)
    {
       x = real_part;
       y = vector_part.getEntry(0);
       z = vector_part.getEntry(1);
       w = vector_part.getEntry(2);
    }; // End of Quaternion()    

    // Normalizes this quaternion 
    public void normalize()
    {
       double norm = Math.sqrt(x*x + y*y + z*z + w*w);
       x /= norm;
       y /= norm;
       z /= norm;
       w /= norm;
    }; // End of normalize()
    
    // Return this*q;
    public Quaternion times(Quaternion q)
    {
       double x2 = q.getComponent(1);
       double y2 = q.getComponent(2);
       double z2 = q.getComponent(3);
       double w2 = q.getComponent(4);
       
       double a1 = (w - z)*(z2 - w2);
       double a2 = (x + y)*(x2 + y2);
       double a3 = (x - y)*(z2 + w2);
       double a4 = (z + w)*(x2 - y2);
       double a5 = (w - y)*(y2 - z2);
       double a6 = (w + y)*(y2 + z2);
       double a7 = (x + z)*(x2 - w2);
       double a8 = (x - z)*(x2 + w2);
       double s = a6 + a7 + a8;
       double t = (a5 + s)/2.0;
       
       return(new Quaternion(a1 + t - a6, a2 + t - s, a3 + t - a8, a4 + t - a7));
    }; // End of times()
    
    // Set this to this*q;
    public void multiply(Quaternion q)
    {
       double x2 = q.getComponent(1);
       double y2 = q.getComponent(2);
       double z2 = q.getComponent(3);
       double w2 = q.getComponent(4);

       double a1 = (w - z)*(z2 - w2);
       double a2 = (x + y)*(x2 + y2);
       double a3 = (x - y)*(z2 + w2);
       double a4 = (z + w)*(x2 - y2);
       double a5 = (w - y)*(y2 - z2);
       double a6 = (w + y)*(y2 + z2);
       double a7 = (x + z)*(x2 - w2);
       double a8 = (x - z)*(x2 + w2);
       double s = a6 + a7 + a8;
       double t = (a5 + s)/2.0;

       x = a1 + t - a6;
       y = a2 + t - s;
       z = a3 + t - a8;
       w = a4 + t - a7;
    }; // End of times()    
    
    // Returns this + q
    public Quaternion plus(Quaternion q)
    {
       double x2 = q.getComponent(1);
       double y2 = q.getComponent(2);
       double z2 = q.getComponent(3);
       double w2 = q.getComponent(4);

       return(new Quaternion(x + x2, y + y2, z + z2, w + w2));
    }; // End of plus()
    
    // Conjugates this quaternion
    public void conjugate()
    {
       y *= -1.0;
       z *= -1.0;
       w *= -1.0;
    }; // End of conjugate()
    
    // Gets the real part of the quaternion
    public double getRealPart()
    {
       return(x);
    }; // End of getRealPart()

    // Gets the vector part of the quaternion
    public PdVector getVectorPart()
    {
       return(new PdVector(y, z, w));
    }; // End of getVectorPart()
    
    // Gets the i-th compoment of the quaternion
    public double getComponent(int i)
    {
       double v = x;
       switch(i)
       {
          case 2:
            v = y;
          break;          

          case 3:
            v = z;
          break;                    
          
          case 4:
            v = w;
          break;                              
       };
       
       return(v);
    }; // End of getComponent()    
    
    // Sets the components of this quaternion
    public void setComponent(double input_x,
                             double input_y,
                             double input_z,
                             double input_w)
    {
       x = input_x;
       y = input_y;
       z = input_z;
       w = input_w;
    }; // End of setComponent()        
    
    // Sets the components of this quaternion
    public void setComponent(Quaternion q)
    {
       x = q.getComponent(1);
       y = q.getComponent(2);
       z = q.getComponent(3);
       w = q.getComponent(4);
    }; // End of setComponent()            
    
    // Gets the norm of the quaternion
    public double getNorm()
    {
       return(Math.sqrt(x*x + y*y + z*z + w*w));
    }; // End of getNorm()   
    
    // Pretty printing for the ComplexNumber class
    public String toString()
    {
       return("(" + String.valueOf(x) + ") + (" 
                  + String.valueOf(y) + ")*i + ("
                  + String.valueOf(z) + ")*j + ( "
                  + String.valueOf(w) + ")*k");
   };    
}; // End of Quaternion class

