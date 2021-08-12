var parameters = {
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
  var views = {'is3D': 1,'AV': 0,'SV': 0,'CV': 0,'EV2': 0,'CP': 0,'PC': 0,'DA': 0,'FI': 0,'macro': 0};
  var applet3d = new GGBApplet(parameters, '5.0', views);
  window.onload = function() {applet3d.inject('Applet3d')};