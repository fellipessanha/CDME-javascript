import os
from shutil import copyfile


destinpath = "D:\\UFF 2020.2\\TCC\\htmls"
frompath = "D:\\UFF 2020.2\\TCC\\legacy"

currentdir = os.listdir()

dirs = os.listdir(frompath)
print(dirs)

dirs.remove("conversion.py")
dirs.remove('inject.js')



for i in dirs:

  htmlpath = frompath+'\\'+i+'\\'+i+'-html'
  indir = os.listdir(htmlpath)
  # print(indir)
  for j in indir:
    if j.find('.ggb') != -1:
      print(j)
      copyfile(htmlpath+'\\'+j, destinpath+'\\'+i+'\\'+j)

