
var parameters3d = {
   "id": "Applet3d",
   "width":354,
   "height":214,
   "showMenuBar":false,
   "showAlgebraInput":false,
   "showToolBar":false,
   "customToolBar":"0 | 1 501 5 19 , 67 | 2 15 45 18 , 7 37 | 514 3 9 , 13 44 , 47 | 16 51 | 551 550 11 ,  20 22 21 23 , 55 56 57 , 12 | 69 | 510 511 , 512 513 | 533 531 , 534 532 , 522 523 , 537 536 , 535 , 538 | 521 520 | 36 , 38 49 560 | 571 30 29 570 31 33 | 17 | 540 40 41 42 , 27 28 35 , 6 , 502",
   "showToolBarHelp":false,
   "showResetIcon":false,
   "enableLabelDrags":false,
   "enableShiftDragZoom":false,
   "enableRightClick":false,
   "errorDialogsActive":false,
   "useBrowserForJS":false,
   "allowStyleBar":false,
   "preventFocus":false,
   "showZoomButtons":false,
   "capturingThreshold":3,
   // add code here to run when the applet starts
   "appletOnLoad":function(api){ /* api.evalCommand('Segment((1,2),(3,4))');*/ },
   "showFullscreenButton":false,
   "scale":1,
   "disableAutoScale":false,
   "allowUpscale":false,
   "clickToLoad":false,
   "appName":"classic",
   "buttonRounding":0.7,
   "buttonShadows":false,
   "language":"pt",
   // use this instead of ggbBase64 to load a material from geogebra.org
   // "material_id":"RHYH3UQ8",
   // use this instead of ggbBase64 to load a .ggb file
   // "filename":"myfile.ggb",
   "ggbBase64":"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",
   };
   // is3D=is 3D applet using 3D view, AV=Algebra View, SV=Spreadsheet View, CV=CAS View, EV2=Graphics View 2, CP=Construction Protocol, PC=Probability Calculator DA=Data Analysis, FI=Function Inspector, macro=Macros
   var views3d = {'is3D': 1,'AV': 0,'SV': 0,'CV': 0,'EV2': 0,'CP': 0,'PC': 0,'DA': 0,'FI': 0,'macro': 0};
   var applet3d = new GGBApplet(parameters3d, '5.0', views3d);
   window.onload = function() {applet3d.inject('Applet3d')};
   
   function closingAnimation()
   {
      var animation = Applet3d.getValue('variavelMovimento');
      animation = Math.max(animation - 0.1, 0);
      Applet3d.setValue('variavelMovimento', animation);
   }
   
   function openingAnimation()
   {
      var animation = Applet3d.getValue('variavelMovimento');
      animation = Math.min(animation + 0.1, 1);
      Applet3d.setValue('variavelMovimento', animation);
   }
   
   function checkKeyPress(key){
   
      if (key.keyCode == '49' || key.keyCode == '97'){
         openingAnimation();
      } else if (key.keyCode == '50' || key.keyCode == '98'){
         closingAnimation();
      }
   }
   
   window.addEventListener("keypress", checkKeyPress, false);