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
var views3d = {'is3D': 1,'AV': 1,'SV': 0,'CV': 0,'EV2': 0,'CP': 0,'PC': 0,'DA': 0,'FI': 0,'macro': 0};
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