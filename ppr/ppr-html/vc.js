var cdmeShortURL = "http://www.uff.br/cdme/ppr/";
var cdmeURL = "http://www.uff.br/cdme/ppr/ppr-html/";
var cdmeVersion = "29.05.2009.gif";

var cdmecv = {};
cdmecv = {
    img:null,
    imgPreload:null,
    timer:null,
    init:function() {
        var sess = new Date();
        var nocache = sess.getTime();
        var imguri = cdmecv.img+"?time="+nocache;
        cdmecv.imgPreload = new Image();
        cdmecv.imgPreload.onload = function() {
            clearTimeout(cdmecv.timer);
            cdmecv.timer = null;
            // document.getElementById("versionBanner").innerHTML = '<br />Você está com a última versão do software!';
        };
        cdmecv.imgPreload.src = imguri;
        cdmecv.timer = setTimeout("cdmecv.fail_to_cdmecv()",2000);
    },
    fail_to_cdmecv:function() {
        clearTimeout(cdmecv.timer);
        cdmecv.timer = null;
        cdmecv.imgPreload = null;
        document.getElementById("versionBanner").innerHTML = '<br /><a href="' + cdmeShortURL + '"><img src="nv.gif" style="border-style: none" /></a>';
    }
};

var cdmeco = {};
cdmeco = {
    img:null,
    imgPreload:null,
    timer:null,
    init:function() {
        var sess = new Date();
        var nocache = sess.getTime();
        var imguri = cdmeco.img+"?time="+nocache;
        cdmeco.imgPreload = new Image();
        cdmeco.imgPreload.onload = function() {
            clearTimeout(cdmeco.timer);
            cdmeco.timer = null;
            cdmecv.img = cdmeURL + cdmeVersion;
            cdmecv.init();
        };
        // We're online, so check software version
        cdmeco.imgPreload.src = imguri;
        cdmeco.timer = setTimeout("cdmeco.fail_to_cdmeco()",60000);
    },
    fail_to_cdmeco:function() {
        clearTimeout(cdmeco.timer);
        cdmeco.timer = null;
        cdmeco.imgPreload = null;
        // document.getElementById("versionBanner").innerHTML = '<br />Você não está conectado na internet!';
    }
};


cdmeco.img = cdmeURL + "online.gif";
cdmeco.init();
