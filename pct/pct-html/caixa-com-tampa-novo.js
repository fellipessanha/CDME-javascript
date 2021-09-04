
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
   "appletOnLoad":function(api){api.setMode(540);},
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
         console.log('oi');
         openingAnimation();
      } else if (key.keyCode == '50' || key.keyCode == '98'){
         closingAnimation();
         console.log('tchau');

      }
   }
   
   window.addEventListener("keypress", checkKeyPress, false);