///////////////////////////////////////////////////////////////////////////////
// Category class
//
// Last update: 28/01/2008
///////////////////////////////////////////////////////////////////////////////

public class Category 
{
   protected String[] name;
   protected int number_of_models;
   protected Model[] models;

   public Category(String[] input_name, int input_number_of_models)
   {
      name = input_name;
      number_of_models = input_number_of_models;
      models = new Model[number_of_models];
   };

   public void addModel(int i, Model m)
   {
      models[i] = m;      
   };

   public String getName(int l)
   {
      return(name[l]);
   };

   public int getNumberOfModels()
   {
      return(number_of_models);
   };

   public Model getModel(int i)
   {
      return(models[i]);
   };
}; // End of Model 


