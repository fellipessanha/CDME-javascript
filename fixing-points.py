import os


dirs = ['eco', 'epi', 'pbc', 'pbe', 'pbo', 'pbt', 'pce', 'pch', 'pco', 'pct', 'pdc', 'pep', 'pge', 'pjn', 'ppa', 'pre', 'ptr', 'rap']
# print(dirs)

for module in dirs:

  path = module+"\\"+module+"-html\\"
  file = module+"-01-br.html"
  os.remove(path+"backup"+file)
  print(path+"backup"+file+" removed")
  # print(module)

  # with open(path+file, mode='r', encoding='utf-8') as f:
  #   filetext = f.read()

  # # with open(path+"backup"+file, mode='w') as f:
  # #   f.write(filetext)

  # filetext = filetext.replace("paramsrspApplet.appletOnLoad = function(api) {rspAppletOK = true}", "paramsrspApplet.appletOnLoad = function(api) {rspAppletOK = true; api.setFixed(\"P\", true, true);}")

  # with open(path+file, mode='w', encoding='utf-8') as f:
  #   f.write(filetext)