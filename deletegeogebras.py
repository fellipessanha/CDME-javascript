import os

to_delete = ["geogebra", "geogebra_cas", "geogebra_export", \
  "geogebra_gui", "geogebra_main", "geogebra_properties"]


dirs = os.listdir()

dirs.remove('app-geogebra')
dirs.remove('geogebra-constructions')
dirs.remove('.git')
dirs.remove('deletegeogebras.py')
dirs.remove('index.html')
dirs.remove('conversion.py')
dirs.remove('pgb')

for i in dirs:
  for j in to_delete:
    filepath = i+"\\"+i+"-html\\"
    filename = j+".jar"
    if filepath in os.listdir(filepath):
      os.remove(filepath+filename)
    else:
      print(filepath+" not found!\n")