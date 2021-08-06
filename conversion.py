from os import listdir, read
import io



ggbConstructParams = [
  "showMenuBar",  "showAlgebraInput", "showResetIcon",
  "enableLabelDrags", "errorDialogsActive", "ggbBase64"
]

oldHtmlParams = [
  "width", "height", 'framePossible', 'enableRightClick', 'enableShiftDragZoom',
  'showToolBar', 'showToolBarHelp'
]


# get a .html file from activity\activity-html\
def readhtml(filename, dir):
  dir += "\\" + dir+"-html\\"
  filename = dir+filename
  print('\n', filename, '\n')
  with open(filename, mode='r', encoding='ISO-8859-1') as f:
    filename = f.read()
    f.close()
  return  filename

# These functions get texts from other texts
def getAppletText(reader):
  startPos = reader.find('<applet')
  endPos = reader.find('</applet>', startPos) + len('</applet>')
  return reader[startPos:endPos]

def getLastAppletText(reader):
  startPos = reader.rfind('<applet')
  endPos = reader.rfind('</applet>', startPos) + len('</applet>')
  return reader[startPos:endPos]

def getggbfilename(reader):
  # finding .ggb file name
  startPos = reader.find('<param name="filename" value="') + (len('<param name="filename" value="'))
  endPos = reader.find('"', startPos)
  return reader[startPos:endPos]

def getAppletName (reader):
  # finding the corresponding applet name
  startPos = reader.find('applet name="') + (len('applet name="'))
  endPos = reader.find('"', startPos)
  return reader[startPos:endPos]


# This sets the parameters as they used to be in java applet times
def makeParams(reader, vparamlist, htmlreader, htmlparlist):
  # vparameters: parameters from ggb construction html file
  # htmlparameters: parameters from old construction, to mantain the standard

  outParams = dict()

  # FIXED PARAMETERS
  # outParams["id"] = appletname+"Applet"
  outParams["allowStyleBar"] = "false"
  outParams["enableShiftDragZoom"] = "false"
  outParams["useBrowserForJS"] = "false"

  # applet dependent parameters
  for i in range(len(vparamlist)):
    param = vparamlist[i]
    startPos = reader.find(param)
    if startPos != -1:
      startPos = reader.find("=", startPos)+2
      endPos = reader.find('\"', startPos+1)
      outParams[param] = reader[startPos:endPos]
  # ggbBase64 is string
  outParams["ggbBase64"] = '"'+outParams["ggbBase64"]+'"'

  for i in range(len(htmlparlist)):
    param = htmlparlist[i]
    startPos = htmlreader.find(param)
    if startPos != -1:
      startPos = htmlreader.find("=", startPos)+2
      endPos = htmlreader.find('\"', startPos+1)
      outParams[param] = htmlreader[startPos:endPos]



  return outParams


# This writes the injector text in modern GeoGebra Javascript language
def writeInjector(appName, outParams, filename = None):
  # writing parameters
  outText = '<script type="text/javascript">\n'
  outText += 'var params'+appName+' = {'
  outText += '"id": "' + appName +'"'
  for i in outParams:
    outText += ', "' + i + '": ' + outParams[i]
  outText += "};"

  outText += "\n"
  # new way to check if applet is loaded!
  outText += 'params'+appName+'.appletOnLoad = function(api) {'+appName+'OK = true};'
  outText += "\nvar "+appName+' = new GGBApplet("5.0", params'+appName+');'
  outText += '\nwindow.addEventListener("load", function(){'
  outText += appName+'.inject("'+appName+'_container", "auto")});\n</script>'
  outText += '\n<div id="'+appName+'_container"></div>'

  #option to save as inject.js, an alternate way to include
  if filename != None:
    with open(filename, 'w') as f:
      f.write(outText)
      f.close()
  return outText

# Changes the old java applet function syntaxis to the modern script one
def functionsJavatoJS(text, appletName):
  old = 'document.getElementById("'+appletName+'")'
  text = text.replace(old, appletName)
  return text

# does all the above to a single applet
def convertAppletoJS(htmlFile, dir, ggbdir, vparameters, htmlparameters, lastApplet = False):
  # vparameters: parameters from ggb construction html file
  # htmlparameters: parameters from old construction, to mantain the standard

  if lastApplet:
    applet1 = getLastAppletText(htmlFile)
  else:
    applet1 = getAppletText(htmlFile)

  appName1 = getAppletName(applet1)

  ggbfilename1 = getggbfilename(applet1)
  ggbfilename1 = ggbfilename1.replace('.ggb', '.html')
  ggbfilename1 = ggbfilename1.replace('-', '_')
  print("read this:", ggbfilename1)

  
  with open(ggbdir+dir+'\\'+ggbfilename1, 'r') as f:
    ggbhtmlFile = f.read()
    f.close()

  paramlist1 = makeParams(ggbhtmlFile, vparameters, applet1, htmlparameters)

  injectText = writeInjector(appName1, paramlist1, dir+'\\inject.js')

  htmlFile = htmlFile.replace(applet1, injectText)
  htmlFile = functionsJavatoJS(htmlFile, appName1)

  # removes deprecated way to check if applet is loaded
  startPos = htmlfile.find('function ggbOnInit(c)')
  endPos = htmlfile.find('function check()')-1

  onInit = htmlfile[startPos:endPos]
  htmlFile = htmlFile.replace(onInit, '')

  return htmlFile


# main()

ggbDir = "geogebra-constructions\\"

# dirs = listdir(ggbDir)
dirs = ["eci", 'eco', 'epi']
print(dirs)

# dirs.remove('copying.py')
# dirs.remove('estranhos')


# print(dir+"\\"+dir+"-html\\filename")

for curr in dirs:
  files = []
  for i in listdir(curr+'\\'+curr+'-html'):
    if i.find('.html') != -1 and i.find(curr+'-0') != -1:
      files.append(i)

  for filetoConvert in files:
    htmlfile = readhtml(filetoConvert, curr)

    if htmlfile.count('<applet') > 2:
      print(filetoConvert, "has 3d graphics")
    htmlfile = '<script type="text/javascript" src="https://cdn.geogebra.org/apps/deployggb.js"></script>\n<meta charset="UTF-8">\n' + htmlfile

    htmlfile = convertAppletoJS(htmlfile, curr, ggbDir, ggbConstructParams, oldHtmlParams)
    htmlfile = convertAppletoJS(htmlfile, curr, ggbDir, ggbConstructParams, oldHtmlParams, lastApplet=True)
    # quit()

    with open(curr+'\\'+curr+'-html'+'\\converted-'+filetoConvert, 'w', encoding='utf-8') as f:
      f.write(htmlfile)

# print(ggbhtmlFile)
print("\n\n")
# print(injectText)